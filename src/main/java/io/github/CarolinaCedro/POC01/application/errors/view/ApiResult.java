package io.github.CarolinaCedro.POC01.application.errors.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ApiResult {

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static ApiResult success(Object data){
        return new SuccessResult(data);
    }

    public static ApiResult error(String code, String errorMessage){
        return new ErrorResult(code,errorMessage);
    }
}