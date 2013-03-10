package br.org.eldorado.pagemarker.database;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public final class MarkerTable implements BaseColumns {

	public static final String TABLE_NAME = "TB_MARKER";
	public static final String COLUMN_ID_USER = "ID_USER";
	public static final String COLUMN_ID_BOOK = "ID_BOOK";
	public static final String COLUMN_PAGE = "NUM_PAGE";
	public static final String COLUMN_PIC = "BIN_PIC";
	
	/**
	 * Declarations for the marker table. Columns: ID (inherited), ID_USER
	 * (integer foreign key), ID_BOOK (integer foreign key), NUM_PAGE (integer),
	 * BIN_PIC (image/binary data)
	 */
	private static final String CREATE_TABLE =
			"CREATE TABLE " + TABLE_NAME + "("									// Create table statement for the book table
			+ _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"						// Id column
			+ COLUMN_ID_USER + " INTEGER NOT NULL REFERENCES TB_USER(ID),"		// UserId from user table
			+ COLUMN_ID_BOOK + " INTEGER NOT NULL REFERENCES TB_BOOK(ID),"		// BookId from book table
			+ COLUMN_PAGE + " INTEGER NULL,"									// Page being marked or
			+ COLUMN_PIC + " TEXT NULL";										// Picture of the page being marked
	
	/**
	 * Table drop script
	 */
	private static final String DROP_TABLE = "DROP TABLE " + TABLE_NAME;
	
	
	/**
	 * Create the marker table.
	 * 
	 * @param database
	 */
	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(CREATE_TABLE);
	}
	
	
	/**
	 * Do the following updates, according to the current database version:
	 * Version 1: - Beta version. Just drop the tables.
	 * 
	 * @param database
	 * @param oldVersion
	 * @param newVersion
	 */
	public static void onUpdate(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		database.execSQL(DROP_TABLE);
	}
}
