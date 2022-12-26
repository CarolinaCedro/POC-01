package io.github.CarolinaCedro.POC01.application.errors.exception;


public class ObjectNotFoundException extends RuntimeException{

    public ObjectNotFoundException(String msg) {
        super(msg);
    }
}
