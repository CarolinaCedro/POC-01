package io.github.CarolinaCedro.POC01.application.errors;

import io.github.CarolinaCedro.POC01.application.errors.exception.CustomException;
import jakarta.annotation.Resource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ExceptionManager {

    @Resource
    Environment environment;

    public CustomException create(String code) {
        return new CustomException(code, environment.getProperty(code));
    }
}