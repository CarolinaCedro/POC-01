package io.github.CarolinaCedro.POC01.domain.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    @NotNull(message = "{campo.street.obrigatorio}")
    private String street;

    @Column(nullable = false, length = 30)
    @NotNull(message = "{campo.number.obrigatorio}")
    private String number;
    @Column(nullable = false, length = 50)
    @NotNull(message = "{campo.neighborhood.obrigatorio}")
    private String neighborhood;
    @Column(nullable = false, length = 50)
    @NotNull(message = "{campo.city.obrigatorio}")
    private String city;
    @Column(nullable = false, length = 8)
    @NotNull(message = "{campo.zipCode.obrigatorio}")
    private String zipCode;
    @Column(nullable = false, length = 30)
    @NotNull(message = "{campo.state.obrigatorio}")
    private String state;

    @NotNull(message = "{campo.isPrincipalAddress.obrigatorio}")
    private Boolean isPrincipalAddress;


    public Address(String street, String number, String neighborhood, String city, String zipCode, String state, Boolean isPrincipalAddress) {
        this.street = street;
        this.number = number;
        this.neighborhood = neighborhood;
        this.city = city;
        this.zipCode = zipCode;
        this.state = state;
        this.isPrincipalAddress = isPrincipalAddress;
    }


}
