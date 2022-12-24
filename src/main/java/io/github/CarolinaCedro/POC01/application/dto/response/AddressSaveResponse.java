package io.github.CarolinaCedro.POC01.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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

    public AddressSaveResponse(Long id, String logradouro, String number, String bairro, String localidade, String cep, String uf, boolean isPrincipalAddress) {
        this.id = id;
        this.logradouro = logradouro;
        this.number = number;
        this.bairro = bairro;
        this.localidade = localidade;
        this.cep = cep;
        this.uf = uf;
        this.isPrincipalAddress = isPrincipalAddress;
    }
}
