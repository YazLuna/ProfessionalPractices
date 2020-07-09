package logic;

import dataaccess.Number;

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
        boolean isValidNameProject;
        Pattern pattern = Pattern
                .compile("[A-Za-z]{8,25}");
        Matcher mather = pattern.matcher(name);
        isValidNameProject = mather.find();
        return isValidNameProject;
    }

    /**
     * Method to validate methodology of Project
     * @param methodology The methodology parameter defines the methodology of the project
     * @return If the methodology is valid
     */
    public boolean validateMethology (String methodology) {
        boolean isValidMethology;
        Pattern pattern = Pattern
                .compile("[A-Za-z_\\s]{10,15}");
        Matcher mather = pattern.matcher(methodology);
        isValidMethology = mather.find();
        return isValidMethology;
    }

    /**
     * Method to validate text with 100 character of Project
     * @param textArea The textArea parameter defines the text with 100 character of the project
     * @return If the textArea is valid
     */
    public boolean validateTextArea (String textArea) {
        boolean isValidTextArea;
        Pattern pattern = Pattern
                .compile("[A-Za-z_\\s_0-9]{20,100}");
        Matcher mather = pattern.matcher(textArea);
        isValidTextArea = mather.find();
        return isValidTextArea;
    }

    /**
     * Method to validate text with 255 character of Project
     * @param text The text parameter defines the text with 255 character of the project
     * @return If the text is valid
     */
    public boolean validateText (String text) {
        boolean isValidText;
        Pattern pattern = Pattern
                .compile("[A-Za-z_\\s_0-9]{20,255}");
        Matcher mather = pattern.matcher(text);
        isValidText = mather.find();
        return isValidText;
    }

    /**
     * Method to validate duration of Project
     * @param duration The duration parameter defines the duration of the project
     * @return If the duration is valid
     */
    public boolean validateDuration (int duration) {
        boolean isValidDuration;
        if(duration >=Number.FIVE.getNumber()){
            isValidDuration = true;
        }else {
            isValidDuration = false;
        }
        return isValidDuration;
    }

    /**
     * Method to validate quiantityPractitioner  of Project
     * @param quiantityPractitioner The quiantityPractitioner parameter defines the quiantityPractitioner of the project
     * @return If the quiantityPractitioner is valid
     */
    public boolean validateQuiantityPractitioner (int quiantityPractitioner) {
        boolean isValidQuiantityPractitioner;
        if(quiantityPractitioner >= Number.TWO.getNumber()){
            isValidQuiantityPractitioner = true;
        }else {
            isValidQuiantityPractitioner = false;
        }
        return isValidQuiantityPractitioner;
    }
}
