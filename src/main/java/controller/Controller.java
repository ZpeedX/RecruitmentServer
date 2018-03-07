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

import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import model.ApplicationDetailsDTO;
import model.Availability;
import model.AvailabilityDTO;
import model.CompetenceDTO;
import model.CompetenceProfile;
import model.CompetenceProfileDTO;
import model.PDFGenerator;
import model.Person;
import model.StatusName;
import model.StatusNameDTO;
import model.Token;
import model.TokenGenerator;

/**
 * Handles the incoming calls from the view and passes them on to the
 * appropriate methods and returns data on success, else throws exceptions.
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
     * @param username the username of the individual trying to get
     * authenticated
     * @return Person the authenticated person or {@code null} if none
     */
    public Person authenticate(String username) {
        return rdao.getPerson(username);
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
     * This method retrieves a list of the competences available at these
     * services.
     *
     * @return List of CompetenceDTO objects
     */
    public List<CompetenceDTO> listCompetence() {
        return rdao.listCompetence().stream()
                .map(competenceName -> new CompetenceDTO(
                competenceName.getCompetenceNameId(),
                competenceName.getCompetenceId(),
                competenceName.getName(),
                competenceName.getSupportedLanguageId().getLocale())
                ).collect(Collectors.toList());
    }

    /**
     * This method gets specific language id by providing language name
     *
     * @param lang provided language name
     * @return language id from database
     */
    public SupportedLanguage getSl(String lang) {
        return rdao.getSlId(lang);
    }

    /**
     * Gets all applications from databases
     *
     * @return List of Application objects
     */
    public List<Applications> listApplications() {
        return rdao.getAllApplications();
    }

    /**
     * Gets all applications which fulfills every search criteria.
     *
     * @param submissionDate Date provided by the client
     * @param periodFrom Date provided by the client
     * @param periodTo Date provided by the client
     * @param competence Competence id provided by the client
     * @param firstname String name provided by the client
     * @param dummyDate Illegal date to filter out all empty dates
     * @return List of Application objects
     */
    public List<Applications> getApplications(Date submissionDate, Date periodFrom, Date periodTo, long competence, String firstname, Date dummyDate) {
        return rdao.getApplications(submissionDate, periodFrom, periodTo, competence, firstname, dummyDate);
    }

    /**
     * This method adds competence profiles of a user and persists them in the
     * database.
     *
     * @param user username of the signed in user to whom the profiles belong.
     * @param profiles the profiles with the information.
     */
    public void addCompetenceProfiles(String user, List<CompetenceProfile> profiles) {
        if (listIsEmpty(profiles)) {
            return;
        }
        rdao.addCompetenceProfiles(user, profiles);
    }

    /**
     * This method adds availabilities of a user and persists them in the
     * database.
     *
     * @param username username of the signed in user to whom the availabilities
     * belong.
     * @param availabilities the list with availability priods.
     */
    public void addAvailabilities(String username, List<Availability> availabilities) {
        if (listIsEmpty(availabilities)) {
            return;
        }
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

        while (!tokenDAO.addToken(token)) {
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
    /**
     * Retrieves application from database
     * @param appId id of application
     * @return  applicationDetails
     */
    public ApplicationDetailsDTO getApplicationDetails(long appId) {
        Applications app = rdao.getApplicationById(appId);
        if (app == null) {
            return null;
        }
        Person person = app.getPersonId();

        List<StatusNameDTO> statusNames = statusNamesToDTO(rdao.getStatusNamesByStatusId(app.getStatusId().getStatusId()));
        List<CompetenceProfileDTO> cp = rdao.getCompetenceProfileByPersonId(person);
        List<AvailabilityDTO> av = avToAvDTO(rdao.getAvailabilityíesByPerson(person));
        return new ApplicationDetailsDTO(
                person.getName(),
                person.getSurname(),
                person.getEmail(),
                person.getSsn(),
                app.getRegistrationDate(),
                statusNames,
                cp,
                av
        );

    }

    private List<StatusNameDTO> statusNamesToDTO(List<StatusName> statusNames) {
        return statusNames.stream()
                .map(statusName -> new StatusNameDTO(
                statusName.getName(),
                statusName.getSupportedLanguageId().getLocale()
                )).collect(Collectors.toList());
    }

    private List<AvailabilityDTO> avToAvDTO(List<Availability> av) {
        return av.stream()
                .map(availability -> new AvailabilityDTO(
                availability.getFromDate(),
                availability.getToDate()))
                .collect(Collectors.toList());
    }
  
    /**
     * Changes the status of an appllication
     * @param applicationId id of application to be changed
     * @param statusName status of application
     * @return List of StatusNameDTO if changed or null if not
     */
    public List<StatusNameDTO> changeAppStatus(long applicationId, String statusName) {
        Applications app = rdao.getApplicationById(applicationId);
        StatusName sn = rdao.getStatusNameByName(statusName);        
        if (app != null && sn != null) {
            app.setStatusId(sn);
            return statusNamesToDTO(rdao.getStatusNamesByStatusId(app.getStatusId().getStatusId()));
        }
        return null;
    }
  
    /**
     * Get pdf of application
     * @param id id of application
     * @param language language of application
     * @return pdfgenerator
     */
    public Object getPdf(long id, String language) {
        ApplicationDetailsDTO appDetails = getApplicationDetails(id);
        return new PDFGenerator(appDetails, language).generatePDF();
    }

    private boolean listIsEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

}
