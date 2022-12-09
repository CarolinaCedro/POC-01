package io.github.CarolinaCedro.POC01.application.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ApiErrors {

    @Getter
    @Setter
    private List<String> errors;

    public ApiErrors(List<String> errors){
        this.errors = errors;
    }

    public ApiErrors(String message){
        this.errors = Collections.singletonList(message);
    }

}