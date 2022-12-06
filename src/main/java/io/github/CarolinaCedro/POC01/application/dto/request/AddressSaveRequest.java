package io.github.CarolinaCedro.POC01.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressSaveRequest {

    @NotEmpty(message = "{campo.street.obrigatorio}")
    @Size(min = 6, max = 50, message = "{campo.street.size}")
    private String street;
    @NotEmpty(message = "{campo.number.obrigatorio}")
    @Size(min = 1, max = 10, message = "{campo.number.size}")
    private String number;
    @NotEmpty(message = "{campo.neighborhood.obrigatorio}")
    @Size(min = 4, max = 50, message = "{campo.neighborhood.size}")
    private String neighborhood;
    @NotEmpty(message = "{campo.city.obrigatorio}")
    @Size(min = 4, max = 30, message = "{campo.city.size}")
    private String city;
    @NotEmpty(message = "{campo.zipCode.obrigatorio}")
    @Size(min = 8, max = 30, message = "{campo.zipCode.size}")
    private String zipCode;
    @NotEmpty(message = "{campo.state.obrigatorio}")
    @Size(min = 3, max = 30, message = "{campo.state.size}")
    private String state;

    @NotNull(message = "{campo.isPrincipalAddress.obrigatorio}")
    private Boolean isPrincipalAddress;

}
