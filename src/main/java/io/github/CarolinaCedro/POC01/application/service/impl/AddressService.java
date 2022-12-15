package io.github.CarolinaCedro.POC01.application.service.impl;

import io.github.CarolinaCedro.POC01.application.dto.request.AddressSaveRequest;
import io.github.CarolinaCedro.POC01.application.dto.response.AddressSaveResponse;
import io.github.CarolinaCedro.POC01.domain.entities.Address;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface AddressService {
    List<AddressSaveResponse> getAll();

    Address findById(Long id);

    AddressSaveResponse save(AddressSaveRequest obj) throws IOException;

    void deleteById(Long id);

    AddressSaveResponse update(Long id, AddressSaveRequest request) throws IOException;
}
