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

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Object>> handleAccessDeniedException(JwtCustomException ex) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(HttpStatus.FORBIDDEN.value(), ex.getError(), null);
        return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DataInsertionException.class)
    public ResponseEntity<ApiResponse<Object>> handleDataInsertionException(DataInsertionException ex)
    {
        ApiResponse<Object> apiResponse = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getError(), null);
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataRetrievalException.class)
    public ResponseEntity<ApiResponse<Object>> handleDataRetrievalException(DataRetrievalException ex)
    {
        ApiResponse<Object> apiResponse = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getError(), null);
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadRequestException(BadRequestException ex)
    {
        ApiResponse<Object> apiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), ex.getError(), null);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiResponse<Object>> handleConflictException(ConflictException ex)
    {
        ApiResponse<Object> apiResponse = new ApiResponse<>(HttpStatus.CONFLICT.value(), ex.getError(), null);
        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFoundException(ResourceNotFoundException ex)
    {
        ApiResponse<Object> apiResponse = new ApiResponse<>(HttpStatus.CONFLICT.value(), ex.getError(), null);
        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFoundException(NotFoundException ex)
    {
        ApiResponse<Object> apiResponse = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), ex.getError(), null);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }
}
