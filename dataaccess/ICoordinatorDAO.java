package dataaccess;

import java.sql.SQLException;
import java.util.List;

import domain.Coordinator;

public interface ICoordinatorDAO {
   public Coordinator getCoordinator () throws SQLException;
   public int updateCoordinator (int staffNumber, Coordinator coordinator) throws SQLException;
   public int deleteCoordinator (Coordinator coordinator) throws SQLException;
   public int addCoordinator (Coordinator coordinator) throws SQLException;
   public List <Coordinator> getAllCoordinator () throws SQLException;
   public int recoverCoordinator(Coordinator coordinator) throws  SQLException;
}