package detectors;

import exceptions.MultipleBitErrorException;
import exceptions.SingleBitErrorException;

import java.util.ArrayList;

public class TwoDimParityBitDetector extends ErrorDetector implements ErrorCorrector {
    public TwoDimParityBitDetector(String[] binaryStrings) {
        super(binaryStrings);
    }


    @Override
    public String[] encode() {

        String[] res = new String[binaryStrings.length+1];
        int cnt = 0;

        for(String element: binaryStrings){
            String ele = parityBit(element);
            res[cnt] = ele;
            cnt++;
        }

        String lastElement = "";

        for(int i = 0; i < res[0].length(); i++){
            int cntOne = 0;
            for(int j = 0; j < res.length -1; j++){
                if(res[j].charAt(i) == '1'){
                    cntOne++;
                }
            }


            if(cntOne % 2 == 0){
                lastElement = lastElement + "0";
            }else{
                lastElement = lastElement + "1";
            }
        }
        res[cnt] = lastElement;
        return res;
    }

    @Override
    public void check() throws SingleBitErrorException, MultipleBitErrorException {

        String[] res = new String[binaryStrings.length+1];
        int cnt = 0;

        int cntOneRow = 0;
        int cntOneCul = 0;

        for(String element: binaryStrings){
            String ele = parityBit(element);
            if(ele.charAt(ele.length() -1) == '1') cntOneRow++;
            res[cnt] = ele;
            cnt++;
        }

        String lastElement = "";

        for(int i = 0; i < res[0].length(); i++){
            int cntOne = 0;
            for(int j = 0; j < res.length -1; j++){
                if(res[j].charAt(i) == '1'){
                    cntOne++;
                }
            }
            if(cntOne % 2 == 0){
                lastElement = lastElement + "0";
            }else if(i != res[0].length()-1 ){
                lastElement = lastElement + "1";
                cntOneCul++;
            }
        }
        res[cnt] = lastElement;

        if(cntOneRow == 0 && cntOneCul == 0) {
            return;
        }else if (cntOneRow == 1 && cntOneCul == 1) {
            throw new SingleBitErrorException();
        } else {
            throw new MultipleBitErrorException();
        }


    }

    private Integer parityBit(Integer binary) {
        String bin = toBinaryStringWithLeadingZeros(binary, bitLength);

        int cntOne = 0;

        int index = 0;

        while((index = bin.indexOf('1', index)) != -1){
            cntOne++;
            index++;
        }

        if(cntOne %2 == 0){
            bin = bin+'0';
        }else {
            bin = bin+'1';
        }

        return Integer.parseInt(bin);
    }

    private String parityBit(String binary) {
        String bin = binary;

        int cntOne = 0;

        int index = 0;

        while((index = bin.indexOf('1', index)) != -1){
            cntOne++;
            index++;
        }

        if(cntOne %2 == 0){
            bin = bin+'0';
        }else {
            bin = bin+'1';
        }

        return bin;
    }


    @Override
    public String[] correct() {
        try {
            this.check();
        } catch (SingleBitErrorException e) {
            String[] wrongEncoded = this.encode();
            String[] newBinaryStrings = new String[binaryStrings.length];
            System.arraycopy(binaryStrings, 0, newBinaryStrings, 0, binaryStrings.length);

            int row = 0;
            int cul = 0;

            for(int i = 0; i < wrongEncoded.length; i++){
                if(wrongEncoded[i].charAt(wrongEncoded[0].length() -1) == '1'){
                    row = i;
                    break;
                }
            }

            for(int i = 0; i < wrongEncoded[0].length(); i++){
                if(wrongEncoded[wrongEncoded.length -1].charAt(i) == '1') {
                    cul = i;
                    break;
                }
            }

            StringBuilder newString = new StringBuilder(binaryStrings[row]);

            if(binaryStrings[row].charAt(cul) == '1') newString.setCharAt(cul, '0');

            else newString.setCharAt(cul, '1');

            newBinaryStrings[row] = newString.toString();

            return newBinaryStrings;

        } catch (MultipleBitErrorException e) {

            return null;
        }

        return binaryStrings;
    }
}
