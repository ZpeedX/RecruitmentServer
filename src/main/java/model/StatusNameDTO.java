/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;


/**
 * DTO for status names
 * @author Evan
 * @author Oscar
 */
public class StatusNameDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private String supportedLanguage;

    /**
     * Constructor
     */
    public StatusNameDTO() {
    }

    /**
     * Created s status DTO with name and language
     *
     * @param name name of status
     * @param supportedLanguage language of name
     */
    public StatusNameDTO(String name, String supportedLanguage) {
        this.name = name;
        this.supportedLanguage = supportedLanguage;
    }

    /**
     * Returns the name of the status
     *
     * @return name of status
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the status
     *
     * @param name status name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the language of the status
     *
     * @return language of status
     */
    public String getSupportedLanguage() {
        return supportedLanguage;
    }

    /**
     * Set the language of the status
     *
     * @param supportedLanguage status language
     */
    public void setSupportedLanguage(String supportedLanguage) {
        this.supportedLanguage = supportedLanguage;
    }

}
