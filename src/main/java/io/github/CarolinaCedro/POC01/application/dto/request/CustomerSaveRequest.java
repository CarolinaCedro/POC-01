package io.github.CarolinaCedro.POC01.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSaveRequest {

    @NotEmpty(message = "{campo.email.obrigatorio}")
    @Email
    private String email;

    @NotEmpty(message = "{campo.address.obrigatorio}")
    private List<Long> address = new ArrayList<>();

    @NotEmpty(message = "{campo.phone.obrigatorio}")
    private String phone;

    @NotEmpty(message = "{campo.cpfOrCnpj.obrigatorio}")
    private String cpfOrCnpj;

    @NotEmpty(message = "{campo.pjOrPf.obrigatorio}")
    private String pjOrPf;

}
