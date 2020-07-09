package dataaccess;

import java.util.List;
import domain.Practitioner;

/**
 * Interface Practitioner DAO
 * @author Yazmin
 * @version 09/07/2020
 */

public interface IPractitionerDAO {
    boolean addPractitioner (Practitioner practitioner);
    Practitioner getPractitioner (String enrollment) ;
    List <Practitioner> getPractitioners();
    List <Practitioner> getPractitionersActive () ;
    List <Practitioner> getPractitionersInformation();
    boolean updatePractitioner (String enrollment, Practitioner practitioner);
    boolean recoverPractitioner(Practitioner practitioner);
    boolean deletePractitioner (String enrollment, String status);
    boolean activePractitioner();
    boolean arePractitioners();
    boolean validPractitionerAdd(Practitioner practitioner);
    boolean validPractitionerUpdate(Practitioner practitioner);
}
