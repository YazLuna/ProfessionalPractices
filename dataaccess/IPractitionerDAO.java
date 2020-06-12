package dataaccess;

import java.util.List;
import domain.Practitioner;

/**
 * IPractitionerDAAO
 * @author Yazmin
 * @version 11/06/2020
 */

public interface IPractitionerDAO {
    Practitioner getPractitioner (String enrollment) ;
    boolean updatePractitioner (String enrollment, Practitioner practitioner);
    boolean deletePractitioner (String enrollment, String status);
    boolean addPractitioner (Practitioner practitioner);
    List <Practitioner> getAllPractitioner () ;
    List <Practitioner> getPractitionersActive () ;
    boolean recoverPractitioner(Practitioner practitioner);
    List <Practitioner> getInformationPractitioner ();
    boolean activePractitioner();
    boolean arePractitioner();
}
