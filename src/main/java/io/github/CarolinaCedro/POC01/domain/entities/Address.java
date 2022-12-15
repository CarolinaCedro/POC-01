package io.github.CarolinaCedro.POC01.domain.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @Version   //Lock otimista
    private Integer version;
    @Column(nullable = false, length = 50)
    @NotNull(message = "{campo.logradouro.obrigatorio}")
    @Size(min = 6, max = 50, message = "{campo.logradouro.size}")
    private String logradouro;

    @Column(nullable = false, length = 30)
    @NotNull(message = "{campo.number.obrigatorio}")
    @Size(min = 1, max = 10, message = "{campo.number.size}")
    private String number;
    @Column(nullable = false, length = 50)
    @NotNull(message = "{campo.bairro.obrigatorio}")
    @Size(min = 4, max = 50, message = "{campo.bairro.size}")
    private String bairro;
    @Column(nullable = false, length = 50)
    @Size(min = 4, max = 30, message = "{campo.localidade.size}")
    private String localidade;
    @Column(nullable = false, length = 8)
    @NotNull(message = "{campo.cep.obrigatorio}")
    @Size(min = 8, max = 8, message = "{campo.cep.size}")
    private String cep;
    @Column(nullable = false, length = 30)
    @Size(min = 2, max = 2, message = "{campo.uf.size}")
    private String uf;

    @NotNull(message = "{campo.isPrincipalAddress.obrigatorio}")
    private Boolean isPrincipalAddress;


    public Address(String logradouro, String number, String bairro, String localidade, String cep, String uf, Boolean isPrincipalAddress) {
        this.logradouro = logradouro;
        this.number = number;
        this.bairro = bairro;
        this.localidade = localidade;
        this.cep = cep;
        this.uf = uf;
        this.isPrincipalAddress = isPrincipalAddress;
    }


}
