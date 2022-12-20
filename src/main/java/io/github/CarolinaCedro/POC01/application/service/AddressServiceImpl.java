package io.github.CarolinaCedro.POC01.application.service;

import com.google.gson.Gson;
import io.github.CarolinaCedro.POC01.application.dto.request.AddressSaveRequest;
import io.github.CarolinaCedro.POC01.application.dto.response.AddressSaveResponse;
import io.github.CarolinaCedro.POC01.application.service.impl.AddressService;
import io.github.CarolinaCedro.POC01.config.app.AppConstants;
import io.github.CarolinaCedro.POC01.config.modelMapper.ModelMapperConfig;
import io.github.CarolinaCedro.POC01.domain.entities.Address;
import io.github.CarolinaCedro.POC01.infra.repository.AddressRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {


    private final AddressRepository addressRepository;

    private final ModelMapperConfig modelMapper;

    @Cacheable("addresses")
    public Page<AddressSaveResponse> getAll(Pageable pageable) {
        // If unsorted, set default sorting.
        if (!pageable.getSort().isSorted()) {
            pageable = PageRequest.of(pageable.getPageNumber(),
                    pageable.getPageSize(), Sort.by(AppConstants.UPDATED_ON).descending());
        }

        Page<Address> page = addressRepository.findAll(pageable);
        return page.map(this::dto);
    }

    @Override
    @Cacheable("address")
    public Optional<AddressSaveResponse> getById(Long id) {
        Optional<Address> address = addressRepository.findById(id);
        return addressRepository.findById(id).map(this::dto);
    }


    @Transactional
    @Override
    @Caching(evict = {
            @CacheEvict(value = "addresses", allEntries = true),
            @CacheEvict(value = "address", allEntries = true)})
    public AddressSaveResponse save(AddressSaveRequest request) throws IOException {
        //Consumindo API publica externa

        AddressSaveRequest addressAux = consumingCepExternalApi(request.getCep());

        request.setLogradouro(request.getLogradouro());
        request.setNumber(request.getNumber());
        request.setBairro(request.getBairro());
        request.setLocalidade(addressAux.getLocalidade());
        request.setCep(request.getCep());
        request.setUf(addressAux.getUf());
        request.setIsPrincipalAddress(request.getIsPrincipalAddress());

        addressRepository.save(modelMapper.convert().map(request, Address.class));

        return modelMapper.convert().map(request, AddressSaveResponse.class);
    }


    @Transactional
    @Override
    @Caching(evict = {
            @CacheEvict(value = "addresses", allEntries = true),
            @CacheEvict(value = "address", allEntries = true)})
    public AddressSaveResponse update(Long id, AddressSaveRequest request) throws IOException {

        Assert.notNull(id, "Unable to update registration");
        Optional<Address> optional = addressRepository.findById(id);
        Address response = new Address();
        if (optional.isPresent()) {
            Address db = optional.get();

            AddressSaveRequest addressAux = consumingCepExternalApi(request.getCep());

            db.setLogradouro(request.getLogradouro());
            db.setNumber(request.getNumber());
            db.setBairro(request.getBairro());
            db.setLocalidade(addressAux.getLocalidade());
            db.setCep(request.getCep());
            db.setUf(addressAux.getUf());
            db.setIsPrincipalAddress(request.getIsPrincipalAddress());
            addressRepository.save(db);
            response = db;
        }
        return modelMapper.convert().map(response,AddressSaveResponse.class);
    }

    @Transactional
    @Override
    @Caching(evict = {
            @CacheEvict(value = "addresses", allEntries = true),
            @CacheEvict(value = "address", allEntries = true)})
    public void deleteById(Long id) {
        Optional<Address> address = addressRepository.findById(id);
        if (address.isPresent()) {
            addressRepository.deleteById(id);
        }

    }




    public AddressSaveRequest consumingCepExternalApi( String requestCep) throws IOException {

        URL url = new URL("https://viacep.com.br/ws/" + requestCep + "/json/");
        URLConnection connection = url.openConnection();
        InputStream is = connection.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

        String cep = "";
        StringBuilder jsonCep = new StringBuilder();

        while ((cep = br.readLine()) != null) {
            jsonCep.append(cep);
        }

        AddressSaveRequest addressAux = null;

        return  addressAux = new Gson().fromJson(jsonCep.toString(), AddressSaveRequest.class);
    }



    public AddressSaveResponse dto(Address address) {
        return modelMapper.convert().map(address, AddressSaveResponse.class);
    }



}
