/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Date;
import model.Applications;
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
import model.Availability;
import model.CompetenceDTO;
import model.CompetenceProfileDTO;
import model.Person;
import model.Token;
import model.TokenGenerator;

/**
 *
 * @author Emil
 * @author Oscar
 * @author Evan
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class Controller {
    
    @EJB
    private RecruitmentDAO rdao;
	
    @EJB
    private TokenDAO tokenDAO;

    @Inject
    private TokenGenerator tokenGenerator;
	
    /**
     * This method takes a username and checks if it is a valid username.
     * 
     * @param username the username of the individual trying to get authenticated
     * @return Person the authenticated person or {@code null} if none
     */
    public Person authenticate(String username) {
        return rdao.existsUser(username);
    }

    /**
     * This method registers a new user to the services.
     * 
     * @param newUser the Person getting registered
     * @return Person if sucessfully registering a new person, else {@code null}
     */
    public Person register(Person newUser) {
        return rdao.registerPerson(newUser);
    }
	
    /**
     * This method retrieves a list of the competences available at these services.
     * 
     * @param locale language desired/specified by the user
     * @return List<CompetenceDTO> with the competences
     */
    public List<CompetenceDTO> listCompetence(String locale) {
        return rdao.getAllCompetences(locale).stream()
                .map(competenceName -> new CompetenceDTO(
                        competenceName.getCompetenceNameId(), 
                        competenceName.getCompetenceId(),
                        competenceName.getName(),
                        competenceName.getSupportedLanguageId().getLocale())
                ).collect(Collectors.toList());
    }	

    /**
     *
     * @param lang
     * @return
     */
    public SupportedLanguage getSl(String lang){
        return rdao.getSlId(lang);
    }

    /**
     *
     * @return
     */
    public List<Applications> listApplications(){
        return rdao.getAllApplications();
    }

    /**
     *
     * @param submissionDate
     * @param periodFrom
     * @param periodTo
     * @param competence
     * @param firstname
     * @param dummyDate
     * @return
     */
    public List<Applications> getApplications(Date submissionDate, Date periodFrom, Date periodTo, long competence, String firstname, Date dummyDate) {
        
        return rdao.getApplications(submissionDate,periodFrom, periodTo, competence, firstname, dummyDate);
    }
	
    /**
     * This method adds competence profiles of a user and tries to store them in the
     * database. It first validates that the entered profiles.
     * 
     * @param user username of the signed in user to whom the profiles belong
     * @param profiles the profiles with the information
     */
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

    /**
     *
     * @param username
     * @param availabilities
     */
    public void addAvailabilities(String username, List<Availability> availabilities) {
        rdao.addAvailabilities(username, availabilities);
    }
	
    /**
     * This method logs in a user.
     * 
     * @param username user to be logged in
     * @param role the role to which this user belongs
     * @return String the generated token value
     */
    public String login(String username, String role) {
        Token token = tokenGenerator.generateToken(username, role);
        
        while(!tokenDAO.addToken(token)) {
            token = tokenGenerator.generateToken(username, role);
        }
        
        return token.getToken();
    }

    /**
     * This method retrieves the role associated to a specific token value.
     *
     * @param token value of the users token
     * @return String the role name the token belongs to
     */
    public String getRoleFromToken(String token) {
        return tokenDAO.getRoleFromToken(token);
    }

    /**
     * This method logs out a user.
     * 
     * @param username user to be logged out
     */
    public void logout(String username) {
        tokenDAO.logout(username);
    }
    
}
