package io.github.CarolinaCedro.POC01.application.dto.response;

import io.github.CarolinaCedro.POC01.domain.entities.Address;
import io.github.CarolinaCedro.POC01.domain.enums.PjOrPf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerMainAddressResponse {

    private Long id;
    private String email;
    private Address addressPrincipal;
    private String phone;
    private String cpfOrCnpj;
    private PjOrPf pjOrPf;

}
