package br.org.eldorado.pagemarker.persistence.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PageMarkerHelper extends SQLiteOpenHelper {

        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "PageMaker.db";

        public PageMarkerHelper(Context context) {
                super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
                UserTable.onCreate(db);
                Log.i(PageMarkerHelper.class.getName(), "Creating table " + UserTable.TABLE_NAME);

                BookTable.onCreate(db);
                Log.i(PageMarkerHelper.class.getName(), "Creating table " + BookTable.TABLE_NAME);

                MarkerTable.onCreate(db);
                Log.i(PageMarkerHelper.class.getName(), "Creating table " + MarkerTable.TABLE_NAME);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                String message = "Updating database from version %d to version %d";
                Log.w(PageMarkerHelper.class.getName(),
                        String.format(message, oldVersion, newVersion));

                UserTable.onUpdate(db, oldVersion, newVersion);
                BookTable.onUpdate(db, oldVersion, newVersion);
                MarkerTable.onUpdate(db, oldVersion, newVersion);
        }

}
