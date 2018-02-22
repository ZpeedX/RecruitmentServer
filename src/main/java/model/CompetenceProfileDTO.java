/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Oscar
 */
public class CompetenceProfileDTO implements Serializable {
    private Long competenceId;
    private transient String name;
    private double yearsOfExperience;

    /**
     * Class Constructor
     */
    public CompetenceProfileDTO() {
    }

    /**
     * Class Constructor
     *
     * @param competenceId sets the competenceId property
     * @param name sets the name property
     * @param yearsOfExperience sets the yearsOfExperience property
     */
    public CompetenceProfileDTO(Long competenceId, String name, double yearsOfExperience) {
        this.competenceId = competenceId;
        this.name = name;
        this.yearsOfExperience = yearsOfExperience;
    }

    /**
     * Gets the value of the getYearsOfExperience property
     *
     * @return getYearsOfExperience as double
     */
    public double getYearsOfExperience() {
        return yearsOfExperience;
    }

    /**
     * Sets the yearsOfExperience property
     *
     * @param yearsOfExperience the yearsOfExperience to set
     */
    public void setYearsOfExperience(double yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
    
    /**
     * Gets the value of the competenceId property
     *
     * @return competenceId as Long object
     */
    public Long getCompetenceId() {
        return competenceId;
    }

    /**
     * Sets the competenceId property
     *
     * @param competenceId the competenceId to set
     */
    public void setCompetenceId(Long competenceId) {
        this.competenceId = competenceId;
    }

    /**
     * Gets the value of the name property
     *
     * @return name as String object
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the name property
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    
}