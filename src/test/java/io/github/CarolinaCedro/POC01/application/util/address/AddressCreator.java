package io.github.CarolinaCedro.POC01.application.util.address;

import io.github.CarolinaCedro.POC01.domain.entities.Address;

public class AddressCreator {
    public static Address createAddressToBeSaved(){
        return Address.builder()
                .id(1L)
                .version(1)
                .logradouro("Rua dos testes")
                .number("45")
                .bairro("bairro brasil")
                .localidade("Santa helena de goias")
                .cep("75920000")
                .uf("GO")
                .isPrincipalAddress(true)
                .build();
    }

    public static Address createValidAddressUpdatePrincipal(){
        return Address.builder()
                .version(1)
                .logradouro("Rua dos testes")
                .number("45")
                .bairro("bairro brasil")
                .localidade("Santa helena de goias")
                .cep("75920000")
                .uf("GO")
                .isPrincipalAddress(false)
                .build();
    }

    public static Address createValidUpdatedAddress(){
        return Address.builder()
                .id(2L)
                .version(1)
                .logradouro("Rua dos testes")
                .number("45")
                .bairro("bairro brasil")
                .localidade("Santa helena de goias")
                .cep("75920000")
                .uf("GO")
                .isPrincipalAddress(true)
                .build();
    }


}
