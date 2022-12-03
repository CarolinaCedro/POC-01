package io.github.CarolinaCedro.POC01.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 150)
    @NotNull(message = "{campo.name.obrigatorio}")
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String zipCode;
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
