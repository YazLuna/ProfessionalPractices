package logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DAO User
 * @author Yazmin
 * @version 08/05/2020
 */

public class ValidateAddUser {
    public boolean validateName (String name) {
        name = deleteSpace(name);
        boolean result;
        Pattern pattern = Pattern
                .compile("[A-Za-z]{3,30}");
        Matcher mather = pattern.matcher(name);
        result = mather.find();
        return result;
    }

    public boolean validateLastName (String lastName) {
        lastName = deleteSpace(lastName);
        boolean result;
        Pattern pattern = Pattern
                .compile("[A-Za-z]{3,30}");
        Matcher mather = pattern.matcher(lastName);
        result = mather.find();
        return result;
    }

    public boolean validateEmail (String email) {
        email = deleteSpace(email);
        boolean result;
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(email);
        result = mather.find();
        return result;
    }

    public boolean validatePhone (String phone) {
        phone = deleteSpace(phone);
        boolean result;
        Pattern pattern = Pattern
                .compile("[0-9]");
        Matcher mather = pattern.matcher(phone);
        result = mather.find();
        if(phone.length()!=10) {
            result = false;
        }
        return result;
    }

    public boolean validateEmpty (String works) {
        boolean result=true;
        if(works.length()!=0){
            result=false;
        }
        return result;
    }

    public String deleteSpace (String works) {
        works = works.replaceAll("\\s+", " ");
        works = works.trim();
        if (works.charAt(works.length() - 1) == ' ') {
            works = works.substring(0, works.length() - 1);
        }
        return works;
    }

    public boolean validateStaffNumber(String staffNumber){
        staffNumber = deleteAllSpace(staffNumber);
        try {
            Integer.parseInt(staffNumber);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
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
