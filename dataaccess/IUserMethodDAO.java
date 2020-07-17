package dataaccess;

import domain.User;

/**
 * Interface DAO User
 * @author Yazmin
 * @version 17/07/2020
 */

public interface IUserMethodDAO {
    boolean addUser(User user) ;
    int validateAcademicAdd(int staffNumber, String email, String alternateEmail, String phone, String userName );
    int validateUser(String email, String alternateEmail, String phone);
    int validateUserAdd(String email, String alternateEmail, String phone, String userName);
    int validateAcademicUpdate(int staffNumber, String email, String alternateEmail, String phone) ;
}
