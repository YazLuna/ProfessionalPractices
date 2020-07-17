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
    int validateUser(String email, String alternateEmail, String phone);
    int validateAcademicUpdate(int staffNumber, String email, String alternateEmail, String phone) ;
    int searchIdUserType(String userType);
    int validateAcademicAdd(int staffNumber, String email, String alternateEmail, String phone, String userName );
    int staffNumberValidateTwoAcademics(int staffNumberSearch);
    int validateUserAdd(String email, String alternateEmail, String phone, String userName);
    int staffNumberTeacherValidate(int staffNumberSearch);
    int staffNumberCoordinatorValidate(int staffNumberSearch);
    int staffNumberTwoAcademicsValidate(int staffNumberSearch);
    boolean searchUserAcademic(String name, String lastName, String email, String alternateEmail, String phone, int gender);
}
