package logic;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class validateDataPerson {

    public boolean validateName (String name) {
        boolean result;
        name = name.toUpperCase();
        int sizeName = name.length();
        if(sizeName>3 && sizeName<51) {
            result = validateWord(name);
        }else{
            result = false;
        }
        return result;
    }
    public boolean validateLastName (String lastName) {
        boolean result;
        lastName = lastName.toUpperCase();
        int sizeLastName = lastName.length();
        if(sizeLastName>3 && sizeLastName<51) {
            result = validateWord(lastName);
        }else{
            result = false;
        }
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
        charge = charge.toUpperCase();
        int sizeCharge = charge.length();
        if(sizeCharge>0 && sizeCharge<26) {
            result = validateWord(charge);
        } else {
          result = false;
        }
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
