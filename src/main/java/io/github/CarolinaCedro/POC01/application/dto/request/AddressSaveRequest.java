package io.github.CarolinaCedro.POC01.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressSaveRequest {

    @NotEmpty(message = "{campo.street.obrigatorio}")
    private String street;
    @NotEmpty(message = "{campo.number.obrigatorio}")
    private String number;
    @NotEmpty(message = "{campo.neighborhood.obrigatorio}")
    private String neighborhood;
    @NotEmpty(message = "{campo.city.obrigatorio}")
    private String city;
    @NotEmpty(message = "{campo.zipCode.obrigatorio}")
    private String zipCode;
    @NotEmpty(message = "{campo.state.obrigatorio}")
    private String state;

    @NotEmpty(message = "{campo.isPrincipalAddress.obrigatorio}")
    private boolean isPrincipalAddress;

}
