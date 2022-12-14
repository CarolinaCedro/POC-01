package io.github.CarolinaCedro.POC01.domain.entities;

import io.github.CarolinaCedro.POC01.domain.CpfOrCnpjInterfaces.CnpjGroup;
import io.github.CarolinaCedro.POC01.domain.CpfOrCnpjInterfaces.CpfGroup;
import io.github.CarolinaCedro.POC01.domain.enums.PjOrPf;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.group.GroupSequenceProvider;



import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@GroupSequenceProvider(CustomerGroupSequenceProvider.class)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    @NotNull(message = "{campo.email.obrigatorio}")
    @Size(min = 5,max = 30,message = "{campo.email.size}")
    private String email;

    @OneToOne
    private Address addressPrincipal;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Column(nullable = false, length = 50)
    @NotNull(message = "{campo.address.obrigatorio}")
    @Size(min = 1,max = 5,message = "{campo.addressList.size}")
    private List<Address> address = new ArrayList<>();


    @Column(nullable = false, length = 30)
    @NotNull(message = "{campo.phone.obrigatorio}")
    @Size(min = 6,max = 30,message = "{campo.phone.size}")
    private String phone;



    @Column(nullable = false)
    @NotNull(message = "{campo.cnfOrCnpj.obrigatorio}")
    @CPF(groups = CpfGroup.class)
    @CNPJ(groups = CnpjGroup.class)
    private String cpfOrCnpj;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PjOrPf pjOrPf;


    public Customer(String email, List<Address> addressList, String phone, String cpfOrCnpj, String pjOrPf, Address addressPrincipal) {
        this.email = email;
        this.address = addressList;
        this.phone = phone;
        this.cpfOrCnpj = cpfOrCnpj;
        this.pjOrPf = PjOrPf.valueOf(pjOrPf);
        this.addressPrincipal = addressPrincipal;
    }


    @Transactional
    public void zera() {
        for (Address response : address
        ) {
            response.setIsPrincipalAddress(false);
        }
    }

}




