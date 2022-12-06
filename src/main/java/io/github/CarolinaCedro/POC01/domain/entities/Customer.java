package io.github.CarolinaCedro.POC01.domain.entities;

import io.github.CarolinaCedro.POC01.domain.enums.PjOrPf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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



    @Column(nullable = false, length = 14)
    @NotNull(message = "{campo.cnfOrCnpj.obrigatorio}")
    @Size(min = 11,max = 14,message = "{campo.cpfOrCnpj.size}")
    private String cpfOrCnpj;


    @Column(nullable = false, length = 2)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{campo.cnfOrCnpj.obrigatorio}")
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




