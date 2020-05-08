package logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateProject {
    public boolean validateNotEmpty (String works) {
        boolean result;
        if(works.length()!=0){
            result=true;
        }else{
            result=false;
        }
        return result;
    }
    public boolean validateNameProject (String name) {
        boolean result;
        Pattern pattern = Pattern
                .compile("[A-Za-z]{10,25}");
        Matcher mather = pattern.matcher(name);
        result = mather.find();
        return result;
    }

    public String deleteSpace (String works) {
        if(works.length()>0) {
            works = works.replaceAll("\\s+", " ");
            if (works.charAt(works.length() - 1) == ' ') {
                works = works.substring(0, works.length() - 1);
            }
        }
        return works;
    }


    public boolean validateMethology (String methodology) {
        boolean result;
        Pattern pattern = Pattern
                .compile("[A-Za-z]{10,15}");
        Matcher mather = pattern.matcher(methodology);
        result = mather.find();
        return result;
    }

    public boolean validateTextArea (String textArea) {
        boolean result;
        Pattern pattern = Pattern
                .compile("[A-Za-z_0-9]{30,100}");
        Matcher mather = pattern.matcher(textArea);
        result = mather.find();
        return result;
    }

    public boolean validateText (String text) {
        boolean result;
        Pattern pattern = Pattern
                .compile("[A-Za-z_0-9]{30,255}");
        Matcher mather = pattern.matcher(text);
        result = mather.find();
        return result;
    }

    public boolean validateLapse (String lapse) {
        boolean result;
        Pattern pattern = Pattern
                .compile("[A-Za-z_0-9]{6,30}");
        Matcher mather = pattern.matcher(lapse);
        result = mather.find();
        return result;
    }

    public boolean validateDuration (int duration) {
        boolean result;
        if(duration != 0 && duration >=5){
            result = true;
        }else {
            result = false;
        }
        return result;
    }
    public boolean validateQuiantityPractitioner (int quiantityPractitioner) {
        boolean result;
        if(quiantityPractitioner != 0 && quiantityPractitioner >=2){
            result = true;
        }else {
            result = false;
        }
        return result;
    }
}
