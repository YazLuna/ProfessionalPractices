package domain;

import java.util.List;
import dataaccess.ResponsibleProjectDAOImpl;
import dataaccess.ChargeDAOImpl;

/**
 * Class Responsible Project
 * @author MARTHA
 * @version 08/05/2020
 */
public class ResponsibleProject{
    private String name;
    private String lastName;
    private String email;
    private String charge;
    private String status;
    private int idResponsible;
    
    public ResponsibleProject(){
        this.status="available";
    }
    
    public String getName () {
        return name;
    }
    
    public void setName (String name) {
        this.name = name;
    }
    
    public String getLastName () {
        return lastName;
    }
    
    public void setLastName (String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail () {
        return email;
    }
    
    public void setEmail (String email) {
        this.email = email;
    }
    
    public String getCharge () {
        return charge;
    }
    
    public void setCharge (String charge) {
        this.charge = charge;
    }

    public String getStatus () {
        return status;
    }

    public void setStatus (String status) {
        this.status = status;
    }
    
    public int getIdResponsible () {
        return idResponsible;
    }
    
    public void setIdResponsible (int idResponsible) {
        this.idResponsible = idResponsible;
    }

    /**
     * Method for register the responsible of the project
     * @param responsibleProject define the data of the responsible of the project
     * @return if the responsible of the project was registered
     */
    public static boolean registerResponsibleProject(ResponsibleProject responsibleProject) {
        boolean isRegisterResponsibleProject;
        ResponsibleProjectDAOImpl responsibleProjectDAO = new ResponsibleProjectDAOImpl();
        isRegisterResponsibleProject = responsibleProjectDAO.addResponsibleProject(responsibleProject);
        return isRegisterResponsibleProject;
    }

    /**
     * Method for get the list of charge
     * @return The list of the charge
     */
    public static List<String> getListCharge() {
        ChargeDAOImpl chargeDAO = new ChargeDAOImpl();
        List<String> listCharge = chargeDAO.getAllCharge();
        return  listCharge;
    }

    /**
     * Method for get responsible project
     * @param  email define the email of the project
     * @return get the responsible project
     */
    public static ResponsibleProject getResponsibleProject(String email) {
        ResponsibleProjectDAOImpl responsibleProjectDAO = new ResponsibleProjectDAOImpl();
        ResponsibleProject responsible = responsibleProjectDAO.getResponsibleProject(email);
        return responsible;
    }

    /**
     * Method for get the list of responsible of the project
     * @return The list of the responsible project of the project
     */
    public static List<ResponsibleProject> getListResponsibleProject() {
        ResponsibleProjectDAOImpl responsibleProjectDAO = new ResponsibleProjectDAOImpl();
        List<ResponsibleProject> listResponsiblesProject = responsibleProjectDAO.getAllResponsible();
        return listResponsiblesProject;
    }

    /**
     * Method for get the list of responsible of the project available
     * @return The list of the responsible of the project available
     */
    public static List<ResponsibleProject> getListResponsibleProjectAvailable() {
        ResponsibleProjectDAOImpl responsibleProjectDAO = new ResponsibleProjectDAOImpl();
        List<ResponsibleProject> listResponsiblesProject = responsibleProjectDAO.getAllResponsibleAvailable();
        return listResponsiblesProject;
    }

    /**
     * Method for get the list of responsible of the project available not assign
     * @return The list of the responsible of the project available not assign
     */
    public static List<ResponsibleProject> getListResponsibleProjectAvailableNotAssign() {
        ResponsibleProjectDAOImpl responsibleProjectDAO = new ResponsibleProjectDAOImpl();
        List<ResponsibleProject> listResponsiblesProject = responsibleProjectDAO.getAllResponsibleAvailableNotAssing();
        return listResponsiblesProject;
    }

    /**
     * Method for modify the responsible of the project
     * @param responsibleProject define the data of the responsible of the project
     * @param datesUpdate the fields to modify
     * @return if the responsible of the project was modify
     */
    public static boolean modifyResponsibleProject(ResponsibleProject responsibleProject,List<String> datesUpdate) {
        boolean isModifyResponsibleProject;
        ResponsibleProjectDAOImpl responsibleProjectDAO = new ResponsibleProjectDAOImpl();
        isModifyResponsibleProject = responsibleProjectDAO.modifyResponsibleProject(responsibleProject,datesUpdate);
        return isModifyResponsibleProject;
    }

    /**
     * Method for delete responsible of the project
     * @param email define the email of the responsible of the project
     * @return if the responsible of the project was deleted
     */
    public static boolean deleteResponsibleProject(String email) {
        boolean isDeleteResponsibleProject;
        ResponsibleProjectDAOImpl responsibleProjectDAO = new ResponsibleProjectDAOImpl();
        isDeleteResponsibleProject = responsibleProjectDAO.deleteResponsibleProject(email);
        return isDeleteResponsibleProject;
    }

    /**
     * Method for validate repeat of the responsible of the project
     * @param email define the email of the responsible of the project
     * @return if valid the responsible of the project
     */
    public static boolean validateRepeatResponsibleProject(String email) {
        boolean isValidateRepeatResponsibleProject;
        ResponsibleProjectDAOImpl responsibleProjectDAO = new ResponsibleProjectDAOImpl();
        isValidateRepeatResponsibleProject = responsibleProjectDAO.validateRepeatResponsibleProject(email);
        return isValidateRepeatResponsibleProject;
    }

    /**
     * Method to know if there are responsible of the project
     * @return If there is a responsible of the project
     */
    public static boolean thereAreResponsibleProject () {
        ResponsibleProjectDAOImpl responsibleProjectDAO = new ResponsibleProjectDAOImpl();
        boolean thereAreResponsible = responsibleProjectDAO.thereAreResponsibleProject();
        return thereAreResponsible;
    }

    /**
     * Method to know if there are responsible of the project available
     * @return If there is a responsible of the project available
     */
    public static boolean thereAreResponsibleProjectAvailable () {
        ResponsibleProjectDAOImpl responsibleProjectDAO = new ResponsibleProjectDAOImpl();
        boolean thereAreResponsibleAvailable = responsibleProjectDAO.thereAreResponsibleProjectAvailable();
        return thereAreResponsibleAvailable;
    }

    /**
     * Method to know if there are responsible of the project available not assign
     * @return If there is a responsible of the project available not assign
     */
    public static boolean thereAreResponsibleProjectAvailableNotAssing () {
        ResponsibleProjectDAOImpl responsibleProjectDAO = new ResponsibleProjectDAOImpl();
        boolean thereAreResponsibleAvailable = responsibleProjectDAO.thereAreResponsibleProjectAvailableNotAssing();
        return thereAreResponsibleAvailable;
    }
}
