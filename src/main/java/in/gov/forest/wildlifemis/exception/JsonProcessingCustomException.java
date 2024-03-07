package in.gov.forest.wildlifemis.exception;

import lombok.Getter;

@Getter
public class JsonProcessingCustomException extends RuntimeException {
    private final Error error;

    public JsonProcessingCustomException(String msg, Error error) {
        super(msg);
        this.error = error;
    }
}
