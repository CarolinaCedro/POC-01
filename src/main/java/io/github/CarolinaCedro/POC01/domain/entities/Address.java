package io.github.CarolinaCedro.POC01.domain.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    @NotNull(message = "{campo.street.obrigatorio}")
    @Size(min = 6, max = 50, message = "{campo.street.size}")
    private String street;

    @Column(nullable = false, length = 30)
    @NotNull(message = "{campo.number.obrigatorio}")
    @Size(min = 1, max = 10, message = "{campo.number.size}")
    private String number;
    @Column(nullable = false, length = 50)
    @NotNull(message = "{campo.neighborhood.obrigatorio}")
    @Size(min = 4, max = 50, message = "{campo.neighborhood.size}")
    private String neighborhood;
    @Column(nullable = false, length = 50)
    @NotNull(message = "{campo.city.obrigatorio}")
    @Size(min = 4, max = 30, message = "{campo.city.size}")
    private String city;
    @Column(nullable = false, length = 8)
    @NotNull(message = "{campo.zipCode.obrigatorio}")
    @Size(min = 8, max = 30, message = "{campo.zipCode.size}")
    private String zipCode;
    @Column(nullable = false, length = 30)
    @NotNull(message = "{campo.state.obrigatorio}")
    @Size(min = 3, max = 30, message = "{campo.state.size}")
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
