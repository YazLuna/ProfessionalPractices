package logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateGeneral {
    /**
     * Method to validate name
     * @param name The name parameter defines the name of the person
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
     * Method to delete space of words
     * @param words The words parameter defines the works
     * @return The words without spaces
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
     * Method to validate words
     * @param words The words parameter defines the words
     * @return If the words is valid
     */
    public boolean validateNotEmpty (String words) {
        boolean isValid;
        if(words.length()!=0){
            isValid=true;
        }else{
            isValid=false;
        }
        return isValid;
    }

    /**
     * Method to validate email
     * @param email The email parameter defines the email
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
     * Method to validate phone Number
     * @param phoneNumber The phoneNumber parameter defines the phoneNumber
     * @return If the phoneNumber is valid
     */
    public boolean validatePhoneNumber (String phoneNumber) {
        boolean isValidPhoneNumber;
        Pattern pattern = Pattern
                .compile("[0-9]{10}");
        Matcher mather = pattern.matcher(phoneNumber);
        isValidPhoneNumber = mather.find();
        return isValidPhoneNumber;
    }
}
