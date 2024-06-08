package detectors;

import exceptions.MultipleBitErrorException;
import exceptions.SingleBitErrorException;

import java.util.regex.Pattern;

public class CRCDetector extends ErrorDetector {
    private String generator = "1001";

    public CRCDetector(String[] binaryStrings) {
        super(binaryStrings);
    }

    public String XOR(String in){

        return toBinaryStringWithLeadingZeros(binaryMod(Integer.parseInt(in) , 1001), in.length());

    }

    @Override
    public String[] encode() {

        String[] result = new String[(binaryStrings.length)];

        int cnt = 0;

        for(String element : binaryStrings){
            String ele = element + "000";

            String workingElement = ele.substring(0,4);
            String baghi = element.substring(4,element.length());
            String resXor = workingElement;

            while(baghi.length() > 0){
                if(workingElement.charAt(0) == '1'){
                    resXor = XOR(workingElement);
                }else{
                    resXor = workingElement;
                }

                workingElement = resXor + baghi.charAt(0);
                workingElement = workingElement.substring(workingElement.length()-4,workingElement.length());
                baghi = baghi.substring(1,baghi.length());
            }

            if(workingElement.charAt(0) != '0'){
                resXor = XOR(workingElement);
                workingElement = resXor;
            }

            result[cnt] = element + workingElement.substring(workingElement.length()-3,workingElement.length());
            cnt++;

        }
        return result;

    }

    @Override
    public void check() throws SingleBitErrorException, MultipleBitErrorException {

        int cntOne = 0;

        for(String element: binaryStrings){

            String workingElement = element.substring(0,4);
            String resXor = workingElement;
            String remain = element.substring(4,element.length());



            while(remain.length() > 0){

                if(workingElement.charAt(0) != '0'){
                    resXor = XOR(workingElement);
                }else{
                    resXor = workingElement;
                }

                workingElement = resXor + remain.charAt(0);
                workingElement = workingElement.substring(workingElement.length()-4, workingElement.length());
                remain = remain.substring(1, remain.length());
            }

            if(workingElement.charAt(0) != '0'){
                resXor = XOR(workingElement);
                workingElement = resXor;
            }


            int index = 0;
            if(workingElement.contains("1")){
                while((index = workingElement.indexOf("1", index)) != -1){
                    index++;
                    cntOne++;
                }
            }
        if(cntOne == 1){
            throw new SingleBitErrorException();
        } else if (cntOne > 1){
                throw  new MultipleBitErrorException();
            }
        }



    }

    private Integer binaryMod(Integer divident, Integer divisor) {

        String in = toBinaryStringWithLeadingZeros(divident, 4);


        StringBuilder res = new StringBuilder();

        for(int i = 0 ; i < generator.length(); i++){
            if((in.charAt(i) == '0' && generator.charAt(i) == '0') || (in.charAt(i) == '1' && generator.charAt(i) == '1')){
                res.append('0');
            }else if((in.charAt(i) == '1' && generator.charAt(i) == '0') || (in.charAt(i) == '0' && generator.charAt(i) == '1')){
                res.append('1');
            }

        }

        return Integer.parseInt(res.toString());
    }

}
