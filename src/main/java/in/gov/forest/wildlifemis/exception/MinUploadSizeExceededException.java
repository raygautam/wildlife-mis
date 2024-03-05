package in.gov.forest.wildlifemis.exception;

public class MinUploadSizeExceededException extends RuntimeException {
    public MinUploadSizeExceededException(Error error) {
        super(String.valueOf(error.getErrorMessage()));
    }
}
