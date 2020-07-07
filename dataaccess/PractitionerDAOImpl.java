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
 * PractitionerDAOImpl
 * @author Yazmin
 * @version 11/06/2020
 */

public class PractitionerDAOImpl extends UserMethodDAOImpl implements IPractitionerDAO {
    private final Connexion connexion;
    private Connection connection;
    private Statement consult;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    public PractitionerDAOImpl() {
        connexion = new Connexion ();
    }
    
    @Override //query
    public Practitioner getPractitioner (String enrollment) {
        Practitioner practitioner = new Practitioner ();
        try {
            connection = connexion.getConnection () ;
            String queryFoundPractitioner ="Select * from Practitioner, User," +
                    " Lapse WHERE Practitioner.idUser = User.idUser AND Practitioner.idLapse = Lapse.idLapse AND Practitioner.enrollment = ?";
            preparedStatement = connection.prepareStatement (queryFoundPractitioner);
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
                practitioner.setPeriod(resultSet.getString("lapse"));
            }
        }catch(SQLException ex){
            Logger.getLogger(PractitionerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return practitioner;
    }

    @Override //periodo
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

    @Override //cambiar al periodo actual
    public boolean recoverPractitioner (Practitioner practitioner) {
        boolean result = false;
        StatusDAOImpl statusDao = new StatusDAOImpl();
        int idUserStatus = statusDao.searchIdStatus(practitioner.getStatus());
        if(idUserStatus == Search.NOTFOUND.getValue() ){
            statusDao.addStatus(practitioner.getStatus());
            idUserStatus = statusDao.searchIdStatus(practitioner.getStatus());
        }
        try {
            connection = connexion.getConnection();
            preparedStatement =
                    connection.prepareStatement("UPDATE Practitioner, User, User_Status SET User_Status.idStatus=?" +
                            " WHERE Practitioner.idUser = User.idUser AND User_Status.idUser =" +
                            " User.idUser AND Practitioner.enrollment =? ");
            preparedStatement.setInt(1, idUserStatus);
            preparedStatement.setString(2, practitioner.getEnrollment());
            preparedStatement.executeUpdate();
            result = true;
        } catch(SQLException ex){
            Logger.getLogger(PractitionerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return result;
    }

    @Override
    public boolean deletePractitioner (String enrollment, String status) {
        boolean result = false;
        StatusDAOImpl statusDao = new StatusDAOImpl();
        int idUserStatus = statusDao.searchIdStatus(status);
        if(idUserStatus == Search.NOTFOUND.getValue()){
            statusDao.addStatus(status);
            idUserStatus = statusDao.searchIdStatus(status);
        }
        try{
            connection = connexion.getConnection();
            preparedStatement=connection.prepareStatement("UPDATE Practitioner, User, User_Status SET User_Status.idStatus=? WHERE" +
                    " Practitioner.idUser = User.idUser AND User_Status.idUser = User.idUser AND Practitioner.enrollment=?");
            preparedStatement.setInt(1,idUserStatus);
            preparedStatement.setString(2,enrollment);
            preparedStatement.executeUpdate();
            result = true;
        }catch(SQLException ex){
            Logger.getLogger(PractitionerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public boolean addPractitioner (Practitioner practitioner) {
        boolean resultAdd = false;
        boolean enrollment = searchEnrollment(practitioner.getEnrollment());
        if (!enrollment){
            LapseDAOImpl lapseDAO = new LapseDAOImpl();
            int idLapse = lapseDAO.searchLapse(practitioner.getPeriod());
            if(idLapse == Search.NOTFOUND.getValue()){
                lapseDAO.addLapse(practitioner.getPeriod());
                idLapse = lapseDAO.searchLapse(practitioner.getPeriod());
            }
            boolean userAdd= addUser(practitioner.getName(),practitioner.getLastName(),practitioner.getEmail(),practitioner.getAlternateEmail()
                    ,practitioner.getPhone(),practitioner.getPassword(), practitioner.getUserType(),practitioner.getStatus()
                    ,practitioner.getGender(),practitioner.getUserName());
            if (userAdd) {
                int idUser = searchIdUser(practitioner.getEmail(),practitioner.getAlternateEmail(),practitioner.getPhone());
                try{
                    connection = connexion.getConnection();
                    String queryAddPractitioner = "INSERT INTO Practitioner  (enrollment, idUser, idLapse ) VALUES ( ?, ?,?)";
                    PreparedStatement sentenceAdd = connection.prepareStatement(queryAddPractitioner);
                    sentenceAdd.setString(1, practitioner.getEnrollment());
                    sentenceAdd.setInt(2, idUser);
                    sentenceAdd.setInt(3, idLapse);
                    sentenceAdd.executeUpdate();
                    resultAdd = true;
                } catch(SQLException ex){
                    Logger.getLogger(PractitionerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    connexion.closeConnection();
                }
            }
        }
        return resultAdd;
    }

    private boolean searchEnrollment(String enrollmentSearch) {
        boolean search = false;
        try{
            connection = connexion.getConnection();
            String queryEnrollment= "Select enrollment from Practitioner where enrollment=?";
            preparedStatement =connection.prepareStatement(queryEnrollment);
            preparedStatement.setString(1,enrollmentSearch);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                search = true;
            }
        }catch(SQLException ex){
            Logger.getLogger(PractitionerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return search;
    }

    @Override 
    public List <Practitioner> getAllPractitioner () {
       List<Practitioner> practitioners = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            consult = connection.createStatement();
            resultSet = consult.executeQuery("Select enrollment,name,lastName FROM Practitioner INNER JOIN User ON Practitioner.idUser = User.idUser");
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
    public List <Practitioner> getPractitionersActive () {
        List<Practitioner> practitioners = new ArrayList<>();
        StatusDAOImpl statusDAO = new StatusDAOImpl();
        int idUserStatus = statusDAO.searchIdStatus("Active");
        if(idUserStatus == Search.NOTFOUND.getValue()){
            statusDAO.addStatus("Active");
            idUserStatus = statusDAO.searchIdStatus("Active");
        }
        try {
            connection = connexion.getConnection();
            String queryPractitionerActive = "Select name, lastName,enrollment from Practitioner,User, User_Status WHERE" +
                    " Practitioner.idUser = User.idUser AND User_Status.idStatus=? AND User_Status.idUser=User.idUser";
            preparedStatement =connection.prepareStatement(queryPractitionerActive);
            preparedStatement.setInt(1,idUserStatus);
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
    public List <Practitioner> getInformationPractitioner () {
        List<Practitioner> practitioners = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            consult = connection.createStatement();
            resultSet = consult.executeQuery("Select * from Practitioner INNER JOIN User ON Practitioner.idUser = User.idUser");
            while(resultSet.next()){
                Practitioner practitioner = new Practitioner();
                practitioner.setName(resultSet.getString("name"));
                practitioner.setLastName(resultSet.getString("lastName"));
                practitioner.setEmail(resultSet.getString("email"));
                practitioner.setAlternateEmail (resultSet.getString("alternateEmail"));
                practitioner.setPhone(resultSet.getString("phone"));
                practitioner.setEnrollment(resultSet.getString("enrollment"));
                //practitioner.setPeriod((resultSet.getString("lapse")));
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
    public boolean activePractitioner() {
        boolean isActive = false;
        StatusDAOImpl statusDAO = new StatusDAOImpl();
        int idUserStatus = statusDAO.searchIdStatus("Active");
        if(idUserStatus == Search.NOTFOUND.getValue()){
            statusDAO.addStatus("Active");
            idUserStatus = statusDAO.searchIdStatus("Active");
        }
        try {
            connection = connexion.getConnection();
            String queryActivePractitioner =
                    "SELECT enrollment from Practitioner, User, UserType, User_Status WHERE Practitioner.idUser= User.idUser AND" +
                            " User_Status.idUser = User.idUser AND UserType.type='Practitioner' AND User_Status.idStatus=?";
            preparedStatement = connection.prepareStatement(queryActivePractitioner);
            preparedStatement.setInt(1,idUserStatus);
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

    @Override
    public boolean arePractitioner() {
        boolean arePractitioner = false;
        try {
            connection = connexion.getConnection();
            String queryActivePractitioner =
                    "SELECT enrollment from Practitioner, User, UserType WHERE Practitioner.idUser= User.idUser" +
                            " AND UserType.type='Practitioner'";
            preparedStatement = connection.prepareStatement(queryActivePractitioner);
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
}