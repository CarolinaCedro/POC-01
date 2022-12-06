package io.github.CarolinaCedro.POC01.application.dto.response;

import io.github.CarolinaCedro.POC01.domain.entities.Address;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CustomerSaveResponse {

    private Long id;
    private String email;
    private Address addressPrincipal;
    private List<AddressConversorResponse> address = new ArrayList<>();
    private String phone;
    private String cpfOrCnpj;
    private String pjOrPf;

}
