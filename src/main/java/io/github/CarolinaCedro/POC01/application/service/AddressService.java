package io.github.CarolinaCedro.POC01.application.service;

import io.github.CarolinaCedro.POC01.application.dto.request.AddressSaveRequest;
import io.github.CarolinaCedro.POC01.application.dto.response.AddressSaveResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface AddressService {
    Page<AddressSaveResponse> getAll(Pageable pageable);

    Optional<AddressSaveResponse> getById(Long id);

    AddressSaveResponse save(AddressSaveRequest obj) throws IOException;

    void deleteById(Long id);

    AddressSaveResponse update(Long id, AddressSaveRequest request) throws IOException;
}
