package com.bobocode.nasa.controller;

import com.bobocode.nasa.exception.NasaException;
import com.bobocode.nasa.exception.NoPhotoFound;
import com.bobocode.nasa.model.ErrorTO;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler({NoPhotoFound.class})
    public ResponseEntity<ErrorTO> handleNoPictureFoundException(
            NoPhotoFound e) {
        var error = new ErrorTO(e.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler({NasaException.class})
    public ResponseEntity<ErrorTO> handleNasaException(
            NasaException e) {
        var error = new ErrorTO("Call to NASA API failed due to: " + e.getCause());
        return ResponseEntity.internalServerError().body(error);
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ErrorTO> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException e) {
        var error = new ErrorTO(
                String.format("Invalid value for parameter = {%s} : %s", e.getName(), e.getMessage()));
        return ResponseEntity.badRequest().body(error);
    }
}