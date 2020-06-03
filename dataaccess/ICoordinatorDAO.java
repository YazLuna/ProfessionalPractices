package dataaccess;

import java.sql.SQLException;
import java.util.List;

import domain.Coordinator;

/**
 * DAO User
 * @author Yazmin
 * @version 03/06/2020
 */

public interface ICoordinatorDAO {
   Coordinator getCoordinator () throws SQLException;
   boolean updateCoordinator (int staffNumber, Coordinator coordinator) throws SQLException;
   boolean deleteCoordinator (String status, String dischargeDate) throws SQLException;
   boolean addCoordinator (Coordinator coordinator) throws SQLException;
   List <Coordinator> getAllCoordinator () throws SQLException;
   boolean recoverCoordinator(Coordinator coordinator) throws  SQLException;
   boolean activeCoordinator() throws SQLException ;
}