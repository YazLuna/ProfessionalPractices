package logic;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Class validate Data of Person
 * @author MARTHA
 * @version 08/05/2020
 */
public class ValidateDataPerson {

    /**
     * Method to validate name
     * @param name The name parameter defines the name of the person
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
     * Method to validate last name
     * @param lastName  The last name parameter defines the last name of the person
     * @return If the last name is valid
     */
    public boolean validateLastName (String lastName) {
        boolean result;
        Pattern pattern = Pattern
                .compile("[A-Za-z]{3,50}");
        Matcher mather = pattern.matcher(lastName);
        result = mather.find();
        return result;
    }

    /**
     * Method to validate email
     * @param email The email parameter defines the email
     * @return If the email is valid
     */
    public boolean validateEmail (String email) {
        boolean result;
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(email);
        result = mather.find();
        return result;
    }

    /**
     * Method to validate charge
     * @param charge The charge parameter defines the charge of the responsible project
     * @return If the charge is valid
     */
    public boolean validateCharge (String charge) {
        boolean result;
        Pattern pattern = Pattern
                .compile("[A-Za-z]{6,25}");
        Matcher mather = pattern.matcher(charge);
        result = mather.find();
        return result;
    }
}
