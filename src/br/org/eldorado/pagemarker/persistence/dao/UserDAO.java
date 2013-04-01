package br.org.eldorado.pagemarker.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import br.org.eldorado.pagemarker.PageMarkerApplication;
import br.org.eldorado.pagemarker.persistence.database.UserTable;
import br.org.eldorado.pagemarker.persistence.exception.PersistenceException;
import br.org.eldorado.pagemarker.persistence.provider.MarkerContentProvider;
import br.org.eldorado.pagemarker.persistence.vo.User;

public class UserDAO {

        /**
         * Get all registered users and return them as a list of {@link User}
         * objects. If there is no users, null is returned.
         * 
         * @return list of {@link User} objects or null.
         * @throws PersistenceException
         */
        public List<User> getAllUsers() throws PersistenceException {
                List<User> users = null;

                try {
                        ContentResolver resolver = PageMarkerApplication.getContext().getContentResolver();
                        Cursor cursor =
                                resolver.query(MarkerContentProvider.USERS_CONTENT_URI,
                                        new String[] { UserTable.COLUMN_NAME }, null, null, null);

                        if (cursor != null && cursor.getCount() > 0) {
                                cursor.moveToFirst();
                                users = new ArrayList<User>();
                                do {
                                        User user = new User();
                                        user.setName(cursor.getString(cursor.getColumnIndex(UserTable.COLUMN_NAME)));
                                        users.add(user);
                                } while (cursor.moveToNext());
                        }
                } catch (Exception ex) {
                        throw new PersistenceException(ex.getCause());
                }

                return users;
        }

        /**
         * Retrieve a user with the given id or null if no user was found.
         * 
         * @param id
         * @return found user.
         */
        public User getUserById(int id) {
                User user = null;
                try {
                        ContentResolver resolver = PageMarkerApplication.getContext().getContentResolver();

                        // Build where clause
                        String where = "ID = ?";
                        String[] selectionArgs = new String[] { String.valueOf(id) };

                        Cursor cursor =
                                resolver.query(MarkerContentProvider.USERS_CONTENT_URI,
                                        new String[] { UserTable.COLUMN_NAME }, where, selectionArgs, null);
                        if (cursor != null && cursor.moveToFirst()) {
                                user = new User();
                                user.setName(cursor.getString(cursor.getColumnIndex(UserTable.COLUMN_NAME)));
                        }
                } catch (Exception ex) {

                }
                return user;
        }

        /**
         * Creates a new user with the given username and returns created user
         * id.
         * 
         * @param username
         * @return id for the created user - last uri path segment.
         * @throws PersistenceException
         */
        public int createNewUser(String username) throws PersistenceException {
                int createdUserId = 0;
                try {
                        ContentResolver resolver = PageMarkerApplication.getContext().getContentResolver();

                        ContentValues values = new ContentValues();
                        values.put(UserTable.COLUMN_NAME, username);

                        // Insert the user and retrieve its id from uri
                        Uri createdUserUri = resolver.insert(MarkerContentProvider.USERS_CONTENT_URI, values);
                        createdUserId = Integer.valueOf(createdUserUri.getLastPathSegment());
                } catch (Exception ex) {
                        throw new PersistenceException(ex.getCause());
                }
                return createdUserId;
        }
}
