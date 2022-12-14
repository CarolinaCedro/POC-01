package io.github.CarolinaCedro.POC01.application.dto.response;

import io.github.CarolinaCedro.POC01.domain.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressSaveResponse {

    private Long id;
    private String logradouro;
    private String number;
    private String bairro;
    private String localidade;
    private String cep;
    private String uf;
    private boolean isPrincipalAddress;

}
