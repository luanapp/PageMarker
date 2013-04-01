package br.org.eldorado.pagemarker.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import br.org.eldorado.pagemarker.R;
import br.org.eldorado.pagemarker.business.bo.UserBO;
import br.org.eldorado.pagemarker.business.exception.BusinessException;
import br.org.eldorado.pagemarker.persistence.database.UserTable;
import br.org.eldorado.pagemarker.persistence.provider.MarkerContentProvider;
import br.org.eldorado.pagemarker.persistence.vo.User;

public class MainActivity extends Activity {

        public final static int RESULT_NEW_USER_ACTIVITY = 0x12;
        public static String CURRENT_USER_ID = "br.eldorado.org.mainactivity.currentuser";

        private int _currentUser = 0;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                getContentResolver().delete(MarkerContentProvider.USERS_CONTENT_URI, null, null);
                loadUsers();
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);

                switch (requestCode) {
                case RESULT_NEW_USER_ACTIVITY:
                        if (resultCode == RESULT_OK) {
                                _currentUser = data.getIntExtra(CURRENT_USER_ID, 0);
                                loadUsers();
                        }
                        break;

                default:
                        break;
                }
        }

        /**
         * Redirects to the UserActivity
         * 
         * @param view
         */
        public void newUserClick(View view) {
                Intent newUserIntent = new Intent(this, NewUserActivity.class);
                startActivityForResult(newUserIntent, RESULT_NEW_USER_ACTIVITY);
        }

        public void loadUsers() {

                // Try to retrieve current users,
                // if there is none, or some error occur,
                // displays a button to create a new one.
                UserBO userBO = new UserBO();
                List<User> users = null;
                try {
                        users = userBO.getAllUsers();
                } catch (BusinessException e) {
                        Log.e(MainActivity.class.getName(), "User names were not retrieved.");
                        Toast.makeText(this, this.getString(R.string.error_user_get_names), Toast.LENGTH_SHORT).show();
                }
                if (users != null && users.size() > 0) {
                        setContentView(R.layout.activity_main);
                        for (User user : users) {
                                TextView textView = new TextView(this);
                                textView.setText(user.getName());
                        }
                } else {
                        setContentView(R.layout.no_users_dialog);
                }
        }

}
