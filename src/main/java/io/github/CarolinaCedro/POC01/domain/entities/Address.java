package io.github.CarolinaCedro.POC01.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
//    private Address address;
    private String phone;
    @Enumerated(EnumType.STRING)
    private CpfOrCnpj cpfOrCnpj;
    @Enumerated(EnumType.STRING)
    private PjOrPf pjOrPf;


}
