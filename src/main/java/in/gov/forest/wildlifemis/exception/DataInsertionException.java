package in.gov.forest.wildlifemis.exception;

import lombok.Getter;

@Getter
public class DataInsertionException extends RuntimeException{
    private final Error error;

    public DataInsertionException(String msg, Error error) {
        super(msg);
        this.error = error;
    }
}
