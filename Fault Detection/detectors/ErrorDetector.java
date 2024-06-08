package detectors;

import exceptions.MultipleBitErrorException;
import exceptions.SingleBitErrorException;

public abstract class ErrorDetector {
    int bitLength;
    String[] binaryStrings;

    public ErrorDetector(String[] binaryStrings) {
        this.bitLength = binaryStrings[0].length();
        this.binaryStrings = binaryStrings;
    }

    abstract public String[] encode();
    
    abstract public void check() throws SingleBitErrorException, MultipleBitErrorException;


    public static String toBinaryStringWithLeadingZeros(Integer number, Integer length) {
        String numberString = Integer.toString(number);
        if (numberString.length() < length) {
            StringBuilder str = new StringBuilder(); 
            int diff = length - numberString.length(); 

            for (int i = 0 ; i < diff & diff != 0; i++){
                str.append("0"); 
            }

            str.append(numberString); 
            return str.toString(); 
        }

        return numberString; 
    }

    public String[]  correct(){
        return null;
    }
}
