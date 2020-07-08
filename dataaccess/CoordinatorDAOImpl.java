package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import domain.Coordinator;
import domain.Search;

/**
 * CoordinatorDAOImpl
 * @author Yazmin
 * @version 07/07/2020
 */

public class CoordinatorDAOImpl extends UserMethodDAOImpl implements ICoordinatorDAO {
    private final Connexion connexion;
    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    public CoordinatorDAOImpl() {
        connexion = new Connexion();
    }

    @Override
    public boolean addCoordinator(Coordinator coordinator) {
        boolean resultAdd = false;
        int idUser = searchIdUser(coordinator.getEmail(),coordinator.getAlternateEmail(),coordinator.getPhone());
        try {
            connection = connexion.getConnection();
            String queryAddCoordinator = "INSERT INTO Coordinator (staffNumber, registrationDate, idUser) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(queryAddCoordinator);
            preparedStatement.setInt(1, coordinator.getStaffNumber());
            preparedStatement.setString(2, coordinator.getRegistrationDate());
            preparedStatement.setInt(3, idUser);
            preparedStatement.executeUpdate();
            resultAdd = true;
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
    return resultAdd;
    }

    @Override
    public Coordinator getCoordinator() {
        Coordinator coordinator = new Coordinator();
        try {
            connection = connexion.getConnection();
            String queryGetCoordinator = "SELECT name, lastName, gender, email, alternateEmail, phone, staffNumber, status" +
                    ", registrationDate FROM Coordinator, User, UserType,Status,User_Status WHERE" +
                    " Coordinator.idUser=User.idUser AND UserType.type=? AND Status.idStatus =" +
                    " User_Status.idStatus AND User_Status.idUser = User.idUser AND" +
                    " User_Status.idUserType = UserType.idUserType";
            preparedStatement = connection.prepareStatement(queryGetCoordinator);
            preparedStatement.setString(1, "Coordinator");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                coordinator.setName(resultSet.getString("name"));
                coordinator.setLastName(resultSet.getString("lastName"));
                coordinator.setGender(resultSet.getInt("gender"));
                coordinator.setEmail(resultSet.getString("email"));
                coordinator.setAlternateEmail(resultSet.getString("alternateEmail"));
                coordinator.setPhone(resultSet.getString("phone"));
                coordinator.setStaffNumber(resultSet.getInt("staffNumber"));
                coordinator.setRegistrationDate(resultSet.getString("registrationDate"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return coordinator;
    }

    @Override
    public Coordinator getCoordinatorSelected(int staffNumber) {
        Coordinator coordinator = new Coordinator();
        try {
            connection = connexion.getConnection();
            String queryGetCoordinatorSelected = "SELECT name, lastName, gender, email, alternateEmail, phone, staffNumber " +
                    ", status FROM Coordinator, User, UserType, Status, User_Status WHERE Coordinator.idUser=User.idUser AND" +
                    " UserType.type=? AND Coordinator.staffNumber=? AND Status.idStatus = User_Status.idStatus AND " +
                    " User_Status.idUser = User.idUser AND User_Status.idUserType = UserType.idUserType";
            preparedStatement = connection.prepareStatement(queryGetCoordinatorSelected);
            preparedStatement.setString(1, "Coordinator");
            preparedStatement.setInt(2, staffNumber);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                coordinator.setName(resultSet.getString("name"));
                coordinator.setLastName(resultSet.getString("lastName"));
                coordinator.setGender(resultSet.getInt("gender"));
                coordinator.setEmail(resultSet.getString("email"));
                coordinator.setAlternateEmail(resultSet.getString("alternateEmail"));
                coordinator.setPhone(resultSet.getString("phone"));
                coordinator.setStaffNumber(resultSet.getInt("staffNumber"));
                coordinator.setStatus(resultSet.getString("status"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return coordinator;
    }

    @Override
    public List<Coordinator> getCoordinators() {
        List<Coordinator> coordinators = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            Statement consult = connection.createStatement();
            resultSet = consult.executeQuery("SELECT name, lastName, staffNumber FROM Coordinator INNER JOIN User ON" +
                    " Coordinator.idUser = User.idUser");
            while (resultSet.next()) {
                Coordinator coordinator = new Coordinator();
                coordinator.setName(resultSet.getString("name"));
                coordinator.setLastName(resultSet.getString("lastName"));
                coordinator.setStaffNumber(resultSet.getInt("staffNumber"));
                coordinators.add(coordinator);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return coordinators;
    }

    @Override
    public List<Coordinator> getCoordinatorsInformation() {
        List<Coordinator> coordinators = new ArrayList<>();
        int idUserType = searchIdUserType("Coordinator");
        try {
            connection = connexion.getConnection();
            String queryGetAllInformation = "SELECT name, lastName, staffNumber, email, alternateEmail, phone, status FROM Coordinator" +
                    ", User, User_Status,Status WHERE Coordinator.idUser = User.idUser AND User_Status.idUser = User.idUser AND" +
                    " User_Status.idUser = User.idUser AND Status.idStatus = User_Status.idStatus AND idUserType =?";
            preparedStatement = connection.prepareStatement(queryGetAllInformation);
            preparedStatement.setInt(1, idUserType);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Coordinator coordinator = new Coordinator();
                coordinator.setName(resultSet.getString("name"));
                coordinator.setLastName(resultSet.getString("lastName"));
                coordinator.setStaffNumber(resultSet.getInt("staffNumber"));
                coordinator.setEmail(resultSet.getString("email"));
                coordinator.setAlternateEmail(resultSet.getString("alternateEmail"));
                coordinator.setPhone(resultSet.getString("phone"));
                coordinator.setStatus(resultSet.getString("status"));
                coordinators.add(coordinator);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return coordinators;
    }

    @Override
    public boolean updateCoordinator(int staffNumberOrigin, Coordinator coordinatorEdit, List<String>DatesUpdate) {
        boolean result = false;
        String datesUpdate = DatesUpdate.get(0)+ "= ?, ";
        List<String> Change = new ArrayList<>();
        Change.add("get"+DatesUpdate.get(0));
        for (int indexDatesUpdate = 1 ; indexDatesUpdate < DatesUpdate.size();  indexDatesUpdate ++) {
            if ( indexDatesUpdate == DatesUpdate.size() -1){
                datesUpdate = datesUpdate + DatesUpdate.get(indexDatesUpdate)  + "= ?";
            } else {
                datesUpdate = datesUpdate + DatesUpdate.get( indexDatesUpdate)  + "= ?,";
            }
            Change.add("get"+DatesUpdate.get( indexDatesUpdate));
        }
        String sentence = "UPDATE Coordinator INNER JOIN User ON Coordinator.idUser = User.idUser SET " +datesUpdate+
                " WHERE Coordinator.staffNumber = " +staffNumberOrigin;
        try{
            connection = connexion.getConnection();
            preparedStatement = connection.prepareStatement(sentence);
            Class classCoordinator = coordinatorEdit.getClass();
            for(int indexPreparedStatement = 1 ; indexPreparedStatement <= DatesUpdate.size(); indexPreparedStatement++){
                Method methodCoordinator;
                boolean isString = true;
                try {
                    methodCoordinator = classCoordinator.getMethod(Change.get(indexPreparedStatement - 1));
                    String isWord = (String) methodCoordinator.invoke(coordinatorEdit, new Object[] {});
                } catch (ClassCastException e) {
                    isString = false;
                }
                if(isString){
                    methodCoordinator = classCoordinator.getMethod(Change.get(indexPreparedStatement - 1));
                    String word = (String) methodCoordinator.invoke(coordinatorEdit, new Object[] {});
                    preparedStatement.setString(indexPreparedStatement,word);
                } else{
                    methodCoordinator = classCoordinator.getMethod(Change.get(indexPreparedStatement - 1));
                    int integer = (int) methodCoordinator.invoke(coordinatorEdit, new Object[] {});
                    preparedStatement.setInt(indexPreparedStatement, integer);
                }
            }
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException | ReflectiveOperationException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return result;
    }

    @Override
    public boolean recoverCoordinator(int staffNumber) {
        boolean result = false;
        StatusDAOImpl statusDao = new StatusDAOImpl();
        int idUserStatus = statusDao.searchIdStatus("Active");
        int idUserType = searchIdUserType("Coordinator");
        try {
            connection = connexion.getConnection();
            String queryRecoverCoordinator = "UPDATE Coordinator INNER JOIN User_Status SET User_Status.idStatus=?" +
                    ", Coordinator.dischargeDate =? WHERE User_Status.idUser = Coordinator.idUser AND" +
                    " Coordinator.staffNumber =? AND User_Status.idUserType =?";
            connection.prepareStatement(queryRecoverCoordinator);
            preparedStatement.setInt(1, idUserStatus);
            preparedStatement.setString(2, null);
            preparedStatement.setInt(3, staffNumber);
            preparedStatement.setInt(4, idUserType);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return result;
    }

    @Override
    public boolean deleteCoordinator(String status, String dischargeDate) {
        boolean result = false;
        StatusDAOImpl statusDao = new StatusDAOImpl();
        int idUserStatus = statusDao.searchIdStatus(status);
        int idUserType = searchIdUserType("Coordinator");
        try {
            connection = connexion.getConnection();
            String queryDeleteCoordinator = "UPDATE Coordinator INNER JOIN User_Status SET User_Status.idStatus=?" +
                    ", Coordinator.dischargeDate =? WHERE User_Status.idUser = Coordinator.idUser AND" +
                    " User_Status.idUserType =?";
            connection.prepareStatement(queryDeleteCoordinator);
            preparedStatement.setInt(1, idUserStatus);
            preparedStatement.setString(2, dischargeDate);
            preparedStatement.setInt(3,idUserType);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return result;
    }

    @Override
    public boolean activeCoordinator() {
        boolean isActive = false;
        StatusDAOImpl statusDAO = new StatusDAOImpl();
        int idUserStatus = statusDAO.searchIdStatus("Active");
        try {
            connection = connexion.getConnection();
            String queryActiveCoordinator = "SELECT staffNumber FROM Coordinator, UserType, User_Status, User_UserType WHERE" +
                    " User_Status.idUser = Coordinator.idUser AND UserType.type=? AND User_UserType.idUser =" +
                    " Coordinator.idUser AND User_UserType.idUserType = UserType.idUserType AND" +
                    " User_Status.idUserType = UserType.idUserType AND User_Status.idStatus =?";
            preparedStatement = connection.prepareStatement(queryActiveCoordinator);
            preparedStatement.setString(1, "Coordinator");
            preparedStatement.setInt(2, idUserStatus);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                isActive = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return isActive;
    }

    @Override
    public boolean areCoordinator() {
        boolean areCoordinator = false;
        try {
            connection = connexion.getConnection();
            String queryActiveCoordinator = "SELECT staffNumber FROM Coordinator, UserType, User_Status, User_UserType WHERE" +
                    " User_Status.idUser = Coordinator.idUser AND UserType.type =? AND User_UserType.idUser = " +
                    "Coordinator.idUser AND User_UserType.idUserType = UserType.idUserType AND " +
                    "User_Status.idUserType = UserType.idUserType";
            preparedStatement = connection.prepareStatement(queryActiveCoordinator);
            preparedStatement.setString(1, "Coordinator");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                areCoordinator = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return areCoordinator;
    }
}