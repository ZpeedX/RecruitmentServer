/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package integration;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import model.AppRuntimeException;
import model.Applications;
import model.CompetenceName;
import model.CompetenceProfile;
import model.Person;
import model.Role;
import model.SupportedLanguage;
import model.Availability;
import model.CompetenceProfileDTO;
import model.ErrorMessageEnum;
import model.StatusName;

/**
 * Handles the communication with the recruitment_db database.
 *
 * @author Emil
 * @author Oscar
 * @author Evan
 */
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Stateless
public class RecruitmentDAO {

    @PersistenceContext(unitName = "recruitmentPU")
    private EntityManager em;
    
    /**
     * This method stores a new user in the database.
     *
     * @param newUser the new user to be stored.
     * @return Person the person stored or {@code null} if unsucessful in
     * persisting the user.
     */
    public Person registerPerson(Person newUser) {
        makeSureUsernameFree(newUser.getUsername());
        makeSureSsnIsUnique(newUser.getSsn());
        
        Role r = getRole("Applicant");
        newUser.setRoleId(r);
        
        persistEntity(newUser);
        
        return newUser;
    }

    /**
     * This method retrieves the person belonging to a specific username from
     * the database.
     *
     * @param username the username of the person.
     * @return Person the person with the username or {@code null} if no such
     * Person exists in the database.
     */
    public Person getPerson(String username) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        
        return getSingleResultByQuery(Person.class, "findByUsername", params);

    }
    
    private void makeSureUsernameFree(String username) {
        if(getPerson(username) != null) {
            throwNewRuntimeException(ErrorMessageEnum.USERNAME_PRESENT.toString(), null);
        }
    }
    
    private void makeSureSsnIsUnique(String ssn) {
        Map<String, Object> params = new HashMap<>();
        params.put("ssn", ssn);
        
        if(getSingleResultByQuery(Person.class, "findBySsn", params) != null) {
            throwNewRuntimeException(ErrorMessageEnum.SSN_PRESENT.toString(), null);
        }
    }

    /**
     * Gets the Role object from database by providing the role name
     *
     * @param name provided to get role object
     * @return Role object
     */
    private Role getRole(String name) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        
        return getSingleResultByQuery(Role.class, "findByName", params);

    }

    /**
     * This metohd returns a list with all the competences stored in the
     * database.
     *
     * @return List of CompetenceName objects.
     */
    public List<CompetenceName> listCompetence() {
        return getListByQuery(CompetenceName.class, "findAll", null);
    }

    /**
     * This method retrieves all the competences in a specific language which is
     * chosen by the user.
     *
     * @param locale language selected/specified.
     * @return List of CompetenceName objects in the specified language.
     */
    public List<CompetenceName> getAllCompetences(String locale) {
        Map<String, Object> params = new HashMap<>();
        params.put("locale", locale);
        
        return getListByQuery(CompetenceName.class, "findAllByLang", params);
    }

    /**
     * This method gets specific language id by providing language name
     *
     * @param locale provided language name
     * @return language id from database
     */
    public SupportedLanguage getSlId(String locale) {
        Map<String, Object> params = new HashMap<>();
        params.put("locale", locale);
        
        return getSingleResultByQuery(SupportedLanguage.class, "findByLocale", params);
    }

    /**
     * Gets all applications from databases
     *
     * @return List of Application objects
     */
    public List<Applications> getAllApplications() {
        return getListByQuery(Applications.class, "findAll", null);
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
        try {
            TypedQuery<Applications> query
                    = em.createNamedQuery("Applications.findByParams", Applications.class)
                            .setParameter("firstname", firstname)
                            .setParameter("cpId", competence)
                            .setParameter("regDate", submissionDate, TemporalType.DATE)
                            .setParameter("tempDate", dummyDate, TemporalType.DATE)
                            .setParameter("datePeriodFrom", periodFrom, TemporalType.DATE)
                            .setParameter("datePeriodTo", periodTo, TemporalType.DATE);
            return query.getResultList();
        } catch(Exception ex) {
            throwNewRuntimeException(ErrorMessageEnum.OPERTAION_FAILED.toString(), ex);
            return null;
        }
        /*Map<String, Object> params = new HashMap<>();
        params.put("firstname", firstname);
        params.put("cpId", competence);
        params.put("regDate", submissionDate);
        params.put("tempDate", dummyDate);
        params.put("datePeriodFrom", periodFrom);
        params.put("datePeriodTo", periodTo);
        
        return getListByQuery(Applications.class, "findByParams", params);*/
    }

    /**
     * This method recieves a list with profiles belonging to a specific user
     * and persists these profiles in the database if they are valid and calls the
     * {@code newContentAddedToApplication} method.
     *
     * @param user username associated with the profiles.
     * @param profiles the profiles to be stored.
     */
    public void addCompetenceProfiles(String user, List<CompetenceProfile> profiles) {
        Person person = getPerson(user);

        profiles.forEach(prof -> {
            prof.setPersonId(person);
            persistEntity(prof);
        });

        newConentAddedToApplication(person);
    }
    
    private void resetApplicationStatusIfRejected(Applications application) {
        StatusName pending = getStatusNameByName("Pending");
        StatusName rejected = getStatusNameByName("Rejected");

        if(application.getStatusId().getStatusId().equals(rejected.getStatusId())) {
            application.setStatusId(pending);
        }
    }
    
    private void createNewApplication(Person person) {
        Applications application = new Applications();
        StatusName status = getStatusNameByName("Pending");
        Date registrationDate = new Date(System.currentTimeMillis());

        application.setPersonId(person);
        application.setStatusId(status);
        application.setRegistrationDate(registrationDate);

        persistEntity(application);
    }
    
    private void newConentAddedToApplication(Person person) {
        Map<String, Object> params = new HashMap<>();
        params.put("person", person);
        
        List<Applications> applications = getListByQuery(Applications.class, "findByPersonObject", params);
       
        if(applications.isEmpty()) {
            createNewApplication(person);
        } else {
            resetApplicationStatusIfRejected(applications.get(0));
        }
    }
    
    /**
     * This method recieves a list with availabilities belonging to a specific
     * user and persists these in the database if they are valid and calls the
     * {@code newContentAddedToApplication} method.
     *
     * @param username the user associated with the availabilities.
     * @param availabilities the availabilities to be stored.
     */
    public void addAvailabilities(String username, List<Availability> availabilities) {
        Person person = getPerson(username);

        availabilities.forEach(av -> {
            av.setPersonId(person);
            persistEntity(av);
        });

        newConentAddedToApplication(person);
    }
    /**
     * Return application based on id
     * @param appId id of application to return
     * @return application
     */
    public Applications getApplicationById(long appId) {
        return getResultFromPK(Applications.class, appId);
    }
    /**
     * Returns competence profile from person id
     * @param person person to get competenceprofile for
     * @return competence profile
     */
    public List<CompetenceProfileDTO> getCompetenceProfileByPersonId(Person person) {
        String query = "SELECT NEW model.CompetenceProfileDTO(cp.competenceId, cn.name, cp.yearsOfExperience, cn.supportedLanguageId.locale) "
                + "FROM CompetenceProfile cp, CompetenceName cn "
                + "WHERE cp.competenceId = cn.competenceId "
                + "AND cp.personId = :person";
        
        try {
            return em.createQuery(query, CompetenceProfileDTO.class)
                .setParameter("person", person).getResultList();
        } catch(Exception ex) {
            throwNewRuntimeException(ErrorMessageEnum.OPERTAION_FAILED.toString(), ex);
            return null;
        }
        
        
        /*Map<String, Object> params = new HashMap<>();
        params.put("person", person);
        
        return getListByQuery(CompetenceProfileDTO.class, query, params);*/
    }
    /**
     * Returns competencenames by id
     * @param competenceId id of competence
     * @return competences list
     */
    public List<CompetenceName> getCompetenceNamesByCompetenceId(long competenceId) {
        Map<String, Object> params = new HashMap<>();
        params.put("competenceId", competenceId);
        
        return getListByQuery(CompetenceName.class, "findByCompetenceId", params);
    }
    /**
     * Get statusnames by id
     * @param statusId id of status
     * @return list of status names
     */
    public List<StatusName> getStatusNamesByStatusId(BigInteger statusId) {
        Map<String, Object> params = new HashMap<>();
        params.put("statusId", statusId);
        
        return getListByQuery(StatusName.class, "findByStatusId", params);
    }
    /**
     * Returns availabilities by person
     * @param person person to get availability for
     * @return list of persons availabilities
     */
    public List<Availability> getAvailabilityíesByPerson(Person person){
        Map<String, Object> params = new HashMap<>();
        params.put("person", person);
        
        return getListByQuery(Availability.class, "findByPersonObject", params);
    }
    /**
     * Returns status by name
     * @param name name of status
     * @return return status name
     */
    public StatusName getStatusNameByName(String name) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        
        return getSingleResultByQuery(StatusName.class, "findByName", params);
        
    }
    
    private <T> List<T> getListByQuery(Class<T> entity, String query, Map<String, Object> parameters) {
        return getNamedQueryWithParameters(entity, query, parameters).getResultList();
    }
    
    private <T> T getSingleResultByQuery(Class<T> entity, String query, Map<String, Object> parameters) {
        validateDbConnectivity();
        
        try {
            return getNamedQueryWithParameters(entity, query, parameters).getSingleResult();
        } catch(Exception ex) {
            return null;
        }
    }
    
    private <T> TypedQuery<T> getNamedQueryWithParameters(Class<T> entity, String query, Map<String, Object> parameters) {
        validateDbConnectivity();
        
        try {
            String entityType = entity.getSimpleName();
            String fullQuery = entityType + "." + query;

            TypedQuery<T> namedQuery = em.createNamedQuery(fullQuery, entity);

            if(parameters != null) {
                parameters.entrySet().forEach(pair -> {
                    namedQuery.setParameter(pair.getKey(), pair.getValue());
                });
            }

            return namedQuery;
        } catch(Exception ex) {
            throwNewRuntimeException(ErrorMessageEnum.OPERTAION_FAILED.toString(), ex);
            return null;
        }
    }
    /**
     * Returns result based on primary key
     * @param <T>   type of entity
     * @param entity entity
     * @param pk primary key value
     * @return result of type T
     */
    public <T> T getResultFromPK(Class<T> entity, Object pk) {
        validateDbConnectivity();
        
        try {
            return em.find(entity, pk);
        } catch(Exception ex) {
            throwNewRuntimeException(ErrorMessageEnum.OPERTAION_FAILED.toString(), ex);
            return null;
        } 
    }
    
    private void persistEntity(Object entity) {
        validateDbConnectivity();
        
        try {
            em.persist(entity);
        } catch(Exception ex) {
            throwNewRuntimeException(ErrorMessageEnum.OPERTAION_FAILED.toString(), ex);
        }
    }
    
    private void throwNewRuntimeException(String msg, Exception ex) {
        if(ex != null) {
            throw new AppRuntimeException(msg, ex);
        } else {
            throw new AppRuntimeException(msg);
        }
        
    }

    private void validateDbConnectivity() {
        String user = "root";
        String pass = "root";
        String dbUrl = "jdbc:derby://localhost:1527/myDB;create=true;user=" + user + ";password=" + pass;
        String driver = "org.apache.derby.jdbc.ClientDriver";

        try {
            Class.forName(driver).newInstance();
            try (Connection conn = DriverManager.getConnection(dbUrl)) {
                if(!conn.isValid(3)) {
                    throwNewRuntimeException(ErrorMessageEnum.NO_DB_CONNECTION.toString(), null);
                }
            }
        } catch(Exception ex) {
            throwNewRuntimeException(ErrorMessageEnum.NO_DB_CONNECTION.toString(), ex);
        }
    }
    
}
