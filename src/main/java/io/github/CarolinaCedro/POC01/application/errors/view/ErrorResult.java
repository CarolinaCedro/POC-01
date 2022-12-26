package io.github.CarolinaCedro.POC01.application.errors.view;

public class ErrorResult extends ApiResult {

    public ErrorResult(){}

    public ErrorResult(String code,String errorMessage) {
        super.setCode(code);
        this.errorMessage = errorMessage;
    }

    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

}