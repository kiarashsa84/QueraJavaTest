package detectors;

import exceptions.MultipleBitErrorException;
import exceptions.SingleBitErrorException;


public class ChecksumDetector extends detectors.ErrorDetector {
    public ChecksumDetector(String[] binaryStrings) {
        super(binaryStrings);
    }
    
    private Integer onesComplement(Integer binary) {
        //int binary = 1101 -> 0010

        String num = toBinaryStringWithLeadingZeros(binary, bitLength);
        StringBuilder res = new StringBuilder(); 

        for(char c : num.toCharArray()){
            if (c == '1') res.append('0'); 
            else res.append('1');     
        }

        num = res.toString(); 
        

        return Integer.parseInt(num); 
    }

    public Integer wraparoundAdd(Integer a, Integer b) {
        
        String aString = toBinaryStringWithLeadingZeros(a, bitLength);
        String bString = toBinaryStringWithLeadingZeros(b, bitLength);

        StringBuilder res = new StringBuilder();

        boolean carry = false;
        boolean flag = false;          
        //0101 + 0001
        for(int i = bitLength - 1; i >= 0; i--){
            if(aString.charAt(i) == '1' & bString.charAt(i) == '1') {
                if (carry){
                    res.append('1');
                } else{
                    res.append('0');
                    carry = true;
                }
            }else if(aString.charAt(i) == '1' && bString.charAt(i) == '0'){
                if(carry) res.append('0');
                else res.append('1');
            }else if(aString.charAt(i) == '0' && bString.charAt(i) == '1'){
                if(carry) res.append('0');
                else res.append('1');
            }else if (aString.charAt(i) == '0' && bString.charAt(i) == '0') {
                if(carry){
                    res.append('1');
                    carry = false;
                }else{
                    res.append('0');
                }

            }

        }
        if(carry){
            flag = true; 
            return wraparoundAdd(Integer.parseInt(res.reverse().toString()), 1);
            
        }
        if(!flag){

            return Integer.parseInt(res.reverse().toString());
        }
        return null; 
    }

    @Override
    public String[] encode() {
        int res = Integer.parseInt(binaryStrings[0]); 
        
        for (int i = 1 ; i < binaryStrings.length; i++){
            res = wraparoundAdd(res, Integer.parseInt(binaryStrings[i])); 
        }
        
        String resString = toBinaryStringWithLeadingZeros(res, bitLength); 

        StringBuilder complementResult = new StringBuilder();

        for(char c: resString.toCharArray()){
            if(c == '1'){
                complementResult.append('0');
            } else {
                complementResult.append('1');
            }
        }

        String[] encoded = new String[binaryStrings.length + 1];
        System.arraycopy(binaryStrings, 0, encoded, 0, binaryStrings.length);
        encoded[binaryStrings.length] = complementResult.toString();


        return encoded;
    }

    @Override
    public void check() throws SingleBitErrorException, MultipleBitErrorException {

        int res = Integer.parseInt(binaryStrings[0]);

        for(int i = 1; i < binaryStrings.length; i++){
            res = wraparoundAdd(res, Integer.parseInt(binaryStrings[i]));
        }


        String resString = toBinaryStringWithLeadingZeros(res, bitLength);

        StringBuilder complementResult = new StringBuilder();

        for(char c: resString.toCharArray()){
            if(c == '1'){
                complementResult.append('0');
            } else {
                complementResult.append('1');
            }
        }

        String result = complementResult.toString();


        int cntOne = 0;
        int cntZero = 0;
        int in = 0;

        while((in = result.indexOf("0" ,in)) != -1){
            in++;
            cntZero++;
        }

        while((in = result.indexOf("1" ,in)) != -1){
            in++;
            cntOne++;
        }

        if (result.contains("1")){
            if(cntOne == 1 || cntZero == 1){
                throw new SingleBitErrorException();
            }else{
                throw new MultipleBitErrorException();
            }
        }


    }

}
