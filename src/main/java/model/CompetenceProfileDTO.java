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

    public CompetenceProfileDTO() {
    }

    public CompetenceProfileDTO(Long competenceId, String name, double yearsOfExperience) {
        this.competenceId = competenceId;
        this.name = name;
        this.yearsOfExperience = yearsOfExperience;
    }

    public Long getCompetenceId() {
        return competenceId;
    }

    public void setCompetenceId(Long competenceId) {
        this.competenceId = competenceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(double yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
    
}