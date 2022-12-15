package io.github.CarolinaCedro.POC01.application.service.impl;

import io.github.CarolinaCedro.POC01.application.dto.request.CustomerSaveRequest;
import io.github.CarolinaCedro.POC01.application.dto.response.AddressSaveResponse;
import io.github.CarolinaCedro.POC01.application.dto.response.CustomerMainAddressResponse;
import io.github.CarolinaCedro.POC01.application.dto.response.CustomerSaveResponse;
import io.github.CarolinaCedro.POC01.domain.entities.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<CustomerSaveResponse> findAll();
    Optional<CustomerSaveResponse> getById(Long id);

    Optional<CustomerMainAddressResponse> findByCustomerMainAddres(Long id);
    CustomerSaveResponse create(CustomerSaveRequest obj);

    CustomerSaveResponse changePrincipalAddress(Long id,CustomerSaveRequest update);
    Optional<AddressSaveResponse>getPrincipalAddress(Long id);
    List<CustomerSaveResponse>findCustomarByEmail(String email);

    CustomerSaveResponse update(Long id,CustomerSaveRequest request);

    void deleteById(Long id);


}
