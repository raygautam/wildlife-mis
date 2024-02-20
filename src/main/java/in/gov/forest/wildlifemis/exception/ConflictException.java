package in.gov.forest.wildlifemis.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class ConflictException extends RuntimeException{
    private final Error error;
    public ConflictException(String msg, Error error) {
        super(msg);
        this.error = error;
    }
}
