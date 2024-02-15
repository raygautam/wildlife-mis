package in.gov.forest.wildlifemis.exception;

import lombok.Getter;

@Getter
public class DataRetrievalException extends RuntimeException {
    private final Error error;

    public DataRetrievalException(String msg, Error error) {
        super(msg);
        this.error = error;
    }
}
