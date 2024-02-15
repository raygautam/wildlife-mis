package in.gov.forest.wildlifemis.exception;

import java.util.Objects;

public class Error {
    private String message;

    public Error(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Error error = (Error) o;
        return Objects.equals(message, error.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }
}
