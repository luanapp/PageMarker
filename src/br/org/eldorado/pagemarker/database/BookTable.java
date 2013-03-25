package br.org.eldorado.pagemarker.database;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public final class BookTable implements BaseColumns {

	public static final String TABLE_NAME = "TB_BOOK";
	public static final String COLUMN_TITLE = "STR_TITLE";
	public static final String COLUMN_AUTHOR = "STR_AUTHOR";
	public static final String COLUMN_EDITION = "STR_EDITION";
	public static final String COLUMN_PUBLISHER = "STR_PUBLISHER";
	public static final String COLUMN_ISBN = "STR_ISBN";	
	
	/**
	 * Declarations for the book table. Columns: ID (inherited), TITLE (string),
	 * AUTHOR (string), EDITION(string), PUBLISHER (string), ISBN(string)
	 */
	private static final String CREATE_TABLE =
			"CREATE TABLE " + TABLE_NAME + "("					// Create table statement for the book table
			+ _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"		// Id column
			+ COLUMN_TITLE		+ " TEXT NOT NULL,"				// Book title
			+ COLUMN_AUTHOR		+ " TEXT NULL,"					// Author, optional
			+ COLUMN_EDITION	+ " TEXT NULL,"					// Edition, optional
			+ COLUMN_PUBLISHER	+ " TEXT NULL,"					// Publisher, optional
			+ COLUMN_ISBN 		+ " TEXT NULL);";				// ISBN number, optional
	
	/**
	 * Table drop script
	 */
	private static final String DROP_TABLE = "DROP TABLE " + TABLE_NAME;

	/**
	 * Create the book table.
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
