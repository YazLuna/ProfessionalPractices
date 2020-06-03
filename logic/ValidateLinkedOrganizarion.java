package logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class validate Data of Linked Organization
 * @author MARTHA
 * @version 08/05/2020
 */
public class ValidateLinkedOrganizarion extends ValidateGeneral{

    /**
     * Method to validate name of Linked Organization
     * @param name The name parameter defines the name of the Linked Organization
     * @return If the name is valid
     */
    public boolean validateName (String name) {
        boolean result;
        Pattern pattern = Pattern
                .compile("[A-Za-z]{3,50}");
        Matcher mather = pattern.matcher(name);
        result = mather.find();
        return result;
    }

    /**
     * Method to validate phone Number of Linked Organization
     * @param phoneNumber The phoneNumber parameter defines the phoneNumber of the Linked Organization
     * @return If the phoneNumber is valid
     */
    public boolean validatePhoneNumber (String phoneNumber) {
        boolean result;
        Pattern pattern = Pattern
                .compile("[0-9]{10}");
        Matcher mather = pattern.matcher(phoneNumber);
        result = mather.find();
        return result;
    }

    /**
     * Method to validate address of Linked Organization
     * @param address The address parameter defines the address of the Linked Organization
     * @return If the address is valid
     */
    public boolean validateAddress (String address) {
        boolean result;
        Pattern pattern = Pattern
                .compile("[A-Za-z_0-9_\\s]{15,50}");
        Matcher mather = pattern.matcher(address);
        result = mather.find();
        return result;
    }

    /**
     * Method to validate city,state and sector of Linked Organization
     * @param comboBox The comboBox parameter defines the city,state and sector of the Linked Organization
     * @return If the comboBox is valid
     */
    public boolean validateComboBox (String comboBox) {
        boolean result;
        Pattern pattern = Pattern
                .compile("[A-Za-z]{6,25}");
        Matcher mather = pattern.matcher(comboBox);
        result = mather.find();
        return result;
    }

    /**
     * Method to validate number Users of Linked Organization
     * @param numberUsers The numberUsers parameter defines the number Users of the Linked Organization
     * @return If the number Users is valid
     */
    public boolean validateNumberUsers (int numberUsers) {
        boolean result=true;
        if(numberUsers != 0 && numberUsers > 3){
            result = true;
        }else {
            result = false;
        }
        return result;
    }
}
