package io.github.CarolinaCedro.POC01.config.test;


import io.github.CarolinaCedro.POC01.domain.entities.Address;
import io.github.CarolinaCedro.POC01.infra.repository.AddressRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("test")
@RequiredArgsConstructor
public class LocalConfig {

    @Autowired
    private  AddressRepository addressRepository;

    @Bean
    public void StartDb(){

        Address address1 = new Address("Rua da food","45","brasil","Santa helena","76895000","Goiás",true);
        Address address2 = new Address("Rua da food","45","brasil","Santa helena","76895000","Goiás",true);
        Address address3 = new Address("Rua da food","45","brasil","Santa helena","76895000","Goiás",true);

        addressRepository.saveAll(List.of(address1,address2,address3));
    }

}
