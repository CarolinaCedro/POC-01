package io.github.CarolinaCedro.POC01.application.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressSaveRequest {


    private Long id;

    @NotEmpty(message = "{campo.logradouro.obrigatorio}")
    @Size(min = 6, max = 50, message = "{campo.logradouro.size}")
    private String logradouro;
    @NotEmpty(message = "{campo.number.obrigatorio}")
    @Size(min = 1, max = 10, message = "{campo.number.size}")
    private String number;
    @NotEmpty(message = "{campo.bairro.obrigatorio}")
    @Size(min = 4, max = 50, message = "{campo.bairro.size}")
    private String bairro;


    private String localidade;


    @NotEmpty(message = "{campo.cep.obrigatorio}")
    @Size(min = 8, max = 8, message = "{campo.cep.size}")
    private String cep;


    private String uf;

    @NotNull(message = "{campo.isPrincipalAddress.obrigatorio}")
    private Boolean isPrincipalAddress;

    public AddressSaveRequest(String logradouro, String number, String bairro, String localidade, String cep, String uf, Boolean isPrincipalAddress) {
        this.logradouro = logradouro;
        this.number = number;
        this.bairro = bairro;
        this.localidade = localidade;
        this.cep = cep;
        this.uf = uf;
        this.isPrincipalAddress = isPrincipalAddress;
    }

    public AddressSaveRequest(Long id) {
        this.id = id;
    }



}
