package dataaccess;

import java.util.List;

/**
 * Interface LoginAccount DAO
 * @author Yazmin
 * @version 04/07/2020
 */

public interface ILoginAccountDAO {
    boolean createLoginAccount(String userName, String password, int idUser);
    boolean firstLogin(String userName, String password);
    List<String> searchUserTypeWithLoginAccount(String user, String password);
    boolean searchLoginAccount(String userName, String password);
    boolean updateLoginAccount(String userNamePrevious, String passwordPrevious, String passwordNew, String userNameNew);
    boolean updateLoginAccountPractitioner(String userNamePrevious, String passwordPrevious, String passwordNew);
    boolean validateUserName(String userName);
}