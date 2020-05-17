package dataaccess;

import java.sql.SQLException;
import java.util.List;

import domain.Coordinator;

/**
 * DAO User
 * @author Yazmin
 * @version 08/05/2020
 */

public interface ICoordinatorDAO {
   Coordinator getCoordinator () throws SQLException;
   int updateCoordinator (int staffNumber, Coordinator coordinator) throws SQLException;
   int deleteCoordinator (Coordinator coordinator) throws SQLException;
   int addCoordinator (Coordinator coordinator) throws SQLException;
   List <Coordinator> getAllCoordinator () throws SQLException;
   int recoverCoordinator(Coordinator coordinator) throws  SQLException;
}