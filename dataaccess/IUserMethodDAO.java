package dataaccess;

import java.io.File;

/**
 * DAO User
 * @author Yazmin
 * @version 17/05/2020
 */

public interface IUserMethodDAO {
    int searchIdUser(String email, String alternateEmail, String phone)  ;
    boolean addUser(String name, String lastName, String email, String alternateEmail, String phone, String password
            , String userType, String status, int gender, String userName, File image) ;
    int searchStaffNumberCoordinator(int staffNumberSearch)  ;
    int searchStaffNumberTeacher(int staffNumberSearch) ;
    int searchIdUserStatus(String status);
    boolean validateUser(String email, String alternateEmail, String phone, String userName);
    int searchStaffNumber(int staffNumberSearch) ;
}
