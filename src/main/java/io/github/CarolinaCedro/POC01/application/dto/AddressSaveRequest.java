package io.github.CarolinaCedro.POC01.application.dto;

import io.github.CarolinaCedro.POC01.domain.entities.Address;
import lombok.Data;

@Data
public class AddressSaveRequest {

    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String zipCode;
    private String state;

    public Address toModel(){
        return new Address(street, number, neighborhood, city,zipCode,state );
    }
}
