package in.gov.forest.wildlifemis.exception;

import lombok.Getter;


@Getter
public class BadRequestException extends RuntimeException{

    private final Error error;

    public BadRequestException(String msg, Error error) {
        super(msg);
        this.error = error;
    }
}
