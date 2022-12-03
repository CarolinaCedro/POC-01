package io.github.CarolinaCedro.POC01.config.errors;

public class DomainException extends RuntimeException{
    public DomainException(String msg){
        super(msg);
    }
}