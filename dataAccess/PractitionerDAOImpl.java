package dataAccess;

import domain.Practitioner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

public class PractitionerDAOImpl implements IPractitionerDAO {
    private final Connexion connexion;
    private Connection connection;
    private Statement consult;
    private ResultSet result;

    public PractitionerDAOImpl() {
        connexion = new Connexion ();
    }
    
    @Override
    public Practitioner getPractitioner (String enrollment) throws SQLException {
        Practitioner practitioner = new Practitioner ();
        try {
            connection = connexion.getConnection () ;
            String queryFoundPractitioner ="Select * from Practitioner, User, Lapse WHERE Practitioner.idUser = User.idUser AND Practitioner.idLapse = Lapse.idLapse AND Practitioner.enrollment = ?";
            PreparedStatement sentence = connection.prepareStatement (queryFoundPractitioner);
            sentence.setString (1,enrollment);
            result = sentence.executeQuery();
            while (result.next()){
                practitioner.setName(result.getString("name"));
                practitioner.setLastName(result.getString("lastName"));
                practitioner.setGender(result.getInt("gender"));
                practitioner.setEmail(result.getString("email"));
                practitioner.setAlternateEmail(result.getString("alternateEmail"));
                practitioner.setPhone(result.getString("phone"));
                practitioner.setEnrollment(result.getString("enrollment"));
                practitioner.setPeriod(result.getString("lapse"));
            }
        }catch(SQLException ex){
            Logger.getLogger(PractitionerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return practitioner;
    }

    @Override
    public int updatePractitioner (String enrollment, Practitioner practitionerEdit) throws SQLException {
        int result = 0;
        try {
            connection = connexion.getConnection();
            PreparedStatement sentenceUpdatePractitioner = connection.prepareStatement ("UPDATE Practitioner INNER JOIN User ON Practitioner.idUser = User.idUser SET User.name = ?, User.lastName = ?, User.gender = ?, User.email = ?,"
                    + " User.alternateEmail = ?, User.phone = ?, Practitioner.enrollment = ?   WHERE  Practitioner.enrollment = ?");
            sentenceUpdatePractitioner.setString(1, practitionerEdit.getName());
            sentenceUpdatePractitioner.setString(2, practitionerEdit.getLastName());
            sentenceUpdatePractitioner.setInt(3, practitionerEdit.getGender());
            sentenceUpdatePractitioner.setString(4, practitionerEdit.getEmail());
            sentenceUpdatePractitioner.setString(5, practitionerEdit.getAlternateEmail());
            sentenceUpdatePractitioner.setString(6, practitionerEdit.getPhone());
            sentenceUpdatePractitioner.setString(7, practitionerEdit.getEnrollment());
            sentenceUpdatePractitioner.setString(8, enrollment);
            //lapse
            sentenceUpdatePractitioner.executeUpdate();
            result = 1;
        }catch(SQLException ex){
            Logger.getLogger(PractitionerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int recoverPractitioner (Practitioner practitionerEdit) throws SQLException {
        int result = 0;
        try {
            connection = connexion.getConnection();
            PreparedStatement sentenceRecoverPractitioner = connection.prepareStatement ("UPDATE Practitioner INNER JOIN User ON Practitioner.idUser = User.idUser SET User.status = 'Active' WHERE Practitioner.enrollment = ?");
            sentenceRecoverPractitioner.setString(1, practitionerEdit.getEnrollment());
            sentenceRecoverPractitioner.executeUpdate();
            result = 1;
        }catch(SQLException ex){
            Logger.getLogger(PractitionerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int deletePractitioner (Practitioner practitioner) throws SQLException {
        int result = 0;
        try{
            connection = connexion.getConnection();
            PreparedStatement sentenceDeletePractitioner=connection.prepareStatement("UPDATE Practitioner INNER JOIN User ON Practitioner.idUser = User.idUser SET status = 'Inactive' WHERE Practitioner.enrollment=?");
            sentenceDeletePractitioner.setString(1,practitioner.getEnrollment());
            sentenceDeletePractitioner.executeUpdate();
            result = 1;
        }catch(SQLException ex){
            Logger.getLogger(PractitionerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public int addPractitioner (Practitioner practitioner) throws SQLException {
        int resultAdd = 0;
        int enrollment = searchEnrollment(practitioner.getEnrollment());
        if (enrollment == 0){
            int idLapse = searchIdLapse(practitioner.getPeriod());
            if(idLapse == 0){
                addLapse(practitioner.getPeriod());
                idLapse = searchIdLapse(practitioner.getPeriod());
            }
            int UserAdd= addUser(practitioner);
            if (UserAdd == 1) {
                int idUser = searchIdUser(practitioner);
                try{
                    connection = connexion.getConnection();
                    String queryAddPractitioner = "INSERT INTO Practitioner  (enrollment, idUser, idLapse ) VALUES ( ?, ?,?)";
                    PreparedStatement sentenceAdd = connection.prepareStatement(queryAddPractitioner);
                    sentenceAdd.setString(1, practitioner.getEnrollment());
                    sentenceAdd.setInt(2, idUser);
                    sentenceAdd.setInt(3, idLapse);
                    sentenceAdd.executeUpdate();
                    resultAdd = 1;
                }catch(SQLException ex){
                    Logger.getLogger(PractitionerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return resultAdd;
    }

    private int searchIdLapse(String period) {
        int idLapse = 0;
        try{
            connection = connexion.getConnection();
            String queryLapse= "Select idLapse from Lapse where lapse=?";
            PreparedStatement sentence =connection.prepareStatement(queryLapse);
            sentence.setString(1, period);
            result= sentence.executeQuery();
            while(result.next()){
                idLapse =result.getInt("idLapse");
            }
        }catch(SQLException ex){
            Logger.getLogger(PractitionerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idLapse;
    }

    private int addLapse(String period) {
        int resultAdd =  0;
        try{
            connection = connexion.getConnection();
            String queryAddPeriod = "INSERT INTO Lapse (lapse)  VALUES (?)";
            PreparedStatement sentenceAddUser = connection.prepareStatement(queryAddPeriod);
            sentenceAddUser.setString(1, period);
            sentenceAddUser.executeUpdate();
            resultAdd = 1;
        } catch (SQLException ex) {
            Logger.getLogger(PractitionerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultAdd;
    }

    public int searchEnrollment(String enrollmentSearch) {
        String enrollment= null;
        int resultAdd =0;
        try{
            connection = connexion.getConnection();
            String queryEnrollment= "Select enrollment from Practitioner where enrollment=?";
            PreparedStatement sentence =connection.prepareStatement(queryEnrollment);
            sentence.setString(1,enrollmentSearch);
            result= sentence.executeQuery();
            while(result.next()){
                enrollment =result.getString("enrollment");
            }
            if(enrollment != null){
                resultAdd = 1;
            }
        }catch(SQLException ex){
            Logger.getLogger(PractitionerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultAdd;
    }

    public int addUser(Practitioner practitioner){
        int result = 0;
        try{
            connection = connexion.getConnection();
            String queryAddPractitionerUser = "INSERT INTO User  (name, lastName, gender, status, email,  alternateEmail, phone)  VALUES (?,?, ?, ?, ?, ?, ?)";
            PreparedStatement sentenceAddUser = connection.prepareStatement(queryAddPractitionerUser);
            sentenceAddUser.setString(1, practitioner.getName());
            sentenceAddUser.setString(2, practitioner.getLastName());
            sentenceAddUser.setInt(3, practitioner.getGender());
            sentenceAddUser.setString(4,practitioner.getStatus());
            sentenceAddUser.setString(5, practitioner.getEmail());
            sentenceAddUser.setString(6, practitioner.getAlternateEmail());
            sentenceAddUser.setString(7, practitioner.getPhone());
            sentenceAddUser.executeUpdate();
            result = 1;
        } catch (SQLException ex) {
            Logger.getLogger(PractitionerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public int searchIdUser (Practitioner practitioner){
        int idUser = 0;
        try{
            connection = connexion.getConnection();
            String queryUser= "Select idUser from User where name=? AND lastName=? AND email=? AND alternateEmail=? AND phone=?";
            PreparedStatement sentence =connection.prepareStatement(queryUser);
            sentence.setString(1, practitioner.getName());
            sentence.setString(2, practitioner.getLastName());
            sentence.setString(3, practitioner.getEmail());
            sentence.setString(4, practitioner.getAlternateEmail());
            sentence.setString(5, practitioner.getPhone());
            result= sentence.executeQuery();
            while(result.next()){
                idUser =result.getInt("idUser");
            }
        }catch(SQLException ex){
            Logger.getLogger(PractitionerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idUser;
    }

    @Override 
    public List <Practitioner> getAllPractitioner () throws SQLException{
       List<Practitioner> practitioners = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            consult = connection.createStatement();
            result = consult.executeQuery("Select * from Practitioner INNER JOIN User ON Practitioner.idUser = User.idUser");
            while(result.next()){
                Practitioner practitioner = new Practitioner();
                practitioner.setName(result.getString("name"));
                practitioner.setLastName(result.getString("lastName"));
                practitioner.setGender(result.getInt("gender"));
                practitioner.setEmail(result.getString("email"));
                practitioner.setAlternateEmail (result.getString("alternateEmail"));
                practitioner.setPhone(result.getString("phone"));
                practitioner.setEnrollment(result.getString("enrollment"));
                practitioner.setPeriod((result.getString("lapse")));
                practitioners.add(practitioner);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PractitionerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return practitioners;
    }

    @Override
    public List <Practitioner> getPractitionersActive () throws SQLException{
        List<Practitioner> practitioners = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            consult = connection.createStatement();
            result = consult.executeQuery("Select * from Practitioner INNER JOIN User ON Practitioner.idUser = User.idUser WHERE User.status = 'Active'");
            while(result.next()){
                Practitioner practitioner = new Practitioner();
                practitioner.setName(result.getString("name"));
                practitioner.setLastName(result.getString("lastName"));
                practitioner.setGender(result.getInt("gender"));
                practitioner.setEmail(result.getString("email"));
                practitioner.setAlternateEmail (result.getString("alternateEmail"));
                practitioner.setPhone(result.getString("phone"));
                practitioner.setEnrollment(result.getString("enrollment"));
                practitioner.setPeriod((result.getString("lapse")));
                practitioners.add(practitioner);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PractitionerDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return practitioners;
    }
}