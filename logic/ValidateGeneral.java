package logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  Class validate General
 * @author MARTHA
 * @version 01/06/2020
 */

public class ValidateGeneral {

    /**
     * Method to validate name
     * @param name The name parameter defines the name
     * @return If the name is valid
     */
    public boolean validateName (String name) {
        boolean isValidName;
        Pattern pattern = Pattern
                .compile("[A-Za-z]{3,50}");
        Matcher mather = pattern.matcher(name);
        isValidName = mather.find();
        return isValidName;
    }

    /**
     * Method to remove spaces from the words
     * @param words defines the words
     * @return the words without spaces
     */
    public String deleteSpace (String words) {
        if(words.length()>0) {
            words = words.replaceAll("\\s+", " ");
            if (words.charAt(words.length() - 1) == ' ') {
                words = words.substring(0, words.length() - 1);
            }
        }
        return words;
    }

    /**
     * Method to validate if the words is empty
     * @param words defines the words
     * @return If the words is valid
     */
    public boolean validateNotEmpty (String words) {
        boolean isValid;
        if(words.length()!= Number.ZERO.getNumber()){
            isValid=true;
        }else{
            isValid=false;
        }
        return isValid;
    }

    /**
     * Method to validate email
     * @param email  The email parameter defines the email
     * @return If the email is valid
     */
    public boolean validateEmail (String email) {
        boolean isValidEmail;
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(email);
        isValidEmail = mather.find();
        return isValidEmail;
    }

    /**
     * Method to validate phone number
     * @param phoneNumber defines the phone number
     * @return If the phone number is valid
     */
    public boolean validatePhoneNumber (String phoneNumber) {
        boolean isValidPhoneNumber;
        Pattern pattern = Pattern
                .compile("[0-9]{10}");
        Matcher mather = pattern.matcher(phoneNumber);
        isValidPhoneNumber = mather.find();
        return isValidPhoneNumber;
    }

    /**
     * Method to validate extensions
     * @param extensions defines the extensions of the phone number
     * @return If the extensions is valid
     */
    public boolean validateExtensions (String extensions) {
        boolean isValidExtensions;
        Pattern pattern = Pattern
                .compile("[0-9_,_\\s]{4,30}");
        Matcher mather = pattern.matcher(extensions);
        isValidExtensions = mather.find();
        if(isValidExtensions){
            String remplaceAllExtensions = extensions.replaceAll("\\s", "");
            int lengthExtensions=1;
            while(lengthExtensions<remplaceAllExtensions.length() && isValidExtensions) {
                if (remplaceAllExtensions.charAt(lengthExtensions-1) == ',' && remplaceAllExtensions.charAt(lengthExtensions) == ',') {
                    isValidExtensions = false;
                }
                lengthExtensions++;
            }
        }
        return isValidExtensions;
    }
}
