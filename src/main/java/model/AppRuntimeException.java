package model;

import java.io.Serializable;
import javax.ejb.ApplicationException;

/**
 * Runtime exception handler
 * @author Oscar
 */
@ApplicationException(rollback=true)
public class AppRuntimeException extends RuntimeException implements Serializable {
    /**
     * 
     * @param message 
     */
    public AppRuntimeException(String message) {
        super(message);
    }

    public AppRuntimeException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
