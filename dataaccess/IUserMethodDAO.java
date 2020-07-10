package dataaccess;

import domain.User;

/**
 * Interface DAO User
 * @author Yazmin
 * @version 09/07/2020
 */

public interface IUserMethodDAO {
    int searchIdUser(String email, String alternateEmail, String phone) ;
    boolean addUser(User user, String userType) ;
    int searchStaffNumberCoordinator(int staffNumberSearch)  ;
    int searchStaffNumberTeacher(int staffNumberSearch) ;
    boolean validateUser(String email, String alternateEmail, String phone);
    boolean validateAcademicUpdate(int staffNumber, String email, String alternateEmail, String phone) ;
    int searchIdUserType(String userType);
    boolean validateAcademicAdd(int staffNumber, String email, String alternateEmail, String phone, String userName );
    boolean staffNumberValidateTwoAcademics(int staffNumberSearch);
    boolean validateUserAdd(String email, String alternateEmail, String phone, String userName);
    boolean staffNumberTeacherValidate(int staffNumberSearch);
    boolean staffNumberCoordinatorValidate(int staffNumberSearch);
    boolean staffNumberTwoAcademicsValidate(int staffNumberSearch);
    boolean searchUserAcademic(String name, String lastName, String email, String alternateEmail, String phone, int gender);
}
