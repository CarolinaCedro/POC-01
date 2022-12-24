package io.github.CarolinaCedro.POC01.application.controller;

import io.github.CarolinaCedro.POC01.application.dto.request.AddressSaveRequest;
import io.github.CarolinaCedro.POC01.application.dto.response.AddressSaveResponse;
import io.github.CarolinaCedro.POC01.application.service.AddressService;
import io.github.CarolinaCedro.POC01.config.modelMapper.ModelMapperConfig;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
@Log4j2
@Api(value = "API de Endereços")
public class AddressController {


    private final AddressService addressService;
    private final ModelMapperConfig mapper;


    @GetMapping
    @ApiOperation(value = "Traz uma lista de endereços com paginação")
    public ResponseEntity<Page<AddressSaveResponse>> getAll(@PageableDefault(page = 0, size = 15, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(addressService.getAll(pageable));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Traz um endereço por id", response = String.class)
    public ResponseEntity<AddressSaveResponse> getById(@PathVariable Long id) {
        return   ResponseEntity.ok().body(mapper.convert().map(addressService.getById(id), AddressSaveResponse.class));
    }

    @PostMapping
    @ApiOperation(value = "Cria um novo endereço")
    public ResponseEntity<AddressSaveRequest> createAddress( @RequestBody @Valid AddressSaveRequest request) throws IOException {
        addressService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Altera um endereço existente")
    public ResponseEntity<AddressSaveResponse>updateAddress(@PathVariable Long id,@Valid @RequestBody AddressSaveRequest request) throws IOException {
        request.setId(id);
        return ResponseEntity.ok().body(mapper.convert().map(addressService.update(id,request),AddressSaveResponse.class));
    }


    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleta um endereço")
    public ResponseEntity<AddressSaveResponse> deleteAddress(@PathVariable Long id) {
        addressService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
