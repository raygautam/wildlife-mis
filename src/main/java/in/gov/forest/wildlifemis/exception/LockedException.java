package in.gov.forest.wildlifemis.exception;

import jakarta.annotation.Generated;

public class LockedException extends RuntimeException {
    public LockedException(Error error) {
        super(String.valueOf(error.getErrorMessage()));
    }

}
