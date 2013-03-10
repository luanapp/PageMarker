package br.org.eldorado.pagemarker;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import br.org.eldorado.pagemarker.dialog.NoUserNoticeDialogFragment;
import br.org.eldorado.pagemarker.dialog.NoUserNoticeDialogFragment.NoUserNoticeDialogListener;

public class MainActivity extends Activity implements NoUserNoticeDialogListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		NoUserNoticeDialogFragment dialog = new NoUserNoticeDialogFragment();
		dialog.show(getFragmentManager(), "NoUserNoticeDialogFragment");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onDialogPostitiveClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		
	}

}
