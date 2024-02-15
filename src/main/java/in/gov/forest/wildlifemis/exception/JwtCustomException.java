package in.gov.forest.wildlifemis.exception;

import lombok.Getter;

@Getter
public class JwtCustomException extends RuntimeException{
    private final Error error;

    public JwtCustomException(String msg, Error error) {
        super(msg);
        this.error = error;
    }

}
