package io.github.CarolinaCedro.POC01.application.controller;

import io.github.CarolinaCedro.POC01.application.dto.request.AddressSaveRequest;
import io.github.CarolinaCedro.POC01.application.dto.response.AddressSaveResponse;
import io.github.CarolinaCedro.POC01.application.service.impl.AddressService;
import io.github.CarolinaCedro.POC01.config.modelMapper.ModelMapperConfig;
import io.github.CarolinaCedro.POC01.domain.entities.Address;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {


    private final AddressService addressService;
    private final ModelMapperConfig mapper;


    @GetMapping
    public ResponseEntity<Page<AddressSaveResponse>> getAll(@PageableDefault(page = 0, size = 15, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(addressService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressSaveResponse> getById(@PathVariable Long id) {
        return   ResponseEntity.ok().body(mapper.convert().map(addressService.getById(id), AddressSaveResponse.class));
    }

    @PostMapping
    public ResponseEntity<AddressSaveRequest> createAddress( @RequestBody @Valid AddressSaveRequest request) throws IOException {
        addressService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressSaveResponse>updateAddress(@PathVariable Long id,@Valid @RequestBody AddressSaveRequest request) throws IOException {
        request.setId(id);
        return ResponseEntity.ok().body(mapper.convert().map(addressService.update(id,request),AddressSaveResponse.class));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<AddressSaveResponse> deleteAddress(@PathVariable Long id) {
        addressService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
