package org.androidspringbootbackend.exception;

import lombok.extern.slf4j.Slf4j;
import org.androidspringbootbackend.error.ApiError;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /*------------------------------------------------------- 400 ----------------------------------------------------*/

    /**
     * handleHttpMediaTypeNotSupported: triggers when the JSON is malformed
     **/
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        log.info(ex.getClass().getName());
        log.error("", ex);

        final ApiError apiError =
                ApiError.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST)
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("JSON IS MALFORMED")
                        .errors(Collections.singletonList(ex.getLocalizedMessage()))
                        .uri(request.getDescription(false).substring(4))
                        .build();
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /**
     * handleMethodArgumentNotValid : triggers when @Valid fails
     **/
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        log.info(ex.getClass().getName());
        log.error("", ex);

        final List<String> fieldErrors =
                ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                        .collect(Collectors.toList());

        final List<String> globalErrors =
                ex.getBindingResult()
                        .getGlobalErrors()
                        .stream().map(objectError -> objectError.getObjectName() + ": " + objectError.getDefaultMessage())
                        .collect(Collectors.toList());

        final List<String> errors =
                Stream.concat(fieldErrors.stream(), globalErrors.stream()).collect(Collectors.toList());

        final ApiError apiError =
                ApiError.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST)
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("VALIDATION ERRORS")
                        .errors(errors)
                        .uri(request.getDescription(false).substring(4))
                        .build();
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /**
     * handleMissingServletRequestParameter: triggers when a required parameter is missing
     **/
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        log.info(ex.getClass().getName());
        log.error("", ex);

        final String error = "parameter " + ex.getParameterName() + " with type " + ex.getParameterType() + " is missing";

        final ApiError apiError =
                ApiError.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST)
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("MISSING PARAMETERS")
                        .errors(Collections.singletonList(error))
                        .uri(request.getDescription(false).substring(4))
                        .build();
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /**
     * handleTypeMismatch: triggers when a parameter's type does not match
     **/
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        log.info(ex.getClass().getName());
        log.error("", ex);

        final ApiError apiError =
                ApiError.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST)
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("TYPE MISMATCH")
                        .errors(Collections.singletonList(ex.getLocalizedMessage()))
                        .uri(request.getDescription(false).substring(4))
                        .build();
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /**
     * handleConstraintViolation: triggers when constraint violations occurs
     **/
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex, final WebRequest request) {
        log.info(ex.getClass().getName());
        log.error("", ex);

        final List<String> errors =
                ex.getConstraintViolations()
                        .stream()
                        .map(constraintViolation -> constraintViolation.getRootBeanClass().getName() + " " + constraintViolation.getPropertyPath() + ": " + constraintViolation.getMessage())
                        .collect(Collectors.toList());

        final ApiError apiError =
                ApiError.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST)
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("CONSTRAINT VALIDATION")
                        .errors(errors)
                        .uri(request.getDescription(false).substring(4))
                        .build();
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /*------------------------------------------------------- 404 ----------------------------------------------------*/

    /**
     * handleNotFoundException: triggers when no resource was found
     **/
    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<ApiError> handleNotFoundException(final NotFoundException ex, final WebRequest request) {
        log.info(ex.getClass().getName());
        log.error("", ex);

        final ApiError apiError =
                ApiError.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND)
                        .code(HttpStatus.NOT_FOUND.value())
                        .message("MANGAS NOT FOUND")
                        .errors(Collections.singletonList(ex.getLocalizedMessage()))
                        .uri(request.getDescription(false).substring(4))
                        .build();
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /*------------------------------------------------------- 405 ----------------------------------------------------*/

    /**
     * handleNoHandlerFoundException: triggers when the handler method is invalid
     **/
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        log.info(ex.getClass().getName());
        log.error("", ex);

        final String unsupported = ex.getLocalizedMessage();
        final String supported = "Supported methods : " + ex.getSupportedHttpMethods();

        final ApiError apiError =
                ApiError.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.METHOD_NOT_ALLOWED)
                        .code(HttpStatus.METHOD_NOT_ALLOWED.value())
                        .message("METHOD NOT FOUND")
                        .errors(Collections.singletonList(unsupported + " " + supported))
                        .uri(request.getDescription(false).substring(4))
                        .build();
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /*------------------------------------------------------- 415 ----------------------------------------------------*/

    /**
     * handleHttpMediaTypeNotSupported: triggers when the media type is invalid
     **/
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        log.info(ex.getClass().getName());
        log.error("", ex);

        final String unsupported = ex.getLocalizedMessage();
        final String supported = "Supported MediaTypes : " + MediaType.toString(ex.getSupportedMediaTypes());

        final ApiError apiError =
                ApiError.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                        .message("MEDIA TYPE IS NOT SUPPORTED")
                        .errors(Collections.singletonList(unsupported + " " + supported))
                        .uri(request.getDescription(false).substring(4))
                        .build();
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    /*------------------------------------------------------- 500 ----------------------------------------------------*/

    /**
     * handleAnyException: catch all types of logic that deals with all other exceptions without a specific handler
     **/
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAnyException(final Exception ex, final WebRequest request) {
        log.info(ex.getClass().getName());
        log.error("", ex);

        final ApiError apiError =
                ApiError.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("ERROR OCCURRED")
                        .errors(Collections.singletonList(ex.getLocalizedMessage()))
                        .uri(request.getDescription(false).substring(4))
                        .build();
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}

