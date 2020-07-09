package logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class validate Data of Linked Organization
 * @author MARTHA
 * @version 08/05/2020
 */
public class ValidateLinkedOrganization extends ValidateGeneral{

    /**
     * Method to validate name of Linked Organization
     * @param name The name parameter defines the name of the Linked Organization
     * @return If the name is valid
     */
    public boolean validateName (String name) {
        boolean isValidName;
        Pattern pattern = Pattern
                .compile("[A-Za-z]{6,100}");
        Matcher mather = pattern.matcher(name);
        isValidName = mather.find();
        return isValidName;
    }

    /**
     * Method to validate address of Linked Organization
     * @param address The address parameter defines the address of the Linked Organization
     * @return If the address is valid
     */
    public boolean validateAddress (String address) {
        boolean isValidAddress;
        Pattern pattern = Pattern
                .compile("[A-Za-z_0-9_\\s]{15,100}");
        Matcher mather = pattern.matcher(address);
        isValidAddress = mather.find();
        return isValidAddress;
    }

    /**
     * Method to validate city,state and sector of Linked Organization
     * @param comboBox The comboBox parameter defines the city,state and sector of the Linked Organization
     * @return If the comboBox is valid
     */
    public boolean validateComboBox (String comboBox) {
        boolean isValidComboBox;
        Pattern pattern = Pattern
                .compile("[A-Za-z]{6,30}");
        Matcher mather = pattern.matcher(comboBox);
        isValidComboBox = mather.find();
        return isValidComboBox;
    }

    /**
     * Method to validate number Users of Linked Organization
     * @param usersOrganization The numberUsers parameter defines the number Users of the Linked Organization
     * @return If the number Users is valid
     */
    public boolean validateUsersOrganization (String usersOrganization) {
        boolean isUserOrganization;
        Pattern pattern = Pattern
                .compile("[A-Za-z_,]{6,100}");
        Matcher mather = pattern.matcher(usersOrganization);
        isUserOrganization = mather.find();
        if(!isUserOrganization){
            Pattern patternUsersFinal = Pattern
                    .compile("[A-Za-z]{6,100}");
            Matcher matherFinal = patternUsersFinal.matcher(usersOrganization);
            isUserOrganization = matherFinal.find();
        }
        return isUserOrganization;
    }
}
