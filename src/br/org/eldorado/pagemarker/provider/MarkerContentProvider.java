package br.org.eldorado.pagemarker.provider;

import br.org.eldorado.pagemarker.database.BookTable;
import br.org.eldorado.pagemarker.database.MarkerTable;
import br.org.eldorado.pagemarker.database.PageMarkerHelper;
import br.org.eldorado.pagemarker.database.UserTable;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;

public class MarkerContentProvider extends ContentProvider {

	private PageMarkerHelper pageMarkerHelper;

	
	/**
	 * UriMatcher identifiers for the content tree
	 */
	private static final int USERS = 13;
	private static final int USER_ID = 14;
	private static final int BOOKS = 23;
	private static final int BOOK_ID = 24;
	private static final int MARKERS = 33;
	private static final int MARKER_ID = 34;

	private static final String AUTHORITY = "br.org.eldorado.pagemarker.provider";

	private static final String USERS_BASE_PATH = "user";
	private static final String BOOKS_BASE_PATH = "book";
	private static final String MARKERS_BASE_PATH = "marker";
	
	/**
	 * Content URI Mime type for the three marker app tables
	 */
	public static final Uri USERS_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + USERS_BASE_PATH);
	public static final Uri BOOKS_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BOOKS_BASE_PATH);
	public static final Uri MARKERS_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + MARKERS_BASE_PATH);

	private static final UriMatcher _uriMatcher = new UriMatcher(
			UriMatcher.NO_MATCH);
	
	/**
	 * Declare the accepted uri matches
	 */
	static {
		_uriMatcher.addURI(AUTHORITY, USERS_BASE_PATH, USERS);
		_uriMatcher.addURI(AUTHORITY, USERS_BASE_PATH + "/#", USER_ID);
		_uriMatcher.addURI(AUTHORITY, BOOKS_BASE_PATH, BOOKS);
		_uriMatcher.addURI(AUTHORITY, BOOKS_BASE_PATH + "/#", BOOK_ID);
		_uriMatcher.addURI(AUTHORITY, MARKERS_BASE_PATH, MARKERS);
		_uriMatcher.addURI(AUTHORITY, MARKERS_BASE_PATH + "/#", MARKER_ID);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int deletedRows = 0;
		
		SQLiteDatabase database = pageMarkerHelper.getWritableDatabase();
		String tableName = null;
		
		int uriMatch = _uriMatcher.match(uri);
		switch (uriMatch) {
		case USER_ID:
			selection += " " + BaseColumns._ID + uri.getLastPathSegment();
		case USERS:
			tableName = UserTable.TABLE_NAME;
			break;
			
		case BOOK_ID:
			selection += " " + BaseColumns._ID + uri.getLastPathSegment();
		case BOOKS:
			tableName = BookTable.TABLE_NAME;
			break;
			
		case MARKER_ID:
			selection += " " + BaseColumns._ID + uri.getLastPathSegment();
		case MARKERS:
			tableName = MarkerTable.TABLE_NAME;
			break;			

		default:
			throw new IllegalArgumentException("Invalid URI");
		}
		
		if(tableName != null) {
			deletedRows = database.delete(tableName, selection, selectionArgs);
			
			// Notify the change for all content resolvers
			getContext().getContentResolver().notifyChange(uri, null);
		}
		
		return deletedRows;
	}

	@Override
	public String getType(Uri uri) {
		String mimeType = null;
		
		// Retrieve the match between the passed uri and the registered uris
		int uriMatch = _uriMatcher.match(uri);
		
		switch (uriMatch) {
		case USER_ID:
			mimeType = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd/page-marker/user";
			break;
		case USERS:
			mimeType = ContentResolver.CURSOR_DIR_BASE_TYPE+ "/vnd/page-marker/user";
			break;
		case BOOK_ID:
			mimeType = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd/page-marker/book";
			break;
		case BOOKS:
			mimeType = ContentResolver.CURSOR_DIR_BASE_TYPE+ "/vnd/page-marker/book";
			break;
		case MARKER_ID:
			mimeType = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd/page-marker/marker";
			break;
		case MARKERS:
			mimeType = ContentResolver.CURSOR_DIR_BASE_TYPE+ "/vnd/page-marker/marker";
			break;

		default:
			throw new IllegalArgumentException("Invalid URI");
		}
		
		return mimeType;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long id = 0;
		String basePath = null;
		SQLiteDatabase database = pageMarkerHelper.getWritableDatabase();
		
		// Retrieve the match between the passed uri and the registered uris
		int uriMatch = _uriMatcher.match(uri);
		
		switch (uriMatch) {
		case USERS:
			id = database.insert(UserTable.TABLE_NAME, null, values);
			basePath = USERS_BASE_PATH;
			break;
		case BOOKS:
			id = database.insert(BookTable.TABLE_NAME, null, values);
			basePath = BOOKS_BASE_PATH;
			break;
		case MARKERS:
			id = database.insert(MarkerTable.TABLE_NAME, null, values);
			basePath = MARKERS_BASE_PATH;
			break;

		default:
			throw new IllegalArgumentException("Invalid URI");
		}
		
		// Notify the change for all content resolvers
		getContext().getContentResolver().notifyChange(uri, null);
		return Uri.parse(basePath + "/" + id);
	}

	@Override
	public boolean onCreate() {
		boolean success = false;
		try {
			// Initialize the PageMarkerHelper object
			pageMarkerHelper = new PageMarkerHelper(getContext());
			success = true;
		} catch (Exception ex) {
			// Do nothing
		}
		
		return success;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Cursor cursor = null;
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		
		// Retrieve the match between the passed uri and the registered uris
		int uriMatch = _uriMatcher.match(uri);
		
		switch (uriMatch) {
		
		// Set specific parameters for the user entity
		case USER_ID:
			queryBuilder.appendWhere("_ID = " + uri.getLastPathSegment());
		case USERS:
			queryBuilder.setTables(UserTable.TABLE_NAME);
			break;
		
		// Set specific parameters for the book entity
		case BOOK_ID:
			queryBuilder.appendWhere("_ID = " + uri.getLastPathSegment());
		case BOOKS:
			queryBuilder.setTables(BookTable.TABLE_NAME);
			break;
			
		// Set specific parameters for the marker entity
		case MARKER_ID:
			queryBuilder.appendWhere("_ID = " + uri.getLastPathSegment());
		case MARKERS:
			queryBuilder.setTables(MarkerTable.TABLE_NAME);
			break;

		default:
			throw new IllegalArgumentException("Invalid URI");
		}
		
		// Perform the query
		SQLiteDatabase database = pageMarkerHelper.getWritableDatabase();
		cursor = queryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int updatedRows = 0;
		SQLiteDatabase database = pageMarkerHelper.getWritableDatabase();
		
		String tableName = null;		
		int uriMatch = _uriMatcher.match(uri);
		switch (uriMatch) {
		case USER_ID:
			selection += " " + BaseColumns._ID + uri.getLastPathSegment();
		case USERS:
			tableName = UserTable.TABLE_NAME;
			break;
			
		case BOOK_ID:
			selection += " " + BaseColumns._ID + uri.getLastPathSegment();
		case BOOKS:
			tableName = BookTable.TABLE_NAME;
			break;
			
		case MARKER_ID:
			selection += " " + BaseColumns._ID + uri.getLastPathSegment();
		case MARKERS:
			tableName = MarkerTable.TABLE_NAME;
			break;			

		default:
			throw new IllegalArgumentException("Invalid URI");
		}
		
		updatedRows = database.update(tableName, values, selection, selectionArgs);
		
		// Notify the change for all content resolvers
		getContext().getContentResolver().notifyChange(uri, null);
		
		return updatedRows;
	}

}
