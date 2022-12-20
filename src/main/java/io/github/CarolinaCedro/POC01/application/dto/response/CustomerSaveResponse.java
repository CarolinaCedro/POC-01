package io.github.CarolinaCedro.POC01.application.dto.response;

import io.github.CarolinaCedro.POC01.domain.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSaveResponse {

    private Long id;
    private String email;
    private Address addressPrincipal;
    private List<AddressConversorResponse> address = new ArrayList<>();
    private String phone;
    private String cpfOrCnpj;
    private String pjOrPf;

    public CustomerSaveResponse(Long id, String email, List<AddressConversorResponse> address, String phone, String cpfOrCnpj, String pjOrPf) {
        this.id = id;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.cpfOrCnpj = cpfOrCnpj;
        this.pjOrPf = pjOrPf;
    }

}
