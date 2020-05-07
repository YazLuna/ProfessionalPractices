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
import domain.Coordinator;

public class CoordinatorDAOImpl implements ICoordinatorDAO {
    private final Connexion connexion;
    private Connection connection;
    private ResultSet result;

    public CoordinatorDAOImpl() {
        connexion = new Connexion();
    }

    @Override
    public Coordinator getCoordinator() throws SQLException {
        Coordinator coordinator = new Coordinator();
        try {
            connection = connexion.getConnection();
            String queryGetCoordinator = "SELECT * from Coordinator, User, UserTypeStatus WHERE Coordinator.idUser = " +
                    "User.idUser AND UserTypeStatus.status = 'Active'";
            PreparedStatement sentence = connection.prepareStatement(queryGetCoordinator);
            result = sentence.executeQuery();
            while (result.next()) {
                coordinator.setName(result.getString("name"));
                coordinator.setLastName(result.getString("lastName"));
                coordinator.setGender(result.getInt("gender"));
                coordinator.setEmail(result.getString("email"));
                coordinator.setAlternateEmail(result.getString("alternateEmail"));
                coordinator.setPhone(result.getString("phone"));
                coordinator.setStaffNumber(result.getInt("staffNumber"));
                coordinator.setRegistrationDate(result.getString("registrationDate"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return coordinator;
    }

    @Override
    public int updateCoordinator(int staffNumber, Coordinator coordinatorEdit) throws SQLException {
        int result = 0;
        try {
            connection = connexion.getConnection();
            String queryCoordinatorModify = "UPDATE Coordinator INNER JOIN User ON Coordinator.idUser =" +
                    " User.idUser SET User.name = ?, User.lastName = ?, User.gender = ?, User.email = ?" +
                    ", User.alternateEmail = ?, User.phone = ?, User.password = ?" +
                    ", Coordinator.staffNumber = ?  WHERE Coordinator.staffNumber = ?";
            PreparedStatement sentence = connection.prepareStatement(queryCoordinatorModify);
            sentence.setString(1, coordinatorEdit.getName());
            sentence.setString(2, coordinatorEdit.getLastName());
            sentence.setInt(3, coordinatorEdit.getGender());
            sentence.setString(4, coordinatorEdit.getEmail());
            sentence.setString(5, coordinatorEdit.getAlternateEmail());
            sentence.setString(6, coordinatorEdit.getPhone());
            sentence.setString(7, coordinatorEdit.getPassword());
            sentence.setInt(8, coordinatorEdit.getStaffNumber());
            sentence.setInt(9, staffNumber);
            sentence.executeUpdate();
            result = 1;
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int deleteCoordinator(Coordinator coordinator) throws SQLException {
        int result = 0;
        int idUserTypeStatus;
        coordinator.setStatus("Inactive");
        idUserTypeStatus = searchIdUserType(coordinator.getUserType(),coordinator.getStatus());
        if(idUserTypeStatus == 0){
            addUserType(coordinator.getUserType(),coordinator.getStatus());
            idUserTypeStatus = searchIdUserType(coordinator.getUserType(),coordinator.getStatus());
        }
        try {
            connection = connexion.getConnection();
            PreparedStatement sentenceDeleteCoordinator =
                    connection.prepareStatement("UPDATE Coordinator, User, User_UserTypeStatus SET User_UserTypeStatus.idUserTypeStatus=?" +
                            ", Coordinator.dischargeDate=? WHERE Coordinator.idUser = User.idUser AND User_UserTypeStatus.idUser = User.idUser");
            sentenceDeleteCoordinator.setInt(1, idUserTypeStatus);
            sentenceDeleteCoordinator.setString(2, coordinator.getDischargeDate());
            sentenceDeleteCoordinator.executeUpdate();
            result = 1;
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int addCoordinator(Coordinator coordinator) throws SQLException {
        int resultAdd = 0;
        int idUser;
        int userAdd = 0;
        int staffNumber;
        boolean emailExist;
        boolean phoneExist;
        boolean alternateEmailExist;
        Coordinator coordinatorSearch = new Coordinator();
        coordinatorSearch = coordinatorSearch.getCoordinator();
        if(coordinatorSearch==null){
            idUser = searchIdUser(coordinator);
            staffNumber = searchStaffNumber(coordinator.getStaffNumber());
            if ((idUser == 0) && (staffNumber == 0)) {
                emailExist = searchEmail(coordinator.getEmail());
                if(!emailExist){
                    phoneExist = searchPhone(coordinator.getPhone());
                    if(!phoneExist){
                        alternateEmailExist = searchAlternateEmail(coordinator.getAlternateEmail());
                        if(!alternateEmailExist){
                            userAdd = addUser(coordinator);
                            idUser = searchIdUser(coordinator);
                        }
                    }
                }
            }
            if ((userAdd == 1) || (idUser > 0)) {
                addUserUserTypeStatus(idUser,searchIdUserType(coordinator.getUserType(),coordinator.getStatus()));
                try {
                    connection = connexion.getConnection();
                    String queryAddCoordinator = "INSERT INTO Coordinator (staffNumber, registrationDate, idUser) VALUES (?, ?, ?)";
                    PreparedStatement sentenceAddCoordinator = connection.prepareStatement(queryAddCoordinator);
                    sentenceAddCoordinator.setInt(1, coordinator.getStaffNumber());
                    sentenceAddCoordinator.setString(2, coordinator.getRegistrationDate());
                    sentenceAddCoordinator.setInt(3, idUser);
                    sentenceAddCoordinator.executeUpdate();
                    resultAdd = 1;
                } catch (SQLException ex) {
                    Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return resultAdd;
    }

    private boolean searchAlternateEmail(String alternateEmail) throws SQLException {
        String alternateEmailFound= null;
        boolean search = false;
        try {
            connection = connexion.getConnection();
            String querySearch = "Select alternateEmail from User where alternateEmail =?";
            PreparedStatement sentence = connection.prepareStatement(querySearch);
            sentence.setString(1, alternateEmail);
            result = sentence.executeQuery();
            while (result.next()) {
                alternateEmailFound = result.getString("alternateEmail");
            }
            if (alternateEmailFound != null){
                search = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return search;
    }

    private boolean searchPhone(String phone) throws SQLException {
        String phoneFound= null;
        boolean search = false;
        try {
            connection = connexion.getConnection();
            String querySearch = "Select phone from User where phone =?";
            PreparedStatement sentence = connection.prepareStatement(querySearch);
            sentence.setString(1, phone);
            result = sentence.executeQuery();
            while (result.next()) {
                phoneFound = result.getString("phone");
            }
            if (phoneFound != null){
                search = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return search;
    }

    private boolean searchEmail(String email) throws SQLException {
        String emailFound= null;
        boolean search = false;
        try {
            connection = connexion.getConnection();
            String querySearch = "Select email from User where email =?";
            PreparedStatement sentence = connection.prepareStatement(querySearch);
            sentence.setString(1, email);
            result = sentence.executeQuery();
            while (result.next()) {
                emailFound = result.getString("email");
            }
            if (emailFound != null){
                search = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return search;
    }

    private void addUserUserTypeStatus(int idUser, int idUserType) throws SQLException {
        try {
            connection = connexion.getConnection();
            String queryAddUserTypeStatus = "INSERT INTO User_UserTypeStatus (idUser, idUserTypeStatus) VALUES (?,?)";
            PreparedStatement sentenceAddUserTypeStatus = connection.prepareStatement(queryAddUserTypeStatus);
            sentenceAddUserTypeStatus.setInt(1,idUser);
            sentenceAddUserTypeStatus.setInt(2,idUserType);
            sentenceAddUserTypeStatus.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int searchIdUserType(String userType, String status)  throws SQLException{
        int idUserType = 0;
        try {
            connection = connexion.getConnection();
            String queryUserType = "Select idUserTypeStatus from UserTypeStatus where type =? AND status=?";
            PreparedStatement sentence = connection.prepareStatement(queryUserType);
            sentence.setString(1, userType);
            sentence.setString(2,status);
            result = sentence.executeQuery();
            while (result.next()) {
                idUserType = result.getInt("idUserTypeStatus");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idUserType;
    }

    public int searchIdUser(Coordinator coordinator) throws SQLException {
        int idUser = 0;
        try {
            connection = connexion.getConnection();
            String queryUser = "Select idUser from User where name=? AND lastName=? AND email=? AND alternateEmail=? AND phone=?";
            PreparedStatement sentence = connection.prepareStatement(queryUser);
            sentence.setString(1, coordinator.getName());
            sentence.setString(2, coordinator.getLastName());
            sentence.setString(3, coordinator.getEmail());
            sentence.setString(4, coordinator.getAlternateEmail());
            sentence.setString(5, coordinator.getPhone());
            result = sentence.executeQuery();
            while (result.next()) {
                idUser = result.getInt("idUser");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connection.close();
        }
        return idUser;
    }

    private int addUser(Coordinator coordinator) throws SQLException {
        int result = 0;
        int idUserTypeSearch;
        idUserTypeSearch= searchIdUserType(coordinator.getUserType(), coordinator.getStatus());
        if(idUserTypeSearch == 0){
            addUserType(coordinator.getUserType(),coordinator.getStatus());
            idUserTypeSearch = searchIdUserType(coordinator.getUserType(), coordinator.getStatus());
        }
        if(idUserTypeSearch > 0){
            try {
                connection = connexion.getConnection();
                String queryAddUser = "INSERT INTO User  (name, lastName, gender, email,  alternateEmail" +
                        ", phone,password)  VALUES (?,?, ?, ?, ?, ?,?)";
                PreparedStatement sentenceAddUser = connection.prepareStatement(queryAddUser);
                sentenceAddUser.setString(1, coordinator.getName());
                sentenceAddUser.setString(2, coordinator.getLastName());
                sentenceAddUser.setInt(3, coordinator.getGender());
                sentenceAddUser.setString(4, coordinator.getEmail());
                sentenceAddUser.setString(5, coordinator.getAlternateEmail());
                sentenceAddUser.setString(6, coordinator.getPhone());
                sentenceAddUser.setString(7,coordinator.getPassword());
                sentenceAddUser.executeUpdate();
                result = 1;
            } catch (SQLException ex) {
                Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    public void addUserType(String userType, String status)  throws SQLException {
        try {
            connection = connexion.getConnection();
            String queryAddUserType = "INSERT INTO UserTypeStatus (type,status)  VALUES (?,?)";
            PreparedStatement sentenceAddUserType = connection.prepareStatement(queryAddUserType);
            sentenceAddUserType.setString(1, userType);
            sentenceAddUserType.setString(2, status);
            sentenceAddUserType.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int searchStaffNumber(int staffNumberSearch)  throws SQLException {
        int staffNumber = 0;
        try {
            connection = connexion.getConnection();
            String queryStaffNumber =
                    "Select staffNumber from Coordinator where staffNumber=?";
            PreparedStatement sentence = connection.prepareStatement(queryStaffNumber);
            sentence.setInt(1, staffNumberSearch);
            result = sentence.executeQuery();
            while (result.next()) {
                staffNumber = result.getInt("staffNumber");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return staffNumber;
    }

    @Override
    public List<Coordinator> getAllCoordinator() throws SQLException {
        List<Coordinator> coordinators = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            Statement consult = connection.createStatement();
            result = consult.executeQuery("Select * from Coordinator INNER JOIN User ON Coordinator.idUser =" +
                    " User.idUser");
            while (result.next()) {
                Coordinator coordinator = new Coordinator();
                coordinator.setName(result.getString("name"));
                coordinator.setLastName(result.getString("lastName"));
                coordinator.setGender(result.getInt("gender"));
                coordinator.setEmail(result.getString("email"));
                coordinator.setAlternateEmail(result.getString("alternateEmail"));
                coordinator.setPhone(result.getString("phone"));
                coordinator.setStaffNumber(result.getInt("staffNumber"));
                coordinator.setRegistrationDate(result.getString("registrationDate"));
                coordinator.setDischargeDate(result.getString("dischargeDate"));
                coordinator.setStatus(result.getString("status"));
                coordinators.add(coordinator);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return coordinators;
    }

    @Override
    public int recoverCoordinator(Coordinator coordinator) throws SQLException {
        int result = 0;
        int idUserTypeStatus;
        coordinator.setStatus("Active");
        idUserTypeStatus = searchIdUserType(coordinator.getUserType(),coordinator.getStatus());
        try {
            connection = connexion.getConnection();
            PreparedStatement sentenceRecoverCoordinator =
                    connection.prepareStatement("UPDATE Coordinator, User, User_UserTypeStatus SET User_UserTypeStatus.idUserTypeStatus=?" +
                            ", Coordinator.dischargeDate=? WHERE Coordinator.idUser = User.idUser AND User_UserTypeStatus.idUser = User.idUser");
            sentenceRecoverCoordinator.setInt(1, idUserTypeStatus);
            sentenceRecoverCoordinator.setString(2, null);
            sentenceRecoverCoordinator.executeUpdate();
            result = 1;
        } catch (SQLException ex) {
            Logger.getLogger(CoordinatorDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}