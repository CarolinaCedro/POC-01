package io.github.CarolinaCedro.POC01.application.service.impl;

import io.github.CarolinaCedro.POC01.application.dto.request.AddressSaveRequest;
import io.github.CarolinaCedro.POC01.application.dto.response.AddressSaveResponse;
import io.github.CarolinaCedro.POC01.application.exception.ObjectNotFoundException;
import io.github.CarolinaCedro.POC01.config.modelMapper.ModelMapperConfig;
import io.github.CarolinaCedro.POC01.domain.entities.Address;
import io.github.CarolinaCedro.POC01.infra.repository.AddressRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Builder(toBuilder = true)
public class AddressServiceImpl implements AddressService{


    private final AddressRepository addressRepository;

    private final ModelMapperConfig modelMapper;



    @Override
    public List<Address> getAll() {
        List<Address> list = addressRepository.findAll();
//        return addressRepository.findAll().stream().map(this::dto).collect(Collectors.toList());
        return list;
    }


    @Override
    public Address findById(Long id) {
        Optional<Address> address = addressRepository.findById(id);
        return address.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado"));
//        return addressRepository.findById(id).map(this::dto);
    }


    @Override
    public Address save(AddressSaveRequest request) {
        return addressRepository.save(modelMapper.convert().map(request,Address.class));
    }


    @Transactional
    public void deleteById(Long id) {
        Optional<Address> address = addressRepository.findById(id);
        if (address.isPresent()){
            addressRepository.deleteById(id);
        }

    }

    public AddressSaveResponse dto(Address address) {
        return modelMapper.convert().map(address,AddressSaveResponse.class);
    }
}
