package logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ValidateAddUser
 * @author Yazmin
 * @version 16/06/2020
 */

public class ValidateUser extends ValidateGeneral {

    /**
     *Method to validate name
     * @param name from User
     * @return whether it is valid or not
     */
    public boolean validateNameUser (String name) {
        Pattern pattern = Pattern
                .compile("[A-Za-zñÑáéíóúÁÉÍÓÚ]{2,30}");
        Matcher mather = pattern.matcher(name);
        boolean result = mather.find();
        return result;
    }

    /**
     * Method to create a correct proper name
     * @param name from User
     * @return Correct proper name
     */
    public String createCorrectProperName (String name) {
        String[] splitName = name.split("");
        for (int index = 0; index < splitName.length; index++) {
            if(index == 0 || splitName[index-1].equals(" ")) {
                splitName[index] = splitName[index].toUpperCase();
            } else {
                splitName[index] = splitName[index].toLowerCase();
            }
        }
        String correctName = String.join("", splitName);
        return correctName;
    }

    /**
     *Method to validate username
     * @param userName from User
     * @return whether it is valid or not
     */
    public boolean validateUserName (String userName) {
        Pattern pattern = Pattern
                .compile("[A-Za-zñÑáéíóúÁÉÍÓÚ0-9]{10,50}");
        Matcher mather = pattern.matcher(userName);
        boolean result = mather.find();
        return result;
    }

    /**
     * Method to validate a password
     * @param password from User
     * @return whether it is valid or not
     */
    public boolean validatePassword (String password) {
        Pattern pattern = Pattern
                .compile("[A-Za-zñÑáéíóúÁÉÍÓÚ0-9]{10,20}");
        Matcher mather = pattern.matcher(password);
        boolean result = mather.find();
        return result;
    }

    /**
     * Method to validate that a field is not empty
     * @param word to validate
     * @return whether it is valid or not
     */
    public boolean validateEmpty (String word) {
        boolean result=false;
        if(word.length() > 0){
            result=true;
        }
        return result;
    }

    /**
     * Method to validate a enrollment
     * @param enrollment from practitioner
     * @return whether it is valid or not
     */
    public boolean validateEnrollment (String enrollment) {
        boolean isValidEnrollment;
        Pattern pattern = Pattern
                .compile("^[S]"+ "[0-9]{8}");
        Matcher mather = pattern.matcher(enrollment);
        isValidEnrollment = mather.find();
        return isValidEnrollment;
    }

    /**
     * Method to know if a practitioner has the necessary credits
     * @param credits from practitioner
     * @return whether it is valid or not
     */
    public boolean validateCreditsPractitioner (String credits) {
        boolean validate = true;
        if(Integer.parseInt(credits) < 250 || Integer.parseInt(credits) > 347) {
            validate = false;
        }
        return validate;
    }
}
