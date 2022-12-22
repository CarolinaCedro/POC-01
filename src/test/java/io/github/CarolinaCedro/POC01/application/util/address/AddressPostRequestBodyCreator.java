package io.github.CarolinaCedro.POC01.application.util.address;

import io.github.CarolinaCedro.POC01.application.dto.request.AddressSaveRequest;


public class AddressPostRequestBodyCreator {
    public static AddressSaveRequest createAddressPostRequestBody(){
        return AddressSaveRequest.builder()
                .id(AddressCreator.createAddressToBeSaved().getId())
                .logradouro(AddressCreator.createAddressToBeSaved().getLogradouro())
                .number(AddressCreator.createAddressToBeSaved().getNumber())
                .bairro(AddressCreator.createAddressToBeSaved().getBairro())
                .localidade(AddressCreator.createAddressToBeSaved().getLocalidade())
                .cep(AddressCreator.createAddressToBeSaved().getCep())
                .uf(AddressCreator.createAddressToBeSaved().getUf())
                .isPrincipalAddress(AddressCreator.createAddressToBeSaved().getIsPrincipalAddress())
                .build();
    }
}
