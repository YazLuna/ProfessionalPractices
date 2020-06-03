package dataaccess;

import java.io.File;
import java.sql.SQLException;

/**
 * DAO User
 * @author Yazmin
 * @version 17/05/2020
 */

public interface IUserMethodDAO {
    int searchIdUser(String email, String alternateEmail, String phone) throws SQLException ;
    boolean addUser(String name, String lastName, String email, String alternateEmail, String phone, String password
            , String userType, String status, int gender, String userName, File image) throws SQLException ;
    int searchStaffNumberCoordinator(int staffNumberSearch)  throws SQLException ;
    int searchStaffNumberTeacher(int staffNumberSearch)  throws SQLException ;
    boolean searchLoginAccount(String userName, String password) throws SQLException;
    String searchUserType(int idUser) throws SQLException;
    void addUserStatus(String status)  throws SQLException ;
    int searchIdUserStatus(String status)  throws SQLException;
    boolean validateUser(String email, String alternateEmail, String phone, String userName) throws SQLException;
    int searchStaffNumber(int staffNumberSearch)  throws SQLException;
}
