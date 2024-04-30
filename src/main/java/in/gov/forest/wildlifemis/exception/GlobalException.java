package in.gov.forest.wildlifemis.exception;

import in.gov.forest.wildlifemis.common.ApiResponse;
import in.gov.forest.wildlifemis.commonDTO.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<Object> handleLockedException(LockedException e) {
//        Error error = new Error("",e.getMessage());
        ApiResponse<Object> apiResponse = new ApiResponse<>(
                HttpStatus.UNAUTHORIZED.value(),
                Collections.singletonList(new Error("",e.getMessage())),
                null);
        return new ResponseEntity<>(apiResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleForbiddenException(DataIntegrityViolationException e) {
//        Error error = new Error("",e.getMessage());
        ApiResponse<Object> apiResponse = new ApiResponse<>(
                HttpStatus.CONFLICT.value(),
                Collections.singletonList(new Error("",e.getMessage())),
                null);
        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
//        Error error = new Error("",e.getMessage());
        ApiResponse<Object> apiResponse = new ApiResponse<>(
                e.getStatusCode().value(),
//                HttpStatus.METHOD_NOT_ALLOWED.value(),
                Collections.singletonList(new Error("",e.getMessage())),
                null);
        return new ResponseEntity<>(apiResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(JwtCustomException.class)
    public ResponseEntity<ApiResponse<Object>> handleJwtExpiredException(JwtCustomException ex) {
//        Error error = new Error("The JWT token has expired");
        ApiResponse<Object> apiResponse = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), Collections.singletonList(ex.getError()), null);
        return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(JsonProcessingCustomException.class)
    public ResponseEntity<ApiResponse<Object>> handleJsonProcessingCustomException(JsonProcessingCustomException ex) {
//        Error error = new Error("The JWT token has expired");
        ApiResponse<Object> apiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), Collections.singletonList(ex.getError()), null);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(IOCustomException.class)
    public ResponseEntity<ApiResponse<Object>> handleIOCustomException(IOCustomException ex) {
//        Error error = new Error("The JWT token has expired");
        ApiResponse<Object> apiResponse = new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), Collections.singletonList(ex.getError()), null);
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiResponse<Object>> handleMaxSizeException(MaxUploadSizeExceededException ex) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), Collections.singletonList(new Error("",ex.getMessage())), null);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MinUploadSizeExceededException.class)
    public ResponseEntity<ApiResponse<Object>> handleMinSizeException(MinUploadSizeExceededException ex) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), Collections.singletonList(new Error("",ex.getMessage())), null);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Object>> handleAccessDeniedException(AccessDeniedException ex) {
        ApiResponse<Object> apiResponse = new ApiResponse<>(HttpStatus.FORBIDDEN.value(), Collections.singletonList(new Error("",ex.getMessage())), null);
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

    //handleMissingServletRequestParameter like RequestParam value is null
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        ApiResponse<?> apiResponse=ApiResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(Collections.singletonList(new Error(ex.getParameterName()," fields are required")))
                .build();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        ApiResponse<?> apiResponse=ApiResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(Collections.singletonList(new Error("",ex.getMessage())))
                .build();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    //handleMissingServletRequestParameter like RequestPart value is null
    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<Object> handleMissingServletRequestPartException(MissingServletRequestPartException ex) {
        ApiResponse<?> apiResponse=ApiResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error(Collections.singletonList(new Error(ex.getRequestPartName()," fields are required")))
                .build();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleMethodArgumentException(MethodArgumentNotValidException exception) {
//        ApiResponse<?> serviceResponse = new ApiResponse<>();
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
//        return serviceResponse;

//        return ApiResponse.builder()
//                .status(HttpStatus.BAD_REQUEST.value())
//                .error(
//                        exception.getBindingResult().getFieldErrors().stream().map(
//                                fieldError -> Error.builder()
//                                        .field(fieldError.getField())
//                                        .errorMessage(fieldError.getDefaultMessage())
//                                        .build()
//                        ).toList()
//
//                ).build();


        ApiResponse<?> serviceResponse = new ApiResponse<>();
        List<ErrorDTO> errors = new ArrayList<>();
//        exception.getBindingResult().getFieldErrors()
//                .forEach(error -> {
//                    ErrorDTO errorDTO = new ErrorDTO(error.getField(), error.getDefaultMessage());
//                    errors.add(errorDTO);
//                });
        serviceResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        serviceResponse.setError( exception.getBindingResult().getFieldErrors().stream().map(
                                fieldError -> Error.builder()
                                        .field(fieldError.getField())
                                        .errorMessage(fieldError.getDefaultMessage())
                                        .build()
                        ).toList());
        return serviceResponse;
    }



    //Observability example
//    @ExceptionHandler(RuntimeException.class)
//    public ProblemDetail onException(RuntimeException ex) {
//        return ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(400), ex.getMessage());
//    }

}
