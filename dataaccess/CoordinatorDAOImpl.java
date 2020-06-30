package dataaccess;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
 * @version 26/06/2020
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
    public Coordinator getCoordinator() {
        Coordinator coordinator = new Coordinator();
        int idUserStatus = searchIdUserStatus("Active");
        try {
            connection = connexion.getConnection();
            String queryGetCoordinator = "SELECT name,lastName,gender,email,alternateEmail,phone,profilePicture,staffNumber,registrationDate from Coordinator, User" +
                    ", UserType, Status, User_Status WHERE Coordinator.idUser=User.idUser AND User_Status.idUser = User.idUser AND" +
                    " UserType.type='Coordinator' AND User_Status.idStatus=?";
            preparedStatement = connection.prepareStatement(queryGetCoordinator);
            preparedStatement.setInt(1,idUserStatus);
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
                //image
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
            String queryGetCoordinatorSelected =
                    "SELECT name,lastName,gender,email,alternateEmail,phone,profilePicture,staffNumber,status from " +
                            "Coordinator, User, UserType,Status,User_Status WHERE Coordinator.idUser=User.idUser AND" +
                            " UserType.type='Coordinator' AND Coordinator.staffNumber=? AND User_Status.idUser =" +
                            " User.idUser AND User_Status.idStatus = Status.idStatus";
            preparedStatement = connection.prepareStatement(queryGetCoordinatorSelected);
            preparedStatement.setInt(1,staffNumber);
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
                //image
            }
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return coordinator;
    }

    @Override
    public boolean updateCoordinator(int staffNumberOrigin, Coordinator coordinatorEdit, String tableOne, String tableTwo
            , String unify, List<String>DatesUpdate, String condition) {
        boolean result = validateInformationUpdate(coordinatorEdit);
        if (result) {
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
            String sentence = "UPDATE "  + tableOne +  " INNER JOIN "  + tableTwo + " ON "  +unify + " SET "  +datesUpdate + " WHERE "  +condition +staffNumberOrigin;
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
                        //Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, e);
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
            } catch (SQLException | NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
                Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                connexion.closeConnection();
            }
        }
        return result;
    }

    private boolean validateInformationUpdate(Coordinator coordinatorEdit) {
        boolean validate = false;
        int staffNumberFound = searchStaffNumber(coordinatorEdit.getStaffNumber());
        if(staffNumberFound == Search.NOTFOUND.getValue() ) {
            validate = validateUserUpdate(coordinatorEdit.getEmail(), coordinatorEdit.getAlternateEmail(),
                    coordinatorEdit.getPhone());
        }
        return  validate;
    }

    @Override
    public boolean deleteCoordinator(String status, String dischargeDate) {
        boolean result = false;
        StatusDAOImpl statusDao = new StatusDAOImpl();
        int idUserStatus = statusDao.searchIdStatus(status);
        if(idUserStatus == Search.NOTFOUND.getValue()){
            statusDao.addStatus(status);
            idUserStatus = statusDao.searchIdStatus(status);
        }
        try {
            connection = connexion.getConnection();
            preparedStatement =
                    connection.prepareStatement("UPDATE Coordinator, User, User_Status SET User_Status.idStatus=?" +
                            ", Coordinator.dischargeDate=? WHERE Coordinator.idUser = User.idUser AND User_Status.idUser = User.idUser");
            preparedStatement.setInt(1, idUserStatus);
            preparedStatement.setString(2, dischargeDate);
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
    public boolean addCoordinator(Coordinator coordinator) {
        boolean resultAdd = false;
        boolean validate = validateCoordinator(coordinator);
        if(validate){
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
        }
        return resultAdd;
    }

    private boolean validateCoordinator(Coordinator coordinator) {
        boolean activeCoordinator= coordinator.activeCoordinator();
        boolean validation= false;
        if(!activeCoordinator){
            int searchStaffNumber = searchStaffNumber(coordinator.getStaffNumber());
            if(searchStaffNumber != Search.NOTFOUND.getValue()){
                int isTeacherSearchStaffNumber = searchStaffNumberTeacher(coordinator.getStaffNumber());
                if(isTeacherSearchStaffNumber != Search.NOTFOUND.getValue()){
                    addRelations(coordinator.getEmail(),coordinator.getAlternateEmail(),coordinator.getPhone(),coordinator.getStatus(),coordinator.getUserType());
                    validation = true;
                }
            }
            if(searchStaffNumber == Search.NOTFOUND.getValue()) {
                boolean addUserValidate = addUser(coordinator.getName(), coordinator.getLastName(), coordinator.getEmail(), coordinator.getAlternateEmail(),
                        coordinator.getPhone(), coordinator.getPassword(), coordinator.getUserType(),
                        coordinator.getStatus(), coordinator.getGender(), coordinator.getUserName(),coordinator.getProfilePicture());
                if(addUserValidate){
                    validation = true;
                }
            }
        }
        return validation;
    }

    @Override
    public List<Coordinator> getAllCoordinator() {
        List<Coordinator> coordinators = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            Statement consult = connection.createStatement();
            resultSet = consult.executeQuery("Select name, lastName,staffNumber from Coordinator INNER JOIN User ON Coordinator.idUser =" +
                    " User.idUser");
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
    public List<Coordinator> getInformationAllCoordinator() {
        List<Coordinator> coordinators = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            Statement consult = connection.createStatement();
            resultSet = consult.executeQuery("Select name, lastName,staffNumber,email,alternateEmail,phone,status from" +
                    " Coordinator,User, User_Status,Status WHERE Coordinator.idUser = User.idUser AND" +
                    " User_Status.idUser=User.idUser AND User_Status.idStatus= Status.idStatus");
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

    @Override //usertype sea coordinador
    public boolean recoverCoordinator(Coordinator coordinator) {
        boolean result = false;
        if(!coordinator.activeCoordinator()){
            StatusDAOImpl statusDao = new StatusDAOImpl();
            int idUserStatus = statusDao.searchIdStatus(coordinator.getStatus());
            if(idUserStatus == Search.NOTFOUND.getValue() ){
                statusDao.addStatus(coordinator.getStatus());
                idUserStatus = statusDao.searchIdStatus(coordinator.getStatus());
            }
            try {
                connection = connexion.getConnection();
                preparedStatement =
                        connection.prepareStatement("UPDATE Coordinator, User, User_Status SET User_Status.idStatus=?" +
                                ", Coordinator.dischargeDate=? WHERE Coordinator.idUser = User.idUser AND User_Status.idUser =" +
                                " User.idUser AND Coordinator.staffNumber =? ");
                preparedStatement.setInt(1, idUserStatus);
                preparedStatement.setString(2, null);
                preparedStatement.setInt(3, coordinator.getStaffNumber());
                preparedStatement.executeUpdate();
                result = true;
            } catch (SQLException ex) {
                Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                connexion.closeConnection();
            }
        }
        return result;
    }

    @Override
    public boolean activeCoordinator() {
        boolean isActive = false;
        StatusDAOImpl statusDAO = new StatusDAOImpl();
        int idUserStatus = statusDAO.searchIdStatus("Active");
        if(idUserStatus == Search.NOTFOUND.getValue()){
            statusDAO.addStatus("Active");
            idUserStatus = statusDAO.searchIdStatus("Active");
        }
        try {
            connection = connexion.getConnection();
            String queryActiveCoordinator =
                    "SELECT staffNumber from Coordinator, User, UserType, Status, User_Status WHERE Coordinator.idUser= User.idUser AND" +
                    " User_Status.idUser = User.idUser AND UserType.type='Coordinator' AND User_Status.idStatus=?";
            preparedStatement = connection.prepareStatement(queryActiveCoordinator);
            preparedStatement.setInt(1,idUserStatus);
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
            String queryActiveCoordinator =
                    "SELECT staffNumber from Coordinator, User, UserType WHERE Coordinator.idUser= User.idUser" +
                            " AND UserType.type='Coordinator'";
            preparedStatement = connection.prepareStatement(queryActiveCoordinator);
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