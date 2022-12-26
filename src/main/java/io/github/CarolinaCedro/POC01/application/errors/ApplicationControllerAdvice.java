package io.github.CarolinaCedro.POC01.application.errors;


import io.github.CarolinaCedro.POC01.application.errors.exception.*;
import io.github.CarolinaCedro.POC01.application.errors.view.ApiErrors;
import io.github.CarolinaCedro.POC01.application.errors.view.ApiResult;
import io.github.CarolinaCedro.POC01.application.errors.view.StandardError;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;



import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @Resource
    ExceptionManager exceptionManager;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationErrorMethodArgumentNotValidadeException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> messages = bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return new ApiErrors(messages);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResult constraintViolationException(ConstraintViolationException e) {
        String code = "";
        Iterator<ConstraintViolation<?>> iterator = e.getConstraintViolations().iterator();
        if (iterator.hasNext()) {
            code = (iterator.next()).getMessage();
        }
        CustomException exception = exceptionManager.create(code);
        return ApiResult
                .error(exception.getCode(), exception.getMessage());
    }



    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleResponseStatusException(ResponseStatusException ex){
        String mensagemError = ex.getMessage();
        return new ApiErrors(mensagemError);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleDataIntegratyViolation(DataIntegrityViolationException ex){
        String mensagemError = "Error: Database duplicate data error";
        return new ApiErrors(mensagemError);
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleInvalidDataAcess(InvalidDataAccessApiUsageException ex){
        String mensagemError = "Atenção:endereço principal deve ser definido para concluir cadastro.";
        return new ApiErrors(mensagemError);
    }

    @ExceptionHandler(FullmailingListException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleFullmainList(FullmailingListException ex){
        String mensagemError = ex.getMessage();
        return new ApiErrors(mensagemError);
    }


    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError>objectNotFound(ObjectNotFoundException ex, HttpServletRequest request) {
        StandardError error =
                new StandardError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }



}