package dataaccess;

import java.io.File;

/**
 * Interface DAO User
 * @author Yazmin
 * @version 17/05/2020
 */

public interface IUserMethodDAO {
    int searchIdUser(String email, String alternateEmail, String phone) ;
    boolean addUser(String name, String lastName, String email, String alternateEmail, String phone, String password
            , String userType, String status, int gender, String userName) ;
    int searchStaffNumberCoordinator(int staffNumberSearch)  ;
    int searchStaffNumberTeacher(int staffNumberSearch) ;
    boolean validateUser(String email, String alternateEmail, String phone);
    int searchStaffNumberTwoAcademics(int staffNumberSearch) ;
    boolean validateUserUpdate(String email, String alternateEmail, String phone) ;
    void addRelations(int idUserAdd, String status, String userType);
    int searchIdUserType(String userType);
    boolean validateAcademicAdd(int staffNumber, String email, String alternateEmail, String phone, String userName );
    boolean staffNumberValidateTwoAcademics(int staffNumberSearch);
    boolean validateUserAdd(String email, String alternateEmail, String phone, String userName);
    boolean staffNumberTeacherValidate(int staffNumberSearch);
    boolean staffNumberCoordinatorValidate(int staffNumberSearch);
    boolean staffNumberTwoAcademicsValidate(int staffNumberSearch);
    boolean searchUserAcademic(String name, String lastName, String email, String alternateEmail, String phone, int gender);
}
