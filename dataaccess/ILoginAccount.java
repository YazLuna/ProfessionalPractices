package dataaccess;

import java.util.List;

/**
 * ILoginAccount
 * @author Yazmin
 * @version 04/06/2020
 */

public interface ILoginAccount {
    boolean firstLogin(String userName, String password);
    List<String> searchUserTypeWithLoginAccount(String user, String password);
    boolean searchLoginAccount(String userName, String password);
    boolean updateLoginAccount(String userNamePrevious, String passwordPrevious, String passwordNew, String userNameNew);
    boolean searchUserName(String userName);
}