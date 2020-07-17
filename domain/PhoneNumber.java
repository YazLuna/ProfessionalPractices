package domain;

import java.util.ArrayList;
import java.util.List;
import dataaccess.PhoneNumberDAOImpl;

/**
 * Class Phone number
 * @author MARTHA
 * @version 08/05/2020
 */
public class PhoneNumber {
    private String phoneNumber;
    private String extensions;
    private int idPhoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getExtensions() {
        return extensions;
    }

    public void setExtensions(String extensions) {
        this.extensions = extensions;
    }

    public int getIdPhoneNumber() {
        return idPhoneNumber;
    }

    public void setIdPhoneNumber(int idPhoneNumber) {
        this.idPhoneNumber = idPhoneNumber;
    }

    /**
     * Method for add the phone number
     * @param phoneNumber define the phone number of the linked organization
     * @param idLinkedOrganization the id of the linked organization
     * @return if the phone number was added
     */
    public static boolean addPhoneNumber(PhoneNumber phoneNumber, int idLinkedOrganization){
        boolean isAddPhoneNumber = false;
        PhoneNumberDAOImpl phoneNumberDAO = new PhoneNumberDAOImpl();
        isAddPhoneNumber = phoneNumberDAO.addPhoneNumber(phoneNumber, idLinkedOrganization);
        return isAddPhoneNumber;
    }

    /**
     * Method for get all number phone of the linked organization
     * @param idLinkedOrganization define the id of the linked organization
     * @return get list oh the phone number of the linked organization
     */
    public static List<PhoneNumber> getListPhoneNumber(int idLinkedOrganization){
        List<PhoneNumber> phoneNumbersList = new ArrayList<>();
        PhoneNumberDAOImpl phoneNumberDAO = new PhoneNumberDAOImpl();
        phoneNumbersList= phoneNumberDAO.getAllPhoneNumber(idLinkedOrganization);
        return phoneNumbersList;
    }

    /**
     * Method for modify the phone number
     * @param phoneNumber define the data of the phone Number
     * @param datesUpdate the fields to modify
     * @return if the phone number was modify
     */
    public static boolean modifyPhoneNumber (PhoneNumber phoneNumber, List<String> datesUpdate) {
        boolean isModifyPhoneNumber;
        PhoneNumberDAOImpl phoneNumberDAO = new PhoneNumberDAOImpl();
        isModifyPhoneNumber = phoneNumberDAO.modifyPhoneNumber(phoneNumber,datesUpdate);
        return isModifyPhoneNumber;
    }

    /**
     * Method for validate repeat phone number
     * @param phoneNumber define the phone number
     * @return if valid phone number
     */
    public static int validateRepeatPhoneNumber (String phoneNumber){
        int isRepeatPhoneNumber;
        PhoneNumberDAOImpl phoneNumberDAO = new PhoneNumberDAOImpl();
        isRepeatPhoneNumber = phoneNumberDAO.validateRepeatPhoneNumber(phoneNumber);
        return isRepeatPhoneNumber;
    }

    /**
     * Method for validate repeat phone number
     * @param phoneNumber define the phone number
     * @return if valid phone number
     */
    public static int validateRepeatPhoneNumberExist (String phoneNumber, int idPhoneNumberOrigin){
        int isRepeatPhoneNumber;
        PhoneNumberDAOImpl phoneNumberDAO = new PhoneNumberDAOImpl();
        isRepeatPhoneNumber = phoneNumberDAO.validateRepeatPhoneNumberExist(phoneNumber,idPhoneNumberOrigin);
        return isRepeatPhoneNumber;
    }
}
