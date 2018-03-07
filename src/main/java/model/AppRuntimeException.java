package model;

import javax.ejb.ApplicationException;

/**
 * Runtime exception handler
 * @author Oscar
 */
@ApplicationException(rollback=true)
public class AppRuntimeException extends RuntimeException{
    /**
     * Sets why exception was cast
     * @param message reason
     */
    public AppRuntimeException(String message) {
        super(message);
    }
    /**
     * Sets message to why exception was thrown
     * @param message reason for exception
     * @param throwable the exception
     */
    public AppRuntimeException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
