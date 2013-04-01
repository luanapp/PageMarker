package br.org.eldorado.pagemarker.business.bo;

import java.util.List;

import br.org.eldorado.pagemarker.business.exception.BusinessException;
import br.org.eldorado.pagemarker.persistence.dao.UserDAO;
import br.org.eldorado.pagemarker.persistence.exception.PersistenceException;
import br.org.eldorado.pagemarker.persistence.vo.User;

public class UserBO {

        /**
         * Get all registered users and return them as a list. If there is no
         * users, null is returned.
         * 
         * @return
         */
        public List<User> getAllUsers() throws BusinessException {
                List<User> users = null;

                try {
                        UserDAO userDAO = new UserDAO();
                        users = userDAO.getAllUsers();
                } catch (Exception ex) {
                        throw new BusinessException(ex.getCause());
                }

                return users;
        }

        /**
         * Get user with the given id. If it was not found, null is returned.
         * 
         * @return
         */
        public User getUserById(int id) throws BusinessException {
                User user = null;

                try {
                        UserDAO userDAO = new UserDAO();
                        user = userDAO.getUserById(id);
                } catch (Exception ex) {
                        throw new BusinessException(ex.getCause());
                }

                return user;
        }

        /**
         * Creates a new user with the given username and returns the id for the
         * created user instance.
         * 
         * @param username
         * @return user id.
         * @throws PersistenceException
         */
        public int createNewUser(String username) throws BusinessException {
                int createdUserId = 0;
                try {
                        UserDAO userDAO = new UserDAO();
                        createdUserId = userDAO.createNewUser(username);
                } catch (Exception ex) {
                        throw new BusinessException(ex.getCause());
                }
                return createdUserId;
        }
}
