package io.github.CarolinaCedro.POC01.application.controller;

import io.github.CarolinaCedro.POC01.application.dto.request.CustomerSaveRequest;
import io.github.CarolinaCedro.POC01.application.dto.response.AddressSaveResponse;
import io.github.CarolinaCedro.POC01.application.dto.response.CustomerSaveResponse;
import io.github.CarolinaCedro.POC01.application.service.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerServiceImpl customerServiceImpl;
    private final ModelMapper mapper;


    @GetMapping
    public ResponseEntity<List<CustomerSaveResponse>> getaAll() {
        return ResponseEntity.ok().body(customerServiceImpl.findAll()
                .stream().map(x -> mapper.map(x,CustomerSaveResponse.class))
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerSaveResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(mapper.map(customerServiceImpl.findById(id),CustomerSaveResponse.class));
    }


    @GetMapping("/filter")
    public ResponseEntity<CustomerSaveResponse> getByEmail(@RequestParam(value = "email",required = false, defaultValue = "") String email) {
        return ResponseEntity.ok().body(mapper.map(customerServiceImpl.findCustomarByEmail(email),CustomerSaveResponse.class));

    }


    @GetMapping("/address/customer/principal/{id}")
    public ResponseEntity<CustomerSaveResponse> getPrincipalAddress(@PathVariable Long id) {
        return ResponseEntity.ok().body(mapper.map(customerServiceImpl.getPrincipalAddress(id),CustomerSaveResponse.class));

    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerSaveRequest>updateAddress(@PathVariable Long id,@RequestBody @Valid CustomerSaveRequest request){
        request.setId(id);
        return ResponseEntity.ok().body(mapper.map(customerServiceImpl.update(id, request),CustomerSaveRequest.class));
    }


    @PatchMapping("/updateAddressPrincipal/{id}")
    public ResponseEntity<CustomerSaveRequest> setAddressPrincipal(@PathVariable Long id, @RequestBody CustomerSaveRequest update) {
        update.setId(id);
        return ResponseEntity.ok().body(mapper.map(customerServiceImpl.changePrincipalAddress(id, update),CustomerSaveRequest.class));
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
