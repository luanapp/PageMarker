package br.org.eldorado.pagemarker.business.bo;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import br.org.eldorado.pagemarker.business.exception.BusinessException;
import br.org.eldorado.pagemarker.persistence.database.UserTable;
import br.org.eldorado.pagemarker.persistence.provider.MarkerContentProvider;

public class UserBO {

        private ContentResolver _resolver;

        public UserBO(ContentResolver resolver) {
                _resolver = resolver;
        }

        /**
         * Get all registered users and return them as a list. If there is no
         * users, null is returned.
         * 
         * @return
         */
        public List<String> getAllUserNames() throws BusinessException {
                List<String> userNames = null;

                try {
                        Cursor cursor =
                                _resolver.query(MarkerContentProvider.USERS_CONTENT_URI,
                                        new String[] { UserTable.COLUMN_NAME }, null, null, null);

                        if (cursor != null && cursor.moveToFirst()) {
                                userNames = new ArrayList<String>();
                                do {
                                        String userName =
                                                cursor.getString(cursor.getColumnIndex(UserTable.COLUMN_NAME));
                                        userNames.add(userName);
                                } while (cursor.moveToNext());
                        }
                } catch (Exception ex) {
                        throw new BusinessException(ex.getCause());
                }

                return userNames;
        }

        public boolean createNewUser(String username) throws BusinessException {
                boolean success = false;
                try {
                        ContentValues values = new ContentValues();
                        values.put(UserTable.COLUMN_NAME, username);
                        _resolver.insert(MarkerContentProvider.USERS_CONTENT_URI, values);
                        success = true;
                } catch (Exception ex) {
                        throw new BusinessException(ex.getCause());
                }
                return success;
        }
}
