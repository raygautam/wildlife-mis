package in.gov.forest.wildlifemis.exception;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.commonDTO.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(JwtCustomException.class)
    public ResponseEntity<ApiResponse<Object>> handleJwtExpiredException(JwtCustomException ex) {
//        Error error = new Error("The JWT token has expired");
        ApiResponse<Object> apiResponse = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), Collections.singletonList(ex.getError()), null);
        return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Object>> handleAccessDeniedException(JwtCustomException ex) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(HttpStatus.FORBIDDEN.value(), Collections.singletonList(ex.getError()), null);
        return new ResponseEntity<>(apiResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DataInsertionException.class)
    public ResponseEntity<ApiResponse<Object>> handleDataInsertionException(DataInsertionException ex)
    {
        ApiResponse<Object> apiResponse = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), Collections.singletonList(ex.getError()), null);
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataRetrievalException.class)
    public ResponseEntity<ApiResponse<Object>> handleDataRetrievalException(DataRetrievalException ex)
    {
        ApiResponse<Object> apiResponse = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), Collections.singletonList(ex.getError()), null);
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadRequestException(BadRequestException ex)
    {
        ApiResponse<Object> apiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), Collections.singletonList(ex.getError()), null);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiResponse<Object>> handleConflictException(ConflictException ex)
    {
        ApiResponse<Object> apiResponse = new ApiResponse<>(HttpStatus.CONFLICT.value(), Collections.singletonList(ex.getError()), null);
        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFoundException(ResourceNotFoundException ex)
    {
        ApiResponse<Object> apiResponse = new ApiResponse<>(HttpStatus.CONFLICT.value(), Collections.singletonList(ex.getError()), null);
        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFoundException(NotFoundException ex)
    {
        ApiResponse<Object> apiResponse = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), Collections.singletonList(ex.getError()), null);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    //handleMissingServletRequestParameter like RequestParam value is null or missing specially the foreign key
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        ApiResponse<?> apiResponse=ApiResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(Collections.singletonList(new Error(ex.getParameterName()," fields are required")))
                .build();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
}

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleMethodArgumentException(MethodArgumentNotValidException exception) {
        ApiResponse<?> serviceResponse = new ApiResponse<>();
//        List<Error> errors = new ArrayList<>();
//        exception.getBindingResult().getFieldErrors()
//                .forEach(error -> {
//                    ErrorDTO errorDTO = new ErrorDTO(error.getField(), error.getDefaultMessage());
//
//                    errors.add(
//                            Error.builder()
//                            .field(error.getField())
//                            .errorMessage(error.getDefaultMessage())
//                            .build()
//                    );
//                });

        ApiResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(
                        exception.getBindingResult().getFieldErrors().stream().map(
                                fieldError -> Error.builder()
                                        .field(fieldError.getField())
                                        .errorMessage(fieldError.getDefaultMessage())
                                        .build()
                        ).collect(Collectors.toList())
                ).build();
        return serviceResponse;
    }
}
