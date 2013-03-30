package br.org.eldorado.pagemarker.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import br.org.eldorado.pagemarker.R;
import br.org.eldorado.pagemarker.business.bo.UserBO;
import br.org.eldorado.pagemarker.business.exception.BusinessException;

public class MainActivity extends Activity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                // Try to retrieve current users,
                // if there is none, or some error occur,
                // displays a button to create a new one.
                UserBO userBO = new UserBO(getContentResolver());
                List<String> userNames = null;
                try {
                        userNames = userBO.getAllUserNames();
                } catch (BusinessException e) {
                        Log.e(MainActivity.class.getName(), "User names were not retrieved.");
                        Toast.makeText(this, this.getString(R.string.error_user_get_names), Toast.LENGTH_SHORT).show();
                }
                if (userNames != null && userNames.size() > 0) {
                        setContentView(R.layout.activity_main);
                        for (String name : userNames) {
                                TextView textView = new TextView(this.getBaseContext());
                                textView.setText(name);
                        }
                } else {
                        setContentView(R.layout.no_users_dialog);
                }
        }

        /**
         * Redirects to the UserActivity
         * 
         * @param view
         */
        public void newUserClick(View view) {
                Intent newUserIntent = new Intent(this, NewUserActivity.class);
                startActivity(newUserIntent);
        }

}
