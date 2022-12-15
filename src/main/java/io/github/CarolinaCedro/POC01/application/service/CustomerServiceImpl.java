package io.github.CarolinaCedro.POC01.application.service;

import io.github.CarolinaCedro.POC01.application.dto.request.AddressSaveRequest;
import io.github.CarolinaCedro.POC01.application.dto.request.CustomerSaveRequest;
import io.github.CarolinaCedro.POC01.application.dto.response.AddressSaveResponse;
import io.github.CarolinaCedro.POC01.application.dto.response.CustomerSaveResponse;
import io.github.CarolinaCedro.POC01.application.exception.ObjectNotFoundException;
import io.github.CarolinaCedro.POC01.application.service.impl.CustomerService;
import io.github.CarolinaCedro.POC01.config.errors.FullmailingListException;
import io.github.CarolinaCedro.POC01.domain.entities.Address;
import io.github.CarolinaCedro.POC01.domain.entities.Customer;
import io.github.CarolinaCedro.POC01.domain.enums.PjOrPf;
import io.github.CarolinaCedro.POC01.infra.repository.AddressRepository;
import io.github.CarolinaCedro.POC01.infra.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;

    private final ModelMapper mapper;

    @Override
    @Cacheable("customer")
    public List<CustomerSaveResponse> findAll() {
        return customerRepository.findAll().stream().map(this::dto).collect(Collectors.toList());
    }

    @Override
    @Cacheable("findCustomer")
    public Customer findById(Long id) {
        Optional<Customer> customerSaveResponse = customerRepository.findById(id);
        return customerSaveResponse.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    @Override
    @Transactional
    public Customer create(CustomerSaveRequest request) {
        List<Address> addressList = addressRepository.findAllById(request.getAddress());
        Customer customer = null;
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
            }
            customer = new Customer(request.getEmail(), addressList, request.getPhone(),
                    request.getCpfOrCnpj(), request.getPjOrPf(), principalAddress.get()
            );
        }

        return customerRepository.save(customer);
    }

    @Transactional
    @Override
    public Customer update(Long id, CustomerSaveRequest customerSaveRequest) {
        Assert.notNull(id, "Unable to update registration");
        Optional<Customer> optional = customerRepository.findById(id);
        List<Address> addressList = addressRepository.findAllById(customerSaveRequest.getAddress());
        AddressSaveRequest addressPrincipal = customerSaveRequest.getAddressPrincipal();
        Address addressUpdated =  mapper.map(addressPrincipal,Address.class);

        if (optional.isPresent()) {
            Customer db = optional.get();
            db.setEmail(customerSaveRequest.getEmail());
            db.setAddress(addressList);
            db.setPhone(customerSaveRequest.getPhone());
            db.setCpfOrCnpj(customerSaveRequest.getCpfOrCnpj());
            db.setPjOrPf(PjOrPf.valueOf(customerSaveRequest.getPjOrPf()));
            db.setAddressPrincipal(addressUpdated);
            customerRepository.save(db);
            return db;
        }
        return null;
    }

    @Override
    @Transactional
    public Customer changePrincipalAddress(Long id, CustomerSaveRequest update) {
        Assert.notNull(id, "Não foi possivel atualizar o registro");
        Optional<Customer> optional = customerRepository.findById(id);
        Optional<Address> addressUpdate = addressRepository.findById(update.getAddressPrincipal().getId());

        if (optional.isPresent()) {
            Customer db = optional.get();
            db.zera();
            if (addressUpdate.isPresent()) {
                db.setAddressPrincipal(addressUpdate.get());
                Address address = addressUpdate.get();
                address.setIsPrincipalAddress(true);
                customerRepository.save(db);
            }

            return db;
        }
        return null;
    }

    @Override
    public Optional<AddressSaveResponse> getPrincipalAddress(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            Long address = customer.get().getAddressPrincipal().getId();
            return addressRepository.findById(address).map(this::addressDtoConverter);
        }
        return Optional.empty();
    }

    @Override
    public List<CustomerSaveResponse> findCustomarByEmail(String email) {
        return customerRepository.findByEmail("%" + email + "%").stream().map(this::dto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            customerRepository.deleteById(id);
        }

    }


    public CustomerSaveResponse dto(Customer customer) {
        return mapper.map(customer, CustomerSaveResponse.class);
    }


    public AddressSaveResponse addressDtoConverter(Address address) {
        return mapper.map(address, AddressSaveResponse.class);
    }


}
