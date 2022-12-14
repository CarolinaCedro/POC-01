package io.github.CarolinaCedro.POC01.application.service;

import io.github.CarolinaCedro.POC01.application.dto.request.AddressSaveRequest;
import io.github.CarolinaCedro.POC01.application.exception.ObjectNotFoundException;
import io.github.CarolinaCedro.POC01.application.service.impl.AddressService;
import io.github.CarolinaCedro.POC01.config.modelMapper.ModelMapperConfig;
import io.github.CarolinaCedro.POC01.domain.entities.Address;
import io.github.CarolinaCedro.POC01.infra.repository.AddressRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {


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



    @Override
    public Address update(AddressSaveRequest request) {
        if(request == null || request.getId() == null){
            throw new ObjectNotFoundException("Objeto não encontrado");
        }
        return this.addressRepository.save(modelMapper.convert().map(request,Address.class));
    }

    @Transactional
    public void deleteById(Long id) {
        Optional<Address> address = addressRepository.findById(id);
        if (address.isPresent()){
            addressRepository.deleteById(id);
        }

    }

}
