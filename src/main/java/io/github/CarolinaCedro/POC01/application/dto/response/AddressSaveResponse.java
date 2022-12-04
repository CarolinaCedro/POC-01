package io.github.CarolinaCedro.POC01.application.dto.response;

import io.github.CarolinaCedro.POC01.domain.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressSaveResponse {

    private Long id;
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String zipCode;
    private String state;
    private boolean isPrincipalAddress;

}
