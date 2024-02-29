package in.gov.forest.wildlifemis.exception;

import lombok.Getter;

@Getter
public class MaxUploadSizeExceededException extends RuntimeException {
//    private final Error error;

    public MaxUploadSizeExceededException(String message, Error error) {
        super(String.valueOf(error.getErrorMessage()));
//        this.error = error;
    }
}
