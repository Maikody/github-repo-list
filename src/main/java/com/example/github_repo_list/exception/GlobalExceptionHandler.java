package com.example.github_repo_list.exception;

import com.example.github_repo_list.model.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
class GlobalExceptionHandler {

    @ExceptionHandler(GithubClientException.class)
    public ResponseEntity<ErrorResponse> handleHttpClientErrorException(GithubClientException e) {
        HttpStatusCode status = e.getStatusCode();
        return new ResponseEntity<>(new ErrorResponse(status.value(), e.getStatusText()), status);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
        log.error("Unexpected error occurred: {}", e.getMessage());
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
