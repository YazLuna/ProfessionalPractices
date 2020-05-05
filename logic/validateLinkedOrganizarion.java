package logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class validateLinkedOrganizarion {

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
                .compile("[0-9]{10}");
        Matcher mather = pattern.matcher(phoneNumber);
        result = mather.find();
        return result;
    }

    public boolean validateAddress (String address) {
        boolean result;
        Pattern pattern = Pattern
                .compile("[A-Za-z]{20,50}");
        Matcher mather = pattern.matcher(address);
        result = mather.find();
        return result;
    }
    public boolean validateCity (String city) {
        boolean result;
        Pattern pattern = Pattern
                .compile("[A-Za-z]{6,25}");
        Matcher mather = pattern.matcher(city);
        result = mather.find();
        return result;
    }

    public boolean validateState (String state) {
        boolean result;
        Pattern pattern = Pattern
                .compile("[A-Za-z]{6,25}");
        Matcher mather = pattern.matcher(state);
        result = mather.find();
        return result;
    }

    public boolean validateSector (String sector) {
        boolean result;
        Pattern pattern = Pattern
                .compile("[A-Za-z]{6,25}");
        Matcher mather = pattern.matcher(sector);
        result = mather.find();
        return result;
    }
}
