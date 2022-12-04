package io.github.CarolinaCedro.POC01.application.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CustomerSaveResponse {

    private Long id;
    private String email;
    private List<Long> address = new ArrayList<>();
    private String phone;
    private String cpfOrCnpj;
    private String pjOrPf;


}
