package logic;

public class validateProject {
    public boolean validateNameProject (String name) {
        boolean result;
        name = name.toUpperCase();
        int sizeName = name.length();
        if(sizeName>5 && sizeName<26) {
            result = validateWord(name);
        }else{
            result = false;
        }
        return result;
    }

    public boolean validateText (String text) {
        boolean result;
        text = text.toUpperCase();
        int sizeName = text.length();
        if(sizeName>20 && sizeName<101) {
            result = validateWord(text);
        }else{
            result = false;
        }
        return result;
    }

    public boolean validateMethology (String methodology) {
        boolean result;
        methodology = methodology.toUpperCase();
        int sizeName = methodology.length();
        if(sizeName>10 && sizeName<15) {
            result = validateWord(methodology);
        }else{
            result = false;
        }
        return result;
    }

    public boolean validateActivities (String activities) {
        return false;
    }


    public boolean validateDuration (int duration) {
        return false;
    }

    public boolean validateQuiantityPractitioner (int quiantityPractitioner) {
        return false;
    }

    public boolean validateLapse (String Lapse) {
        return false;
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
