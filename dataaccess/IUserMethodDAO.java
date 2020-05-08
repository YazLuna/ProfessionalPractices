package dataaccess;

import java.sql.SQLException;

/**
 * DAO User
 * @author Yazmin
 * @version 08/05/2020
 */

public interface IUserMethodDAO {
    public int searchAlternateEmail(String alternateEmail) throws SQLException ;
    public int searchPhone(String phone) throws SQLException;
    public int searchEmail(String email) throws SQLException;
    public void addUserUserTypeStatus(int idUser, int idUserType) throws SQLException ;
    public int searchIdUserType(String userType, String status)  throws SQLException;
    public int searchIdUser(String name, String lastName, String email, String alternateEmail, String phone) throws SQLException ;
    public int addUser(String name, String lastName, String email, String alternateEmail, String phone, String password
            , String userType, String status, int gender) throws SQLException ;
    public void addUserType(String userType, String status)  throws SQLException ;
    public int searchStaffNumber(int staffNumberSearch)  throws SQLException ;
    public int searchStaffNumberTeacher(int staffNumberSearch) throws SQLException;
}
