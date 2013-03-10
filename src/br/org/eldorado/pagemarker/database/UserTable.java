package br.org.eldorado.pagemarker.database;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public final class UserTable implements BaseColumns {

	/**
	 * Declarations for the user table. Columns: ID (inherited), NAME (string),
	 * PIC (image/binary data)
	 */
	public static final String TABLE_NAME = "TB_USER";
	public static final String COLUMN_NAME = "STR_NAME";
	public static final String COLUMN_PIC = "BIN_PIC";

	/**
	 * Table creation script
	 */
	private static final String CREATE_TABLE = 
			"CREATE TABLE " + TABLE_NAME+ "(" 					// Create table statement for the user table
			+ _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 		// Id column
			+ COLUMN_NAME + " TEXT NOT NULL,"					// User name column, mandatory
			+ COLUMN_PIC  + " TEXT NULL );";					// User picture column, optional

	/**
	 * Table drop script
	 */
	private static final String DROP_TABLE = "DROP TABLE " + TABLE_NAME;

	/**
	 * Create the user table.
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
