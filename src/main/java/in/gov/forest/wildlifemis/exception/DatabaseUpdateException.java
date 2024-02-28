package in.gov.forest.wildlifemis.exception;

import lombok.Getter;

@Getter
public class DatabaseUpdateException extends RuntimeException{
    private final Error error;

    public DatabaseUpdateException(String msg, Error error) {
        super(msg);
        this.error = error;
    }
}
