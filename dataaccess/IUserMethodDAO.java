package dataaccess;

import java.sql.SQLException;
import domain.Coordinator;

public interface IUserMethodDAO {
    public int searchAlternateEmail(String alternateEmail) throws SQLException ;
    public int searchPhone(String phone) throws SQLException;
    public int searchEmail(String email) throws SQLException;
    public void addUserUserTypeStatus(int idUser, int idUserType) throws SQLException ;
    public int searchIdUserType(String userType, String status)  throws SQLException;
    public int searchIdUser(Coordinator coordinator) throws SQLException ;
    public int addUser(Coordinator coordinator) throws SQLException ;
    public void addUserType(String userType, String status)  throws SQLException ;
    public int searchStaffNumber(int staffNumberSearch)  throws SQLException ;
}
