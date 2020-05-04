package dataaccess;

import java.sql.SQLException;
import java.util.List;

import domain.Practitioner;

public interface IPractitionerDAO {
    public Practitioner getPractitioner (String enrollment) throws SQLException;
    public int updatePractitioner (String enrollment, Practitioner practitioner) throws SQLException;
    public int deletePractitioner (Practitioner practitioner) throws SQLException;
    public int addPractitioner (Practitioner practitioner) throws SQLException;
    public List <Practitioner> getAllPractitioner () throws SQLException;
    public List <Practitioner> getPractitionersActive () throws SQLException;
    public int recoverPractitioner(Practitioner practitioner) throws  SQLException;
}
