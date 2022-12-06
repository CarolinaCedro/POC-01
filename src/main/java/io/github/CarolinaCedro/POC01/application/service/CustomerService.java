package io.github.CarolinaCedro.POC01.application.service;

import io.github.CarolinaCedro.POC01.application.dto.request.CustomerSaveRequest;
import io.github.CarolinaCedro.POC01.application.dto.response.AddressSaveResponse;
import io.github.CarolinaCedro.POC01.application.dto.response.CustomerSaveResponse;
import io.github.CarolinaCedro.POC01.config.errors.FullmailingListException;
import io.github.CarolinaCedro.POC01.config.modelMapper.ModelMapperConfig;
import io.github.CarolinaCedro.POC01.domain.entities.Address;
import io.github.CarolinaCedro.POC01.domain.entities.Customer;
import io.github.CarolinaCedro.POC01.infra.repository.AddressRepository;
import io.github.CarolinaCedro.POC01.infra.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;

    private final ModelMapperConfig mapper;


    public List<CustomerSaveResponse> findAll() {
        return customerRepository.findAll().stream().map(this::dto).collect(Collectors.toList());
    }

    public Optional<CustomerSaveResponse> findById(Long id) {
        return customerRepository.findById(id).map(this::dto);
    }

    @Transactional
    public void create(CustomerSaveRequest request) {
        List<Address> addressList = addressRepository.findAllById(request.getAddress());
        Long idPrincipal = null;
        for (Address ids : addressList
        ) {
            if (ids.getIsPrincipalAddress()) {

                idPrincipal = ids.getId();
            }
        }

        if (idPrincipal != null) {
            Optional<Address> principalAddress = addressRepository.findById(idPrincipal);
            if (addressList.size() > 5) {
                throw new FullmailingListException("Atenção:Tamanho permitido para a lista de endereços foi excedido.");
            } else {

                Customer customer = new Customer(request.getEmail(), addressList, request.getPhone(),
                        request.getCpfOrCnpj(), request.getPjOrPf(), principalAddress.get()
                );

                customerRepository.save(customer);
            }
        }

    }

    @Transactional
    public Customer changePrincipalAddress(Long id, CustomerSaveRequest update) {
        Assert.notNull(id, "Não foi possivel atualizar o registro");
        Optional<Customer> optional = customerRepository.findById(id);
        Optional<Address> addressUpdate = addressRepository.findById(update.getAddressPrincipal().getId());

        if (optional.isPresent()) {
            Customer db = optional.get();
            db.zera();
            if (addressUpdate.isPresent()){
                db.setAddressPrincipal(addressUpdate.get());
                Address address = addressUpdate.get();
                address.setIsPrincipalAddress(true);
                customerRepository.save(db);
            }

            return db;
        }
        return null;
    }

    public Optional<AddressSaveResponse> getPrincipalAddress(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()){
            Long address = customer.get().getAddressPrincipal().getId();
            return addressRepository.findById(address).map(this::addressDtoConverter);
        }
        return Optional.empty();
    }

    public List<CustomerSaveResponse> findCustomarByEmail(String email) {
        return customerRepository.findByEmail("%" + email + "%").stream().map(this::dto).collect(Collectors.toList());
    }

    public CustomerSaveResponse dto(Customer customer) {
        return mapper.convert().map(customer, CustomerSaveResponse.class);
    }

    public AddressSaveResponse addressDtoConverter(Address address) {
        return mapper.convert().map(address, AddressSaveResponse.class);
    }

}
