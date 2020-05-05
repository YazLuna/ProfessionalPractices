package logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class validateProject {
    public boolean validateNameProject (String name) {
        boolean result;
        Pattern pattern = Pattern
                .compile("[A-Za-z]{5,25}");
        Matcher mather = pattern.matcher(name);
        result = mather.find();
        return result;
    }

    public boolean validateText (String text) {
        boolean result;
        Pattern pattern = Pattern
                .compile("[A-Za-z]{30,100}");
        Matcher mather = pattern.matcher(text);
        result = mather.find();
        return result;
    }

    public boolean validateMethology (String methodology) {
        boolean result;
        Pattern pattern = Pattern
                .compile("[A-Za-z]{10,15}");
        Matcher mather = pattern.matcher(methodology);
        result = mather.find();
        return result;
    }

    public boolean validateActivities (String activities) {
        return false;
    }


    public boolean validateLapse (String lapse) {
        boolean result;
        Pattern pattern = Pattern
                .compile("[A-Za-z0-9]{10,30}");
        Matcher mather = pattern.matcher(lapse);
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
