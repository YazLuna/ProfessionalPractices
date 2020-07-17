package dataaccess;

import java.util.List;
import domain.Coordinator;

/**
 * Interface DAO Coordinator
 * @author Yazmin
 * @version 07/07/2020
 */

public interface ICoordinatorDAO {
    boolean addCoordinator (Coordinator coordinator) ;
    Coordinator getCoordinator () ;
    Coordinator getCoordinatorSelected (int staffNumber) ;
    List <Coordinator> getCoordinators () ;
    List <Coordinator> getCoordinatorsInformation ();
    boolean updateCoordinator(int staffNumberOrigin, Coordinator coordinatorEdit, List<String>DatesUpdate);
    boolean recoverCoordinator(int staffNumber) ;
    boolean deleteCoordinator (String status, String dischargeDate) ;
    int activeCoordinator()  ;
    int areCoordinator();
    boolean isTeacher(Coordinator coordinator);
}