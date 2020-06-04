package logic;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Class validate Data of Person
 * @author MARTHA
 * @version 08/05/2020
 */
public class ValidateDataPerson extends ValidateGeneral {
    /**
     * Method to validate last name
     * @param lastName  The last name parameter defines the last name of the person
     * @return If the last name is valid
     */
    public boolean validateLastName (String lastName) {
        boolean isValidLastName;
        Pattern pattern = Pattern
                .compile("[A-Za-z]{3,50}");
        Matcher mather = pattern.matcher(lastName);
        isValidLastName = mather.find();
        return isValidLastName;
    }

    /**
     * Method to validate charge
     * @param charge The charge parameter defines the charge of the responsible project
     * @return If the charge is valid
     */
    public boolean validateCharge (String charge) {
        boolean isValidCharge;
        Pattern pattern = Pattern
                .compile("[A-Za-z]{7,70}");
        Matcher mather = pattern.matcher(charge);
        isValidCharge = mather.find();
        return isValidCharge;
    }
}
