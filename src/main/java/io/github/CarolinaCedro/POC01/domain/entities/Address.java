package io.github.CarolinaCedro.POC01.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
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
    @NotNull(message = "{campo.name.obrigatorio}")
    private String number;
    @Column(nullable = false, length = 50)
    @NotNull(message = "{campo.name.obrigatorio}")
    private String neighborhood;
    @Column(nullable = false, length = 50)
    @NotNull(message = "{campo.name.obrigatorio}")
    private String city;
    @Column(nullable = false, length = 8)
    @NotNull(message = "{campo.name.obrigatorio}")
    private String zipCode;
    @Column(nullable = false, length = 30)
    @NotNull(message = "{campo.name.obrigatorio}")
    private String state;


    public Address(String street, String number, String neighborhood, String city, String zipCode, String state) {
        this.street = street;
        this.number = number;
        this.neighborhood = neighborhood;
        this.city = city;
        this.zipCode = zipCode;
        this.state = state;
    }
}
