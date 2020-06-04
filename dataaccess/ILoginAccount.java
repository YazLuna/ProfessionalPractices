package dataaccess;

import java.sql.SQLException;

public interface ILoginAccount {
    public boolean firstLogin(String userName, String password) throws SQLException;

    public String searchUserTypeWithLoginAccount(String user, String password) throws SQLException;

    public boolean searchLoginAccount(String userName, String password) throws SQLException;

    public boolean updateLoginAccount(String userNamePrevious, String passwordPrevious, String passwordNew, String userNameNew) throws SQLException;
}