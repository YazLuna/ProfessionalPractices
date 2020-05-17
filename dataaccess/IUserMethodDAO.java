package dataaccess;

import java.sql.SQLException;

/**
 * DAO User
 * @author Yazmin
 * @version 08/05/2020
 */

public interface IUserMethodDAO {
    int searchIdUser(String email, String alternateEmail, String phone) throws SQLException ;
    boolean addUser(String name, String lastName, String email, String alternateEmail, String phone, String password
            , String userType, String status, int gender,String userName) throws SQLException ;
    int searchStaffNumber(int staffNumberSearch)  throws SQLException ;
    boolean searchLoginAccount(String userName, String password) throws SQLException;
}
