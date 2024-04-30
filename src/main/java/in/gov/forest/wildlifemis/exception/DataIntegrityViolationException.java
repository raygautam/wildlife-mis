package in.gov.forest.wildlifemis.exception;

public class DataIntegrityViolationException extends RuntimeException{
    public DataIntegrityViolationException(String msg) {
        super(msg);
    }
}
