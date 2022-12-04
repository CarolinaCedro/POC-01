package io.github.CarolinaCedro.POC01.domain.entities;

import io.github.CarolinaCedro.POC01.domain.enums.CpfOrCnpj;
import io.github.CarolinaCedro.POC01.domain.enums.PjOrPf;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
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
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    @NotNull(message = "{campo.email.obrigatorio}")
    private String email;

    @OneToOne
    private Address addressPrincipal;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Column(nullable = false, length = 50)
    @NotNull(message = "{campo.address.obrigatorio}")
    private List<Address> address = new ArrayList<>();


    @Column(nullable = false, length = 50)
    @NotNull(message = "{campo.phone.obrigatorio}")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    @NotNull(message = "{campo.cnfOrCnpj.obrigatorio}")
    private CpfOrCnpj cpfOrCnpj;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{campo.cnfOrCnpj.obrigatorio}")
    private PjOrPf pjOrPf;


    public Customer(String email, List<Address> addressList, String phone, String cpfOrCnpj, String pjOrPf, Address addressPrincipal) {
        this.email = email;
        this.address = addressList;
        this.phone = phone;
        this.cpfOrCnpj = CpfOrCnpj.valueOf(cpfOrCnpj);
        this.pjOrPf = PjOrPf.valueOf(pjOrPf);
        this.addressPrincipal = addressPrincipal;
    }


    @Transactional
    public void zera() {
        for (Address response : address
        ) {
            response.setPrincipalAddress(false);
        }
    }

}


