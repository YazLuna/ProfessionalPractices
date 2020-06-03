package logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class validate Data of Project
 * @author MARTHA
 * @version 08/05/2020
 */
public class ValidateProject extends ValidateGeneral {

    /**
     * Method to validate name of Project
     * @param name The name parameter defines the name of the project
     * @return If the name is valid
     */
    public boolean validateName (String name) {
        boolean result;
        Pattern pattern = Pattern
                .compile("[A-Za-z]{8,25}");
        Matcher mather = pattern.matcher(name);
        result = mather.find();
        return result;
    }

    /**
     * Method to validate methodology of Project
     * @param methodology The methodology parameter defines the methodology of the project
     * @return If the methodology is valid
     */
    public boolean validateMethology (String methodology) {
        boolean result;
        Pattern pattern = Pattern
                .compile("[A-Za-z_\\s]{10,15}");
        Matcher mather = pattern.matcher(methodology);
        result = mather.find();
        return result;
    }

    /**
     * Method to validate text with 100 character of Project
     * @param textArea The textArea parameter defines the text with 100 character of the project
     * @return If the textArea is valid
     */
    public boolean validateTextArea (String textArea) {
        boolean result;
        Pattern pattern = Pattern
                .compile("[A-Za-z_\\s_0-9]{20,100}");
        Matcher mather = pattern.matcher(textArea);
        result = mather.find();
        return result;
    }

    /**
     * Method to validate text with 255 character of Project
     * @param text The text parameter defines the text with 255 character of the project
     * @return If the text is valid
     */
    public boolean validateText (String text) {
        boolean result;
        Pattern pattern = Pattern
                .compile("[A-Za-z_\\s_0-9]{20,255}");
        Matcher mather = pattern.matcher(text);
        result = mather.find();
        return result;
    }

    /**
     * Method to validate lapse of Project
     * @param lapse The lapse parameter defines the lapse of the project
     * @return If the lapse is valid
     */
    public boolean validateLapse (String lapse) {
        boolean result;
        Pattern pattern = Pattern
                .compile("[A-Za-z_0-9]{6,30}");
        Matcher mather = pattern.matcher(lapse);
        result = mather.find();
        return result;
    }

    /**
     * Method to validate duration of Project
     * @param duration The duration parameter defines the duration of the project
     * @return If the duration is valid
     */
    public boolean validateDuration (int duration) {
        boolean result;
        if(duration >=5){
            result = true;
        }else {
            result = false;
        }
        return result;
    }

    /**
     * Method to validate quiantityPractitioner  of Project
     * @param quiantityPractitioner The quiantityPractitioner parameter defines the quiantityPractitioner of the project
     * @return If the quiantityPractitioner is valid
     */
    public boolean validateQuiantityPractitioner (int quiantityPractitioner) {
        boolean result;
        if(quiantityPractitioner >=2){
            result = true;
        }else {
            result = false;
        }
        return result;
    }
}
