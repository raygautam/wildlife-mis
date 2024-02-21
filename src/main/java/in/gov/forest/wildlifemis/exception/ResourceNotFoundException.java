package in.gov.forest.wildlifemis.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException{
    private final Error error;

    public ResourceNotFoundException(String msg, Error error) {
        super(msg);
        this.error = error;
    }
}
