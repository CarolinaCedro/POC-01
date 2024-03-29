package io.github.CarolinaCedro.POC01.application.dto.request;

import io.github.CarolinaCedro.POC01.domain.CpfOrCnpjInterfaces.CnpjGroup;
import io.github.CarolinaCedro.POC01.domain.CpfOrCnpjInterfaces.CpfGroup;
import io.github.CarolinaCedro.POC01.domain.entities.Address;
import io.github.CarolinaCedro.POC01.domain.enums.PjOrPf;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUpdateRequest {

    private Long id;

    @NotEmpty(message = "{campo.email.obrigatorio}")
    @Email
    @Size(min = 5, max = 30, message = "{campo.email.size}")
    private String email;

    @NotEmpty(message = "{campo.address.obrigatorio}")
    @Size(min = 1, max = 5, message = "{campo.addressList.size}")
    private List<Long> address = new ArrayList<>();

    @NotEmpty(message = "{campo.phone.obrigatorio}")
    @Size(min = 6, max = 30, message = "{campo.phone.size}")
    private String phone;


    @NotEmpty(message = "{campo.cpfOrCnpj.obrigatorio}")
    @Size(min = 11, max = 18, message = "{campo.cpfOrCnpj.size}")
    @CPF(groups = CpfGroup.class)
    @CNPJ(groups = CnpjGroup.class)
    private String cpfOrCnpj;

    private String pjOrPf;

    public void setFalse(List<Address> list,Address principal) {

        Long idPrincipal = principal.getId();

        for (Address response : list
        ) {
            if (!Objects.equals(response.getId(), idPrincipal)){
                response.setIsPrincipalAddress(false);
            }
        }
    }


}
