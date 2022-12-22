package io.github.CarolinaCedro.POC01.application.controller;

import io.github.CarolinaCedro.POC01.application.dto.request.CustomerSaveRequest;
import io.github.CarolinaCedro.POC01.application.dto.request.CustomerUpdateRequest;
import io.github.CarolinaCedro.POC01.application.dto.response.AddressSaveResponse;
import io.github.CarolinaCedro.POC01.application.dto.response.CustomerMainAddressResponse;
import io.github.CarolinaCedro.POC01.application.dto.response.CustomerSaveResponse;
import io.github.CarolinaCedro.POC01.application.service.CustomerServiceImpl;
import io.github.CarolinaCedro.POC01.config.modelMapper.ModelMapperConfig;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/customer")
@CrossOrigin("http://localhost:4200")
@RequiredArgsConstructor
@Builder
public class CustomerController {

    private final CustomerServiceImpl customerServiceImpl;
    private final ModelMapperConfig mapper;



    @GetMapping
    public ResponseEntity<Page<CustomerSaveResponse>> getAll(@PageableDefault(page = 0, size = 15, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerServiceImpl.findAll(pageable));
    }

    @GetMapping("v1/customer/{id}")
    public ResponseEntity<CustomerSaveResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(mapper.convert().map(customerServiceImpl.getById(id), CustomerSaveResponse.class));
    }

    @GetMapping("v2/customer/{id}")
    public ResponseEntity<CustomerMainAddressResponse> getCustomerWithMainAddress(@PathVariable Long id) {
        return ResponseEntity.ok().body(mapper.convert().map(customerServiceImpl.findByCustomerMainAddres(id), CustomerMainAddressResponse.class));
    }


    @GetMapping("/filter")
    public ResponseEntity<CustomerSaveResponse> getByEmail(@RequestParam(value = "email", required = false, defaultValue = "") String email) {
        return ResponseEntity.ok().body(mapper.convert().map(customerServiceImpl.findCustomerByEmail(email), CustomerSaveResponse.class));

    }


    @GetMapping("/address/principal/{id}")
    public ResponseEntity<AddressSaveResponse> getPrincipalAddress(@PathVariable Long id) {
        return ResponseEntity.ok().body(mapper.convert().map(customerServiceImpl.getPrincipalAddress(id), AddressSaveResponse.class));

    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerSaveResponse> updateAddress(@PathVariable Long id, @RequestBody CustomerUpdateRequest request) {
        request.setId(id);
        return ResponseEntity.ok().body(mapper.convert().map(customerServiceImpl.update(id, request), CustomerSaveResponse.class));
    }


    @PatchMapping("/updateAddressPrincipal/{id}")
    public ResponseEntity<CustomerSaveResponse> setAddressPrincipal(@PathVariable Long id, @RequestBody CustomerSaveRequest update) {
        update.setId(id);
        return ResponseEntity.ok().body(mapper.convert().map(customerServiceImpl.changePrincipalAddress(id, update), CustomerSaveResponse.class));
    }

    @PostMapping
    public ResponseEntity<CustomerSaveRequest> createCustomer(@RequestBody @Valid CustomerSaveRequest request) {
        customerServiceImpl.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerSaveResponse> deleteCustomer(@PathVariable Long id) {
        customerServiceImpl.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
