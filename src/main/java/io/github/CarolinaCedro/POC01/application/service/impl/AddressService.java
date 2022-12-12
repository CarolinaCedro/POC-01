package io.github.CarolinaCedro.POC01.application.service.impl;

import io.github.CarolinaCedro.POC01.application.dto.request.AddressSaveRequest;
import io.github.CarolinaCedro.POC01.application.dto.response.AddressSaveResponse;
import io.github.CarolinaCedro.POC01.domain.entities.Address;

import java.util.List;

public interface AddressService {
    List<Address> getAll();
    Address findById(Long id);
    Address save(AddressSaveRequest obj);
    void deleteById(Long id);
    Address update(AddressSaveRequest request);
}
