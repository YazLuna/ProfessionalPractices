package dataaccess;

import java.util.List;
import domain.Coordinator;

/**
 * Interface DAO Coordinator
 * @author Yazmin
 * @version 26/06/2020
 */

public interface ICoordinatorDAO {
    Coordinator getCoordinator () ;
    Coordinator getCoordinatorSelected (int staffNumber) ;
    boolean updateCoordinator(int staffNumberOrigin, Coordinator coordinatorEdit, String tableOne, String tableTwo
            , String unify, List<String>DatesUpdate, String condition);
    boolean deleteCoordinator (String status, String dischargeDate) ;
    boolean addCoordinator (Coordinator coordinator) ;
    List <Coordinator> getAllCoordinator () ;
    List <Coordinator> getInformationAllCoordinator () ;
    boolean recoverCoordinator(Coordinator coordinator) ;
    boolean activeCoordinator()  ;
    boolean areCoordinator();
}