package in.gov.forest.wildlifemis.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    private final Error error;

    public NotFoundException(String msg, Error error) {
        super(msg);
        this.error = error;
    }


}
