package io.github.CarolinaCedro.POC01.application.errors.view;

public class SuccessResult extends ApiResult {

    public SuccessResult(){}

    public SuccessResult(Object data) {
        super.setCode("success");
        this.data = data;
    }

    private Object data;

    public Object getData() {
        return data;
    }

}