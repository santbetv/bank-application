package com.devsu.hackerearth.backend.client.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    private static final String ERROR_VALIDATION = "Error al ingresar datos.";
    private static final String ERROR_IN_TEMPLATE = "Error en busqueda del documento.";

    @ExceptionHandler(BussinesRuleException.class)
    public ResponseEntity<ApiExceptionResponse> handleBussinesRuleException(BussinesRuleException ex) {
        ApiExceptionResponse response = new ApiExceptionResponse
            (ex.getClass().getSimpleName()
                , ex.getHttpStatus().getReasonPhrase()
                , ex.getHttpStatus().toString(),
                ex.getLocalizedMessage());
        return new ResponseEntity(response, ex.getHttpStatus());
    }

    //
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiExceptionResponse> handleGenericException(Exception ex) {
        log.error("Ha ocurrido un error inesperado: {}", ex.getMessage(), ex);


        ApiExceptionResponse response = new ApiExceptionResponse
            (ex.getClass().getSimpleName()
                , "Error Interno del Servidor"
                , ERROR_VALIDATION,
                ex.getMessage());
        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ApiExceptionResponse> handleValidationException(Exception ex) {
        log.error("Ha ocurrido un error de Validación: {}", ex.getMessage(), ex);

        String detalle = extractErrorMessages(ex);

        ApiExceptionResponse response = new ApiExceptionResponse(
            ex.getClass().getSimpleName(),
            ERROR_VALIDATION,
            "002",
            detalle
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    private String extractErrorMessages(Exception ex) {
        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException manvEx = (MethodArgumentNotValidException) ex;
            return manvEx.getBindingResult().getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining("; "));
        } else if (ex instanceof BindException) {
            BindException bindEx = (BindException) ex;
            return bindEx.getBindingResult().getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining("; "));
        } else if (ex instanceof HttpMessageNotReadableException) {
            return "El cuerpo de la solicitud no es legible.";
        }

        return "Datos inválidos proporcionados.";
    }


}
