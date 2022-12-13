package io.github.CarolinaCedro.POC01.application.service.impl;

import io.github.CarolinaCedro.POC01.application.dto.request.AddressSaveRequest;
import io.github.CarolinaCedro.POC01.application.dto.request.CustomerSaveRequest;
import io.github.CarolinaCedro.POC01.application.dto.response.AddressSaveResponse;
import io.github.CarolinaCedro.POC01.application.dto.response.CustomerSaveResponse;
import io.github.CarolinaCedro.POC01.domain.entities.Address;
import io.github.CarolinaCedro.POC01.domain.entities.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<CustomerSaveResponse> findAll();
    Customer findById(Long id);
    Customer create(CustomerSaveRequest obj);

    Customer changePrincipalAddress(Long id,CustomerSaveRequest update);
    Optional<AddressSaveResponse>getPrincipalAddress(Long id);
    List<CustomerSaveResponse>findCustomarByEmail(String email);

    Customer update(Long id,CustomerSaveRequest request);

    void deleteById(Long id);
}
