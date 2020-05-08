package logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateLinkedOrganizarion {

    public boolean validateNameLinked (String name) {
        boolean result;
        Pattern pattern = Pattern
                .compile("[A-Za-z]{3,50}");
        Matcher mather = pattern.matcher(name);
        result = mather.find();
        return result;
    }

    public boolean validatePhoneNumber (String phoneNumber) {
        boolean result;
        Pattern pattern = Pattern
                .compile("[0-9]{10,10}");
        Matcher mather = pattern.matcher(phoneNumber);
        result = mather.find();
        return result;
    }

    public boolean validateAddress (String address) {
        boolean result;
        Pattern pattern = Pattern
                .compile("[A-Za-z_0-9]{15,50}");
        Matcher mather = pattern.matcher(address);
        result = mather.find();
        return result;
    }

    public boolean validateComboBox (String comboBox) {
        boolean result;
        Pattern pattern = Pattern
                .compile("[A-Za-z]{6,25}");
        Matcher mather = pattern.matcher(comboBox);
        result = mather.find();
        return result;
    }

    public boolean validateNumberUsers (int numberUsers) {
        boolean result;
        if(numberUsers != 0 && numberUsers > 3){
            result = true;
        }else {
            result = false;
        }
        return result;
    }
}
