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
    boolean updatePractitioner(String enrollmentOrigin, Practitioner practitionerEdit, List<String>datesUpdate);
    boolean recoverPractitioner(String enrollment);
    boolean deletePractitioner (String enrollment, String status);
    int activePractitioner();
    int arePractitioners();
    int validPractitionerUpdate(Practitioner practitioner);
    int validPractitionerAdd(Practitioner practitioner);
}
