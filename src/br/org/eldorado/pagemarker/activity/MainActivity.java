package br.org.eldorado.pagemarker.activity;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import br.org.eldorado.pagemarker.R;
import br.org.eldorado.pagemarker.database.UserTable;
import br.org.eldorado.pagemarker.provider.MarkerContentProvider;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
	
	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		
		Cursor cursor = getContentResolver().query(MarkerContentProvider.USERS_CONTENT_URI,
				new String[] {UserTable.COLUMN_NAME}, null, null, null);
		
		if(cursor != null) {
			cursor.moveToFirst();
			do {
				String user = cursor.getString(cursor.getColumnIndex(UserTable.COLUMN_NAME));
				Log.d(TAG, user);
			} while(cursor.moveToNext());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
