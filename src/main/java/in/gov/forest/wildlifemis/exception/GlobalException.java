package in.gov.forest.wildlifemis.exception;

import in.gov.forest.wildlifemis.comman.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(JwtCustomException.class)
    public ResponseEntity<ApiResponse<Object>> handleJwtExpiredException(JwtCustomException ex) {
//        Error error = new Error("The JWT token has expired");
        ApiResponse<Object> apiResponse = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), ex.getError(), null);
        return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ApiResponse<Object>> handleAccessDeniedException(JwtCustomException ex) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(HttpStatus.FORBIDDEN.value(), ex.getError(), null);
        return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);
    }
}
