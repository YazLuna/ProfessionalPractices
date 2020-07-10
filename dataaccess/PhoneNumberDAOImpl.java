package dataaccess;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import domain.PhoneNumber;

/**
 * Implementation of the IPhoneNumberDAO
 * @author MARTHA
 * @version 08/05/2020
 */
public class PhoneNumberDAOImpl implements IPhoneNumberDAO  {
    private final Connexion connexion;
    private Connection connection;
    private Statement consultation;
    private ResultSet results;

    /**
     * Constructor for the PhoneNumberDAOImpl class
     */
    public PhoneNumberDAOImpl (){
        connexion = new Connexion();
    }

    /**
     * Method to add a phone number
     * @param phoneNumber The phoneNumber parameter defines the Phone Number
     */
    public boolean addPhoneNumber(PhoneNumber phoneNumber,int idLinkedOrganization){
        boolean isAddPhoneNumber =false;
        try{
            connection = connexion.getConnection();
            PreparedStatement sentenceState = connection.prepareStatement("INSERT INTO PhoneNumber (phoneNumber,extensions, idLinkedOrganization) VALUES (?,?,?)");
            sentenceState.setString(1,phoneNumber.getPhoneNumber());
            sentenceState.setString(2,phoneNumber.getExtensions());
            sentenceState.setInt(3,idLinkedOrganization);
            sentenceState.executeUpdate();
            isAddPhoneNumber =true;
        }catch(SQLException ex){
            Logger.getLogger(PhoneNumberDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return isAddPhoneNumber;
    }

    /**
     * Method to get the phone number list
     * @return The phone number list
     */
    public List<PhoneNumber> getAllPhoneNumber(int idLinkedOrganization){
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        try {
            connection = connexion.getConnection();
            String queryAllPhoneNumber = "SELECT * FROM PhoneNumber WHERE idLinkedOrganization =?";
            PreparedStatement sentenceAllOrganization = connection.prepareStatement(queryAllPhoneNumber);
            sentenceAllOrganization.setInt(1, idLinkedOrganization);
            results= sentenceAllOrganization.executeQuery();
            while(results.next()){
                PhoneNumber phoneNumber= new PhoneNumber();
                phoneNumber.setPhoneNumber(results.getString("phoneNumber"));
                phoneNumber.setExtensions(results.getString("extensions"));
                phoneNumber.setIdPhoneNumber(results.getInt("idPhoneNumber"));
                phoneNumbers.add(phoneNumber);
            }
        }catch (SQLException ex){
            Logger.getLogger(PhoneNumberDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return phoneNumbers;
    }


    /**
     * Method to modify the phone
     * @param phoneNumberEdit define the phone number of the linked organization
     * @param datesUpdate data that is modified
     * @return if this phone is modify
     */
    public boolean modifyPhoneNumber (PhoneNumber phoneNumberEdit, List<String> datesUpdate){
        boolean result = false;
        String sentenceDatesUpdate="";
        List<String> Change = new ArrayList<>();
        for (int indexDatesUpdate = Number.ZERO.getNumber(); indexDatesUpdate < datesUpdate.size(); indexDatesUpdate++) {
            if (indexDatesUpdate == datesUpdate.size() - 1) {
                sentenceDatesUpdate = sentenceDatesUpdate + datesUpdate.get(indexDatesUpdate) + "= ?";
            } else {
                sentenceDatesUpdate = sentenceDatesUpdate + datesUpdate.get(indexDatesUpdate) + "= ?,";
            }
            Change.add("get" + datesUpdate.get(indexDatesUpdate));
        }
        String sentence = "UPDATE PhoneNumber SET "+sentenceDatesUpdate+ " WHERE idPhoneNumber " +
                "= "+ phoneNumberEdit.getIdPhoneNumber();
        try{
            connection = connexion.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sentence);
            Class classPhone = phoneNumberEdit.getClass();
            for(int indexPreparedStatement = Number.ONE.getNumber() ; indexPreparedStatement
                    <= datesUpdate.size(); indexPreparedStatement++){
                Method methodPhone;
                methodPhone = classPhone.getMethod(Change.get(indexPreparedStatement - 1));
                String word = (String) methodPhone.invoke(phoneNumberEdit, new Object[] {});
                preparedStatement.setString(indexPreparedStatement,word);
            }
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException | ReflectiveOperationException ex) {
            Logger.getLogger(PhoneNumberDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            connexion.closeConnection();
        }
        return result;
    }

    /**
     * Method to find out if the phone is registered
     * @param phoneNumber define the phone number
     * @return if this phone is registered
     */
    public boolean validateRepeatPhoneNumber (String phoneNumber) {
        boolean isRepeatPhoneNumber = false;
        try {
            connection = connexion.getConnection();
            String queryAllPhoneNumber = "SELECT idPhoneNumber FROM PhoneNumber WHERE phoneNumber =?";
            PreparedStatement sentenceAllOrganization = connection.prepareStatement(queryAllPhoneNumber);
            sentenceAllOrganization.setString(1, phoneNumber);
            results= sentenceAllOrganization.executeQuery();
            if(results.next()){
                isRepeatPhoneNumber = true;
            }
        }catch (SQLException ex){
            Logger.getLogger(PhoneNumberDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            connexion.closeConnection();
        }
        return isRepeatPhoneNumber;
    }
}
