package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import domain.Practitioner;
import domain.Search;

/**
 * Practitioner DAO Implements
 * @author Yazmin
 * @version 09/07/2020
 */
public class PractitionerDAOImpl extends UserMethodDAOImpl implements IPractitionerDAO {
    private final Connexion connexion;
    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    /**
     * Constructor PractitionerDAOImpl class
     */
    public PractitionerDAOImpl() {
        connexion = new Connexion ();
    }

    /**
     * Method for adding a practitioner
     * @param practitioner Object to add
     * @return true if successful false if not
     */
    @Override
    public boolean addPractitioner (Practitioner practitioner) {
        boolean resultAdd = false;
        TermDAOImpl TermDAO = new TermDAOImpl();
        int idTerm = TermDAO.searchTerm(practitioner.getTerm());
        if(idTerm == Search.NOTFOUND.getValue()){
            TermDAO.addTerm(practitioner.getTerm());
            idTerm = TermDAO.searchTerm(practitioner.getTerm());
        }
        int idUser = searchIdUser(practitioner.getEmail(),practitioner.getAlternateEmail(),practitioner.getPhone());
        try{
            connection = connexion.getConnection();
            String queryAddPractitioner = "INSERT INTO Practitioner (enrollment, idUser, idTerm, credits) VALUES (?,?,?,?)";
            preparedStatement = connection.prepareStatement(queryAddPractitioner);
            preparedStatement.setString(1, practitioner.getEnrollment());
            preparedStatement.setInt(2, idUser);
            preparedStatement.setInt(3, idTerm);
            preparedStatement.setInt(4, practitioner.getCredits());
            preparedStatement.executeUpdate();
            resultAdd = true;
        } catch(SQLException ex){
            Logger.getLogger(PractitionerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return resultAdd;
    }

    /**
     * Method to obtain a practitioner according to their enrollment
     * @param enrollment from practitioner
     * @return Practitioner Object
     */
    @Override
    public Practitioner getPractitioner (String enrollment) {
        Practitioner practitioner = new Practitioner ();
        try {
            connection = connexion.getConnection () ;
            String queryGetPractitioner ="SELECT * FROM Practitioner, User," +
                    " Term WHERE Practitioner.idUser = User.idUser AND Practitioner.idTerm = Term.idTerm AND Practitioner.enrollment = ?";
            preparedStatement = connection.prepareStatement (queryGetPractitioner);
            preparedStatement.setString (1,enrollment);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                practitioner.setName(resultSet.getString("name"));
                practitioner.setLastName(resultSet.getString("lastName"));
                practitioner.setGender(resultSet.getInt("gender"));
                practitioner.setStatus(resultSet.getString("status"));
                practitioner.setEmail(resultSet.getString("email"));
                practitioner.setAlternateEmail(resultSet.getString("alternateEmail"));
                practitioner.setPhone(resultSet.getString("phone"));
                practitioner.setEnrollment(resultSet.getString("enrollment"));
                practitioner.setTerm(resultSet.getString("Term"));
            }
        }catch(SQLException ex){
            Logger.getLogger(PractitionerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return practitioner;
    }

    /**
     * Method of obtaining the list of practitioner
     * @return Practitioner List
     */
    @Override
    public List <Practitioner> getPractitioners() {
        List<Practitioner> practitioners = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            Statement consult = connection.createStatement();
            resultSet = consult.executeQuery("SELECT name, lastName, enrollment FROM Practitioner INNER JOIN User ON" +
                    " Practitioner.idUser = User.idUser");
            while(resultSet.next()){
                Practitioner practitioner = new Practitioner();
                practitioner.setName(resultSet.getString("name"));
                practitioner.setLastName(resultSet.getString("lastName"));
                practitioner.setEnrollment(resultSet.getString("enrollment"));
                practitioners.add(practitioner);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PractitionerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return practitioners;
    }

    /**
     * Method of obtaining the list of active practitioners
     * @return List of active practitioners
     */
    @Override
    public List <Practitioner> getPractitionersActive () {
        List<Practitioner> practitioners = new ArrayList<>();
        StatusDAOImpl statusDAO = new StatusDAOImpl();
        int idUserStatus = statusDAO.searchIdStatus("Active");
        int idUserType = searchIdUserType("Practitioner");
        try {
            connection = connexion.getConnection();
            String queryPractitionerActive = "SELECT name, lastName, enrollment FROM Practitioner, User, User_Status WHERE" +
                    " Practitioner.idUser = User.idUser AND User_Status.idStatus =? AND" +
                    " User_Status.idUser = User.idUser AND idUserType =?";
            preparedStatement =connection.prepareStatement(queryPractitionerActive);
            preparedStatement.setInt(1, idUserStatus);
            preparedStatement.setInt(2,idUserType);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Practitioner practitioner = new Practitioner();
                practitioner.setName(resultSet.getString("name"));
                practitioner.setLastName(resultSet.getString("lastName"));
                practitioner.setEnrollment(resultSet.getString("enrollment"));
                practitioners.add(practitioner);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PractitionerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return practitioners;
    }

    @Override
    public List <Practitioner> getPractitionersInformation() {
        List<Practitioner> practitioners = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            //consult = connection.createStatement();
            //resultSet = consult.executeQuery("Select * from Practitioner INNER JOIN User ON Practitioner.idUser = User.idUser");
            while(resultSet.next()){
                Practitioner practitioner = new Practitioner();
                practitioner.setName(resultSet.getString("name"));
                practitioner.setLastName(resultSet.getString("lastName"));
                practitioner.setEmail(resultSet.getString("email"));
                practitioner.setAlternateEmail (resultSet.getString("alternateEmail"));
                practitioner.setPhone(resultSet.getString("phone"));
                practitioner.setEnrollment(resultSet.getString("enrollment"));
                //practitioner.setPeriod((resultSet.getString("Term")));
                //practitioner.setStatus(resultSet.getString("status"));
                practitioners.add(practitioner);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PractitionerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return practitioners;
    }

    @Override
    public boolean updatePractitioner (String enrollment, Practitioner practitionerEdit) {
        boolean result = false;
        try {
            connection = connexion.getConnection();
            preparedStatement = connection.prepareStatement ("UPDATE Practitioner INNER JOIN User ON Practitioner.idUser = User.idUser SET User.name = ?, User.lastName = ?, User.gender = ?, User.email = ?,"
                    + " User.alternateEmail = ?, User.phone = ?, Practitioner.enrollment = ?   WHERE  Practitioner.enrollment = ?");
            preparedStatement.setString(1, practitionerEdit.getName());
            preparedStatement.setString(2, practitionerEdit.getLastName());
            preparedStatement.setInt(3, practitionerEdit.getGender());
            preparedStatement.setString(4, practitionerEdit.getEmail());
            preparedStatement.setString(5, practitionerEdit.getAlternateEmail());
            preparedStatement.setString(6, practitionerEdit.getPhone());
            preparedStatement.setString(7, practitionerEdit.getEnrollment());
            preparedStatement.setString(8, enrollment);
            preparedStatement.executeUpdate();
            result = true;
        } catch(SQLException ex){
            Logger.getLogger(PractitionerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return result;
    }

    /**
     * Method to recover a deleted practitioner
     * @param enrollment from Teacher
     * @return True if recover, false if not
     */
    @Override
    public boolean recoverPractitioner (String enrollment) {
        boolean result = false;
        StatusDAOImpl statusDao = new StatusDAOImpl();
        int idUserStatus = statusDao.searchIdStatus("Active");
        int idUserType = searchIdUserType("Practitioner");
        try {
            connection = connexion.getConnection();
            String queryRecoverPractitioner = "UPDATE Practitioner INNER JOIN User_Status SET User_Status.idStatus =? WHERE" +
                    " User_Status.idUser = Practitioner.idUser AND Practitioner.enrollment =? AND User_Status.idUserType =?";
            preparedStatement = connection.prepareStatement(queryRecoverPractitioner);
            preparedStatement.setInt(1, idUserStatus);
            preparedStatement.setString(2, enrollment);
            preparedStatement.setInt(3,idUserType);
            preparedStatement.executeUpdate();
            result = true;
        } catch(SQLException ex){
            Logger.getLogger(PractitionerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return result;
    }

    /**
     * Method to delete a practitioner
     * @param status Inactive
     * @param enrollment from Practitioner
     * @return True if delete, False if not
     */
    @Override
    public boolean deletePractitioner (String enrollment, String status) {
        boolean result = false;
        StatusDAOImpl statusDao = new StatusDAOImpl();
        int idUserStatus = statusDao.searchIdStatus(status);
        int idUserType = searchIdUserType("Practitioner");
        try {
            connection = connexion.getConnection();
            String queryDeletePractitioner = "UPDATE Practitioner INNER JOIN User_Status SET User_Status.idStatus =? WHERE" +
                    " User_Status.idUser = Practitioner.idUser AND Practitioner.enrollment =? AND User_Status.idUserType =?";
            preparedStatement = connection.prepareStatement(queryDeletePractitioner);
            preparedStatement.setInt(1, idUserStatus);
            preparedStatement.setString(2, enrollment);
            preparedStatement.setInt(3, idUserType);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException ex) {
            Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return result;
    }

    /**
     * Method to know if there is at least one active practitioner
     * @return true if there is at least one active practitioner, false if not
     */
    @Override
    public boolean activePractitioner() {
        boolean isActive = false;
        StatusDAOImpl statusDAO = new StatusDAOImpl();
        int idUserStatus = statusDAO.searchIdStatus("Active");
        try {
            connection = connexion.getConnection();
            String queryActivePractitioner = "SELECT enrollment FROM Practitioner, UserType, User_Status, User_UserType WHERE" +
                    " User_Status.idUser = Practitioner.idUser AND UserType.type=? AND User_UserType.idUser = " +
                    " Practitioner.idUser AND User_UserType.idUserType = UserType.idUserType AND" +
                    " User_Status.idUserType = UserType.idUserType AND User_Status.idStatus =?";
            preparedStatement = connection.prepareStatement(queryActivePractitioner);
            preparedStatement.setString(1, "Practitioner");
            preparedStatement.setInt(2, idUserStatus);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                isActive = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TeacherDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return isActive;
    }

    /**
     * Method to know if are practitioners
     * @return True if are practitioners, false if not
     */
    @Override
    public boolean arePractitioners() {
        boolean arePractitioner = false;
        try {
            connection = connexion.getConnection();
            String queryArePractitioner = "SELECT enrollment FROM Practitioner, UserType, User_UserType WHERE UserType.type=? AND" +
                    " User_UserType.idUser = Practitioner.idUser AND User_UserType.idUserType = UserType.idUserType";
            preparedStatement = connection.prepareStatement(queryArePractitioner);
            preparedStatement.setString(1, "Practitioner");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                arePractitioner = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PractitionerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return arePractitioner;
    }

    /**
     * Method to know if the data of a practitioner to be added is valid
     * @param practitioner object to add
     * @return true if is valid, false if not
     */
    @Override
    public boolean validPractitionerAdd(Practitioner practitioner) {
        boolean validUserPractitioner = validateUserAdd(practitioner.getEmail(), practitioner.getAlternateEmail()
                , practitioner.getPhone(), practitioner.getUserName());
        if (validUserPractitioner) {
            System.out.println("Usuario valido"+validUserPractitioner);
            validUserPractitioner = validEnrollment(practitioner.getEnrollment());
            System.out.println("matricula valido"+validUserPractitioner);
        }
        return validUserPractitioner;
    }

    /**
     * Method to know if the data of a practitioner to be update is valid
     * @param practitioner object to update
     * @return true if is valid, false if not
     */
    @Override
    public boolean validPractitionerUpdate(Practitioner practitioner) {
        boolean validUserPractitioner = validateUser(practitioner.getEmail(), practitioner.getAlternateEmail(), practitioner.getPhone());
        if (validUserPractitioner) {
            validUserPractitioner = validEnrollment(practitioner.getEnrollment());
        }
        return validUserPractitioner;
    }

    private boolean validEnrollment(String enrollmentSearch) {
        boolean validEnrollment = true;
        try{
            connection = connexion.getConnection();
            String queryEnrollment= "SELECT enrollment FROM Practitioner WHERE enrollment=?";
            preparedStatement =connection.prepareStatement(queryEnrollment);
            preparedStatement.setString(1,enrollmentSearch);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                validEnrollment = false;
            }
        }catch(SQLException ex){
            Logger.getLogger(PractitionerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return validEnrollment;
    }
}