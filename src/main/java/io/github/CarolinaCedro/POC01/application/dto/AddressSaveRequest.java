package io.github.CarolinaCedro.POC01.application.dto;

import io.github.CarolinaCedro.POC01.domain.entities.Address;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
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

    public Address toModel(){
        return new Address(street, number, neighborhood, city,zipCode,state );
    }
}
