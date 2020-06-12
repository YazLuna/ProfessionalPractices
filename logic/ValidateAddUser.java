package logic;

import com.sun.source.tree.BreakTree;
import org.junit.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ValidateAddUser
 * @author Yazmin
 * @version 07/06/2020
 */

public class ValidateAddUser extends ValidateGeneral {

    public boolean validateNameUser (String name) {
        Pattern pattern = Pattern
                .compile("[A-Za-z]{3,30}");
        Matcher mather = pattern.matcher(name);
        boolean result = mather.find();
        return result;
    }

    public boolean validateEmpty (String works) {
        boolean result=false;
        if(works.length() > 0){
            result=true;
        }
        return result;
    }

    public boolean validateEnrollment (String enrollment) {
        boolean isValidEnrollment;
        Pattern pattern = Pattern
                .compile("^[S]"+ "[0-9]{8}");
        Matcher mather = pattern.matcher(enrollment);
        isValidEnrollment = mather.find();
        return isValidEnrollment;
    }

    public String deleteSpace (String works) {
        works = works.replaceAll("\\s+", " ");
        works = works.trim();
        if (works.charAt(works.length() - 1) == ' ') {
            works = works.substring(0, works.length() - 1);
        }
        return works;
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
