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
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String zipCode;
    private String state;
    private boolean isPrincipalAddress;


    public AddressConversorResponse(List<Long> address) {

        List<Long> listDeIds = new ArrayList<>();

        for (Long response : address
        ) {
            Long id = response;
            listDeIds.add(id);
        }

        List<Address> addressList = addressRepository.findAllById(listDeIds);
        for (Address res : addressList
        ) {
            this.id = res.getId();
            this.street = res.getStreet();
            this.number = res.getNumber();
            this.neighborhood = res.getNeighborhood();
            this.city = res.getCity();
            this.zipCode = res.getZipCode();
            this.state = res.getState();
            this.isPrincipalAddress = res.getIsPrincipalAddress();
        }

    }
}
