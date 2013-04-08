package br.org.eldorado.pagemarker.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import br.org.eldorado.pagemarker.R;
import br.org.eldorado.pagemarker.business.bo.UserBO;
import br.org.eldorado.pagemarker.business.exception.BusinessException;
import br.org.eldorado.pagemarker.persistence.provider.MarkerContentProvider;
import br.org.eldorado.pagemarker.persistence.vo.User;

public class MainActivity extends ListActivity {

        public final static int RESULT_NEW_USER_ACTIVITY = 0x12;
        public final static String CURRENT_USER_ID = "br.eldorado.org.mainactivity.currentuser";

        private final static String LIST_MAP_USERNAME = "username";
        private final static String LIST_MAP_USER = "user";

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

        @Override
        protected void onListItemClick(ListView l, View v, int position, long id) {

        }

        public void loadUsers() {

                // Try to retrieve current users,
                // if there is none, or some error occur,
                // redirect to the activity to create a new user
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
                        setListAdapter(new SimpleAdapter(getBaseContext(), getListAdapterData(users),
                                android.R.layout.simple_list_item_1, new String[] { LIST_MAP_USERNAME },
                                new int[] { android.R.id.text1 }));
                } else {
                        Intent newUserIntent = new Intent(this, NewUserActivity.class);
                        newUserIntent.putExtra(NewUserActivity.DISPLAY_NO_USERS_TEXT, true);
                        startActivityForResult(newUserIntent, RESULT_NEW_USER_ACTIVITY);
                }
        }

        private List<Map<String, Object>> getListAdapterData(List<User> users) {
                List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

                for (User user : users) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put(LIST_MAP_USERNAME, user.getName());
                        map.put(LIST_MAP_USER, user);
                        data.add(map);
                }

                return data;
        }
}
