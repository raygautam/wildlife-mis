package in.gov.forest.wildlifemis.exception;

import lombok.Getter;

@Getter
public class IOCustomException extends RuntimeException {
    private final Error error;

    public IOCustomException(String msg, Error error) {
        super(msg);
        this.error = error;
    }
}
