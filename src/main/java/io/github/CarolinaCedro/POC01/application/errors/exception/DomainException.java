package io.github.CarolinaCedro.POC01.application.errors.exception;

public class DomainException extends RuntimeException{
    public DomainException(String msg){
        super(msg);
    }
}