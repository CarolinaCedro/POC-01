package io.github.CarolinaCedro.POC01.application.errors.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class CustomException extends RuntimeException {

    public CustomException(String code, String msg) {
        super(code);
        this.code = code;
        this.msg = msg;
    }


    private String code;
    private String msg;

}