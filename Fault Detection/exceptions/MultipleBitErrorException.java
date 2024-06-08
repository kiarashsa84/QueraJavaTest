package exceptions;

public class MultipleBitErrorException extends Exception {
    public MultipleBitErrorException() {
        super("Multiple bit error detected");
    }
}
