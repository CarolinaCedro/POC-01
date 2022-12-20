package io.github.CarolinaCedro.POC01.application.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.CarolinaCedro.POC01.domain.entities.Address;
import io.github.CarolinaCedro.POC01.infra.repository.AddressRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressConversorResponse {

    @Autowired
    @JsonIgnore
    private AddressRepository addressRepository;

    private Long id;
    private String logradouro;
    private String number;
    private String bairro;
    private String localidade;
    private String cep;
    private String uf;
    private boolean isPrincipalAddress;


    public AddressConversorResponse(List<Long> address) {

        List<Long> listOfIds = new ArrayList<>();

        for (Long response : address
        ) {
            listOfIds.add(response);
        }

        List<Address> addressList = addressRepository.findAllById(listOfIds);
        for (Address res : addressList
        ) {
            this.id = res.getId();
            this.logradouro = res.getLogradouro();
            this.number = res.getNumber();
            this.bairro = res.getBairro();
            this.localidade = res.getLocalidade();
            this.cep = res.getCep();
            this.uf = res.getUf();
            this.isPrincipalAddress = res.getIsPrincipalAddress();
        }

    }

    public AddressConversorResponse(Long id, String logradouro, String number, String bairro, String localidade, String cep, String uf, boolean isPrincipalAddress) {
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
