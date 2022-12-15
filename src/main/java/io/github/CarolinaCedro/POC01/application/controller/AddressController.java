package io.github.CarolinaCedro.POC01.application.controller;

import io.github.CarolinaCedro.POC01.application.dto.request.AddressSaveRequest;
import io.github.CarolinaCedro.POC01.application.dto.response.AddressSaveResponse;
import io.github.CarolinaCedro.POC01.application.service.impl.AddressService;
import io.github.CarolinaCedro.POC01.config.modelMapper.ModelMapperConfig;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {


    private final AddressService addressService;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<AddressSaveResponse>> getAll() {
        return ResponseEntity.ok().body(addressService.getAll()
                .stream().map(x -> mapper.map(x,AddressSaveResponse.class))
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressSaveResponse> getById(@PathVariable Long id) {
        return  ResponseEntity.ok().body(mapper.map(addressService.findById(id),AddressSaveResponse.class));
    }

    @PostMapping
    public ResponseEntity<AddressSaveRequest> createAddress( @RequestBody @Valid AddressSaveRequest request) throws IOException {
        addressService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressSaveResponse>updateAddress(@PathVariable Long id,@Valid @RequestBody AddressSaveRequest request) throws IOException {
        request.setId(id);
        return ResponseEntity.ok().body(mapper.map(addressService.update(id,request),AddressSaveResponse.class));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<AddressSaveResponse> deleteAddress(@PathVariable Long id) {
        addressService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
