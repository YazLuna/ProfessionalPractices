package logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ValidateGeneral
 * @author MARTHA
 * @version 01/06/2020
 */

public class ValidateGeneral {
    public boolean validateName (String name) {
        boolean isValidName;
        Pattern pattern = Pattern
                .compile("[A-Za-z]{3,50}");
        Matcher mather = pattern.matcher(name);
        isValidName = mather.find();
        return isValidName;
    }

    public String deleteSpace (String words) {
        if(words.length()>0) {
            words = words.replaceAll("\\s+", " ");
            if (words.charAt(words.length() - 1) == ' ') {
                words = words.substring(0, words.length() - 1);
            }
        }
        return words;
    }

    public boolean validateNotEmpty (String words) {
        boolean isValid;
        if(words.length()!=0){
            isValid=true;
        }else{
            isValid=false;
        }
        return isValid;
    }

    public boolean validateEmail (String email) {
        boolean isValidEmail;
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(email);
        isValidEmail = mather.find();
        return isValidEmail;
    }

    public boolean validatePhoneNumber (String phoneNumber) {
        boolean isValidPhoneNumber;
        Pattern pattern = Pattern
                .compile("[0-9]{10}");
        Matcher mather = pattern.matcher(phoneNumber);
        isValidPhoneNumber = mather.find();
        return isValidPhoneNumber;
    }
}
