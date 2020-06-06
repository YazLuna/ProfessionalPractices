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
   Coordinator getCoordinator () ;
   Coordinator getCoordinatorSelected (int staffNumber) ;
   boolean updateCoordinator (int staffNumber, Coordinator coordinator);
   boolean deleteCoordinator (String status, String dischargeDate) ;
   boolean addCoordinator (Coordinator coordinator) ;
   List <Coordinator> getAllCoordinator () ;
   List <Coordinator> getInformationAllCoordinator () ;
   boolean recoverCoordinator(Coordinator coordinator) ;
   boolean activeCoordinator()  ;
   boolean areCoordinator();
}