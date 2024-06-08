package exceptions;

public class SingleBitErrorException extends Exception {
    private int bitRow;
    private int bitColumn;
    
    public SingleBitErrorException() {
        super("Single bit error detected");
    }

    public SingleBitErrorException(int bitRow, int bitColumn) {
        super("Single bit error detected at " + bitRow + ":" + bitColumn);
        this.bitRow = bitRow;
        this.bitColumn = bitColumn;
    }

    public int getBitRow() {
        return this.bitRow;
    }

    public int getBitColumn() {
        return this.bitColumn;
    }
}
