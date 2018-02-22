/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Date;
import model.Applications;
import model.CompetenceName;
import model.SupportedLanguage;

import integration.RecruitmentDAO;
import integration.TokenDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import model.AvailabilityDTO;
import model.CompetenceDTO;
import model.CompetenceProfileDTO;
import model.Person;
import model.Token;
import model.TokenGenerator;

/**
 *
 * @author Emil
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class Controller {
    
    @EJB
    RecruitmentDAO rdao;
	
    @EJB
    TokenDAO tokenDAO;

    @Inject
    TokenGenerator tokenGenerator;
	
    public Person authenticate(String username) {
        return rdao.existsUser(username);
    }
    public Person register(Person newUser) {
        return rdao.registerPerson(newUser);
    }
	
    public List<CompetenceDTO> listCompetence(String locale) {
        return rdao.getAllCompetences(locale).stream()
                .map(competence -> new CompetenceDTO(
                        competence.getCompetenceNameId(), 
                        competence.getCompetenceId(),
                        competence.getName(),
                        competence.getSupportedLanguageId().getLocale())
                ).collect(Collectors.toList());
    }	

    public SupportedLanguage getSl(String lang){
        return rdao.getSlId(lang);
    }
    public List<Applications> listApplications(){
        return rdao.getAllApplications();
    }

    public List<Applications> getApplications(Date submissionDate, Date periodFrom, Date periodTo, long competence, String firstname, Date dummyDate) {
        
        return rdao.getApplications(submissionDate,periodFrom, periodTo, competence, firstname, dummyDate);
    }
	
    public void addCompetenceProfiles(String user, List<CompetenceProfileDTO> profiles) {
        List<CompetenceProfileDTO> cleanProfiles = new ArrayList<>();
        
        profiles.forEach(p -> {
            if(p.getName() != null && p.getCompetenceId() != null && p.getYearsOfExperience() != 0) {
                cleanProfiles.add(p);
            }
        });
        
        if(!cleanProfiles.isEmpty()) {
            rdao.addCompetenceProfiles(user, cleanProfiles);
        }
    }

    public void addAvailabilities(String user, List<AvailabilityDTO> availabilities) {
        List<AvailabilityDTO> cleanAvailabilities = new ArrayList<>();

        availabilities.forEach(a -> {
            if(a.getFromDate() != null && a.getToDate() != null) {
                cleanAvailabilities.add(a);
            }
        });

        if (!cleanAvailabilities.isEmpty()) {
            rdao.addAvailabilities(user, cleanAvailabilities);
        }
    }
	
    public String login(String username, String role) {
        Token token = tokenGenerator.generateToken(username, role);
        
        while(!tokenDAO.addToken(token)) {
            token = tokenGenerator.generateToken(username, role);
        }
        
        return token.getToken();
    }

    public String getRoleFromToken(String token) {
        return tokenDAO.getRoleFromToken(token);
    }

    public void logout(String username) {
        tokenDAO.logout(username);
    }
}
