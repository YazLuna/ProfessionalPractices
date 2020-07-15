package logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ValidateAddUser
 * @author Yazmin
 * @version 07/06/2020
 */

public class ValidateAddUser extends ValidateGeneral {

    public boolean validateNameUser (String name) {
        Pattern pattern = Pattern
                .compile("[A-Za-z]{2,30}");
        Matcher mather = pattern.matcher(name);
        boolean result = mather.find();
        return result;
    }

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

    public boolean validateUserName (String word) {
        Pattern pattern = Pattern
                .compile("[A-Za-z0-9]{10,50}");
        Matcher mather = pattern.matcher(word);
        boolean result = mather.find();
        return result;
    }

    public boolean validatePassword (String word) {
        Pattern pattern = Pattern
                .compile("[A-Za-z0-9]{10,20}");
        Matcher mather = pattern.matcher(word);
        boolean result = mather.find();
        return result;
    }

    public boolean validateEmpty (String works) {
        boolean result=false;
        if(works.length() > 0){
            result=true;
        }
        return result;
    }

    public boolean validateEnrollment (String enrollment) {
        boolean isValidEnrollment;
        Pattern pattern = Pattern
                .compile("^[S]"+ "[0-9]{8}");
        Matcher mather = pattern.matcher(enrollment);
        isValidEnrollment = mather.find();
        return isValidEnrollment;
    }

    public boolean validateCreditsPractitioner (String credits) {
        boolean validate = true;
        if(Integer.parseInt(credits) < 250 || Integer.parseInt(credits) > 347) {
            validate = false;
        }
        return validate;
    }

    public String deleteSpace (String works) {
        works = works.replaceAll("\\s+", " ");
        works = works.trim();
        if (works.charAt(works.length() - 1) == ' ') {
            works = works.substring(0, works.length() - 1);
        }
        return works;
    }


    public String deleteAllSpace (String works) {
        works = works.replaceAll(" ", "");
        works = works.replaceAll("\\s+", "");
        if (works.charAt(works.length() - 1) == ' ') {
            works = works.substring(0, works.length() - 1);
        }
        return works;
    }
}
