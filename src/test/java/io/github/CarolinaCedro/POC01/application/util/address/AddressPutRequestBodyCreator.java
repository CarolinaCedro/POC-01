package io.github.CarolinaCedro.POC01.application.util.address;

import io.github.CarolinaCedro.POC01.application.dto.request.AddressSaveRequest;


public class AddressPutRequestBodyCreator {
    public static AddressSaveRequest createAddPutRequestBody(){
        return AddressSaveRequest.builder()
                .id(AddressCreator.createValidUpdatedAddress().getId())
                .logradouro(AddressCreator.createValidUpdatedAddress().getLogradouro())
                .number(AddressCreator.createValidUpdatedAddress().getNumber())
                .bairro(AddressCreator.createValidUpdatedAddress().getBairro())
                .localidade(AddressCreator.createValidUpdatedAddress().getLocalidade())
                .cep(AddressCreator.createValidUpdatedAddress().getCep())
                .uf(AddressCreator.createValidUpdatedAddress().getUf())
                .isPrincipalAddress(AddressCreator.createValidUpdatedAddress().getIsPrincipalAddress())
                .build();
    }
}
