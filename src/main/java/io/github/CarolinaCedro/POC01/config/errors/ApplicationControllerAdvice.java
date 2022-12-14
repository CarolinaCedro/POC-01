package io.github.CarolinaCedro.POC01.config.errors;


import io.github.CarolinaCedro.POC01.application.exception.ApiErrors;
import io.github.CarolinaCedro.POC01.application.exception.ObjectNotFoundException;
import io.github.CarolinaCedro.POC01.application.exception.StandardError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApplicationControllerAdvice {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationErros(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> messages = bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return new ApiErrors(messages);
    }

//    @ExceptionHandler(ResponseStatusException.class)
//    public ResponseEntity handleResponseStatusException(ResponseStatusException ex) {
//        String mensagemErro = ex.getReason();
//        HttpStatus codigoStatus = ex.getStatus();
//        ApiErrors apiErrors = new ApiErrors(mensagemErro);
//        return new ResponseEntity(apiErrors, codigoStatus);
//    }


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