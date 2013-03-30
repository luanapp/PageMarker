package br.org.eldorado.pagemarker.business.exception;

/**
 * Class to classify exceptions generated in the business layer
 * 
 * @author auro
 * 
 */
public class BusinessException extends Exception {

        /**
         * Generated serial version UID
         */
        private static final long serialVersionUID = 5003514581293926388L;

        public BusinessException() {
                super();
        }

        public BusinessException(String message) {
                super(message);
        }

        public BusinessException(String message, Throwable cause) {
                super(message, cause);
        }

        public BusinessException(Throwable cause) {
                super(cause);
        }
}
