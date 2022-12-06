package io.github.CarolinaCedro.POC01.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSaveRequest {

    @NotEmpty(message = "{campo.email.obrigatorio}")
    @Email
    @Size(min = 5,max = 30,message = "{campo.email.size}")
    private String email;

    @NotEmpty(message = "{campo.address.obrigatorio}")
    @Size(min = 1,max = 5,message = "{campo.addressList.size}")
    private List<Long> address = new ArrayList<>();

    @NotEmpty(message = "{campo.phone.obrigatorio}")
    @Size(min = 6,max = 30,message = "{campo.phone.size}")
    private String phone;

    @NotEmpty(message = "{campo.cpfOrCnpj.obrigatorio}")
    @Size(min = 11,max = 14,message = "{campo.cpfOrCnpj.size}")
    private String cpfOrCnpj;

    @NotEmpty(message = "{campo.pjOrPf.obrigatorio}")
    private String pjOrPf;

}
