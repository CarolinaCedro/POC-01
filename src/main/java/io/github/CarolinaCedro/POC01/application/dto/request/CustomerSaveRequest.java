package io.github.CarolinaCedro.POC01.application.dto.request;

import io.github.CarolinaCedro.POC01.domain.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSaveRequest {

    @NotEmpty(message = "{campo.email.obrigatorio}")
    @Email
    private String email;
    @NotEmpty(message = "{campo.number.obrigatorio}")

    private List<Long>address = new ArrayList<>();
    @NotEmpty(message = "{campo.neighborhood.obrigatorio}")

    private String phone;
    @NotEmpty(message = "{campo.city.obrigatorio}")
    private String cpfOrCnpj;
    @NotEmpty(message = "{campo.state.obrigatorio}")
    private String pjOrPf;

}
