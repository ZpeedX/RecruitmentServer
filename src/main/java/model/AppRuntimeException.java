/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.ejb.ApplicationException;

/**
 *
 * @author Oscar
 */
@ApplicationException(rollback=true)
public class AppRuntimeException extends RuntimeException implements Serializable {
    
    public AppRuntimeException(String message) {
        super(message);
    }

    public AppRuntimeException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
