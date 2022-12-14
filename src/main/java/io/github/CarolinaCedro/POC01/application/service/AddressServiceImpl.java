package io.github.CarolinaCedro.POC01.application.service;

import com.google.gson.Gson;
import io.github.CarolinaCedro.POC01.application.dto.request.AddressSaveRequest;
import io.github.CarolinaCedro.POC01.application.dto.response.AddressSaveResponse;
import io.github.CarolinaCedro.POC01.application.dto.response.CustomerSaveResponse;
import io.github.CarolinaCedro.POC01.application.exception.ObjectNotFoundException;
import io.github.CarolinaCedro.POC01.application.service.impl.AddressService;
import io.github.CarolinaCedro.POC01.config.modelMapper.ModelMapperConfig;
import io.github.CarolinaCedro.POC01.domain.entities.Address;
import io.github.CarolinaCedro.POC01.domain.entities.Customer;
import io.github.CarolinaCedro.POC01.infra.repository.AddressRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {


    private final AddressRepository addressRepository;

    private final ModelMapperConfig modelMapper;


    @Override
    public List<AddressSaveResponse> getAll() {
        return addressRepository.findAll().stream().map(this::dto).collect(Collectors.toList());
    }


    @Override
    public Address findById(Long id) {
        Optional<Address> address = addressRepository.findById(id);
        return address.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
//        return addressRepository.findById(id).map(this::dto);
    }


    @Override
    public AddressSaveResponse save(AddressSaveRequest request) throws IOException {
        //Consumindo API publica externa

        URL url = new URL("https://viacep.com.br/ws/" + request.getCep() + "/json/");
        URLConnection connection = url.openConnection();
        InputStream is = connection.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

        String cep = "";
        StringBuilder jsonCep = new StringBuilder();

        while ((cep = br.readLine()) != null) {
            jsonCep.append(cep);
        }

        AddressSaveRequest addressAux = new Gson().fromJson(jsonCep.toString(), AddressSaveRequest.class);

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


    @Override
    public Address update(AddressSaveRequest request) {
        if (request == null || request.getId() == null) {
            throw new ObjectNotFoundException("Objeto não encontrado");
        }
        return this.addressRepository.save(modelMapper.convert().map(request, Address.class));
    }

    @Transactional
    public void deleteById(Long id) {
        Optional<Address> address = addressRepository.findById(id);
        if (address.isPresent()) {
            addressRepository.deleteById(id);
        }

    }


    public AddressSaveResponse dto(Address address) {
        return modelMapper.convert().map(address, AddressSaveResponse.class);
    }

}
