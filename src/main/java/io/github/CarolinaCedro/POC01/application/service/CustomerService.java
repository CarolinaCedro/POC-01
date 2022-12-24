package io.github.CarolinaCedro.POC01.application.service;

import io.github.CarolinaCedro.POC01.application.dto.request.CustomerSaveRequest;
import io.github.CarolinaCedro.POC01.application.dto.request.CustomerUpdateRequest;
import io.github.CarolinaCedro.POC01.application.dto.response.AddressSaveResponse;
import io.github.CarolinaCedro.POC01.application.dto.response.CustomerMainAddressResponse;
import io.github.CarolinaCedro.POC01.application.dto.response.CustomerSaveResponse;
import io.github.CarolinaCedro.POC01.domain.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Page<CustomerSaveResponse> findAll(Pageable pageable);
    Optional<CustomerSaveResponse> getById(Long id);

    Optional<CustomerMainAddressResponse> findByCustomerMainAddres(Long id);
    CustomerSaveResponse create(CustomerSaveRequest obj);

    CustomerSaveResponse changePrincipalAddress(Long id,CustomerSaveRequest update);
    Optional<AddressSaveResponse>getPrincipalAddress(Long id);
    List<CustomerSaveResponse>findCustomerByEmail(String email); //filter

    CustomerSaveResponse update(Long id, CustomerUpdateRequest request);

    void deleteById(Long id);


}
