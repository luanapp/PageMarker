package br.org.eldorado.pagemarker.persistence.exception;

/**
 * Class to classify exceptions generated in the persistence layer
 * 
 * @author auro
 * 
 */
public class PersistenceException extends Exception {

        /**
         * Generated serial version UID
         */
        private static final long serialVersionUID = -2848416724870160287L;

        public PersistenceException() {
                super();
        }

        public PersistenceException(String message) {
                super(message);
        }

        public PersistenceException(String message, Throwable cause) {
                super(message, cause);
        }

        public PersistenceException(Throwable cause) {
                super(cause);
        }
}
