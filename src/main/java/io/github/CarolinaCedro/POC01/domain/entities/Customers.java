package io.github.CarolinaCedro.POC01.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
public class Customers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    @OneToMany
    private List<Address> address = new ArrayList<>(5);
    private String phone;
    @Enumerated(EnumType.STRING)
    private CpfOrCnpj cpfOrCnpj;
    @Enumerated(EnumType.STRING)
    private PjOrPf pjOrPf;


}
