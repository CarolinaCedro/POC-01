package io.github.CarolinaCedro.POC01.application.controller;

import io.github.CarolinaCedro.POC01.application.dto.request.CustomerAddressPrincipalUpdate;
import io.github.CarolinaCedro.POC01.application.dto.request.CustomerSaveRequest;
import io.github.CarolinaCedro.POC01.application.dto.response.CustomerSaveResponse;
import io.github.CarolinaCedro.POC01.application.service.CustomerService;
import io.github.CarolinaCedro.POC01.domain.entities.Address;
import io.github.CarolinaCedro.POC01.domain.entities.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


    @GetMapping
    public ResponseEntity<?> getaAll() {
        return ResponseEntity.ok(customerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.findById(id));
    }


    @GetMapping("/filter")
    public ResponseEntity<?> getByEmail(@RequestParam(value = "email",required = false, defaultValue = "") String email) {
        return ResponseEntity.ok(customerService.findCustomarByEmail(email));
    }


    @GetMapping("/address/customer/principal/{id}")
    public ResponseEntity<?> getPrincipalAddress(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getPrincipalAddress(id));
    }

    @PatchMapping("/updateAddressPrincipal/{id}")
    public ResponseEntity<?> setAddressPrincipal(@PathVariable Long id, @RequestBody CustomerSaveRequest update) {
        update.setId(id);
        return ResponseEntity.ok(customerService.changePrincipalAddress(id, update));
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody @Valid CustomerSaveRequest request) {
        customerService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
