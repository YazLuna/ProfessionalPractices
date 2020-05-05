package logic;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class validateDataPerson {

    public boolean validateName (String name) {
        boolean result;
        Pattern pattern = Pattern
                .compile("[A-Za-z]{3,50}");
        Matcher mather = pattern.matcher(name);
        result = mather.find();
        return result;
    }
    public boolean validateLastName (String lastName) {
        boolean result;
        Pattern pattern = Pattern
                .compile("[A-Za-z]{3,50}");
        Matcher mather = pattern.matcher(lastName);
        result = mather.find();
        return result;
    }
    public boolean validateEmail (String email) {
        boolean result;
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(email);
        result = mather.find();
        return result;
    }

    public boolean validateCharge (String charge) {
        boolean result;
        Pattern pattern = Pattern
                .compile("[A-Za-z]{6,25}");
        Matcher mather = pattern.matcher(charge);
        result = mather.find();
        return result;
    }

    public boolean validateWord(String word) {
        boolean result = true;
        int index = 0;
        int valueASCII;
        int sizeWord= word.length();
        while(result && index<sizeWord){
            valueASCII = (int)word.charAt(index);
            if(valueASCII == 32 && ((int)word.charAt(index - 1)) == 32){
                result = false;
            }else{
                if((valueASCII < 65 && valueASCII!=32) || (valueASCII > 90 && valueASCII != 165)){
                    result = false;
                }
            }
            index++;
        }
        return result;
    }
}
