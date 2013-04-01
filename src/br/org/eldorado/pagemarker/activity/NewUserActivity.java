package br.org.eldorado.pagemarker.activity;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import br.org.eldorado.pagemarker.R;
import br.org.eldorado.pagemarker.business.bo.UserBO;
import br.org.eldorado.pagemarker.business.exception.BusinessException;

public class NewUserActivity extends Activity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.new_user);

                // Force keyboard to appear
                final EditText editTextUserName = (EditText) findViewById(R.id.editTextUserName);
                editTextUserName.requestFocus();
                editTextUserName.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                                InputMethodManager imm =
                                        (InputMethodManager) NewUserActivity.this.getSystemService(Service.INPUT_METHOD_SERVICE);
                                imm.showSoftInput(editTextUserName, InputMethodManager.SHOW_FORCED);
                        }
                }, 200);
        }

        /**
         * Method called by the activity button to save the filled user
         * information.
         * 
         * @param view
         */
        public void saveUser(View view) {
                int savedUserId = 0;
                EditText editTextUserName = (EditText) findViewById(R.id.editTextUserName);
                String userName = editTextUserName.getText().toString();

                try {
                        // Save the user
                        UserBO userBO = new UserBO();
                        savedUserId = userBO.createNewUser(userName);
                } catch (BusinessException e) {
                        Log.e(NewUserActivity.class.getName(), String.format("User %s was not saved.", userName));
                        Toast.makeText(this, String.format(this.getString(R.string.error_user_add), userName),
                                Toast.LENGTH_SHORT).show();
                }

                // Show success message
                Toast.makeText(this, String.format(this.getString(R.string.success_user_add), userName),
                        Toast.LENGTH_SHORT).show();

                // Return to main activity
                Intent resultIntent = new Intent();
                resultIntent.putExtra(MainActivity.CURRENT_USER_ID, savedUserId);
                this.setResult(RESULT_OK, resultIntent);
                this.finish();
        }
}
