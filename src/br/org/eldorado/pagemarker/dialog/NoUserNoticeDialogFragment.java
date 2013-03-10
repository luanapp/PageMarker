package br.org.eldorado.pagemarker.dialog;

import br.org.eldorado.pagemarker.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class NoUserNoticeDialogFragment extends DialogFragment {

	private NoUserNoticeDialogListener dialogListener;

	/**
	 * The activity that creates an instance of this dialog fragment must
	 * implement this interface in order to receive event callbacks. Each method
	 * passes the DialogFragment in case the host needs to query it.
	 */
	public interface NoUserNoticeDialogListener {
		public void onDialogPostitiveClick(DialogFragment dialog);

		public void onDialogNegativeClick(DialogFragment dialog);
	}

	/**
	 * Override the Fragment.onAttach() method to instantiate the
	 * NoUserNoticeDialogFragment
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			dialogListener = (NoUserNoticeDialogListener) activity;
		} catch (ClassCastException ex) {
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(activity.toString()
					+ " must implement NoticeDialogListener");
		}
	}

	/**
	 * Create the dialog attaching the callback events to the dialog events
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
				getActivity());
		dialogBuilder
				.setTitle(R.string.no_users)
				.setMessage(R.string.create_user_text)
				.setPositiveButton(R.string.create_user,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialogListener
										.onDialogPostitiveClick(NoUserNoticeDialogFragment.this);
							}
						})
				.setNegativeButton(R.string.button_cancel,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialogListener
										.onDialogNegativeClick(NoUserNoticeDialogFragment.this);
							}
						});
		return dialogBuilder.create();
	}
}
