package br.org.eldorado.pagemarker.activity;

import android.app.Activity;
import android.app.Service;
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

        public void saveUser(View view) {
                UserBO userBO = new UserBO(getContentResolver());
                EditText editTextUserName = (EditText) findViewById(R.id.editTextUserName);

                // Save the user
                try {
                        userBO.createNewUser(editTextUserName.getText().toString());
                } catch (BusinessException e) {
                        Log.e(NewUserActivity.class.getName(), "User was not saved.");
                        Toast.makeText(this, this.getString(R.string.error_user_get_names), Toast.LENGTH_SHORT).show();
                }
        }
}
