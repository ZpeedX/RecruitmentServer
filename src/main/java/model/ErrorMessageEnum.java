/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Messages sent to the exception mapper to differentiate between exceptions.
 *
 * @author Oscar
 */
public enum ErrorMessageEnum {
    /**
     * Meesage for no database connection
     */
    NO_DB_CONNECTION,
    /**
     * Message for invalid content
     */
    INVALID_CONTENT,
    /**
     * Message for content present
     */
    CONTENT_PRESENT,
    /**
     * Message for username present
     */
    USERNAME_PRESENT,
    /**
     * Message for social security number present
     */
    SSN_PRESENT,
    /**
     * Message for failed operation
     */
    OPERTAION_FAILED,
    /**
     * Message for no content
     */
    NO_CONTENT
}
