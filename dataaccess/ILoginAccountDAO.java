package dataaccess;

import java.util.List;

/**
 * Interface LoginAccount DAO
 * @author Yazmin
 * @version 04/06/2020
 */

public interface ILoginAccountDAO {
    boolean firstLogin(String userName, String password);
    List<String> searchUserTypeWithLoginAccount(String user, String password);
    boolean searchLoginAccount(String userName, String password);
    boolean updateLoginAccount(String userNamePrevious, String passwordPrevious, String passwordNew, String userNameNew);
    boolean searchUserName(String userName);
    boolean updateLoginAccountPractitioner(String userNamePrevious, String passwordPrevious, String passwordNew);
}