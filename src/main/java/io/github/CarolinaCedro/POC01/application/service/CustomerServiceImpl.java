package io.github.CarolinaCedro.POC01.application.service;

import io.github.CarolinaCedro.POC01.application.dto.request.CustomerSaveRequest;
import io.github.CarolinaCedro.POC01.application.dto.request.CustomerUpdateRequest;
import io.github.CarolinaCedro.POC01.application.dto.response.AddressSaveResponse;
import io.github.CarolinaCedro.POC01.application.dto.response.CustomerMainAddressResponse;
import io.github.CarolinaCedro.POC01.application.dto.response.CustomerSaveResponse;
import io.github.CarolinaCedro.POC01.application.exception.ObjectNotFoundException;
import io.github.CarolinaCedro.POC01.application.service.impl.CustomerService;
import io.github.CarolinaCedro.POC01.config.errors.FullmailingListException;
import io.github.CarolinaCedro.POC01.config.modelMapper.ModelMapperConfig;
import io.github.CarolinaCedro.POC01.domain.entities.Address;
import io.github.CarolinaCedro.POC01.domain.entities.Customer;
import io.github.CarolinaCedro.POC01.domain.enums.PjOrPf;
import io.github.CarolinaCedro.POC01.infra.repository.AddressRepository;
import io.github.CarolinaCedro.POC01.infra.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;

    private final ModelMapperConfig mapper;

    @Override
    public Page<CustomerSaveResponse> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable).map(this::dtoCustomerFullAddress);
    }

    @Override
    public Optional<CustomerSaveResponse> getById(Long id) {
        return Optional.ofNullable(customerRepository.findById(id).map(this::dtoCustomerFullAddress).orElseThrow(
                () -> new ObjectNotFoundException("Cliente não existe na base de dados ! ")
        ));
    }

    @Override
    public Optional<CustomerMainAddressResponse> findByCustomerMainAddres(Long id) {
        return Optional.ofNullable(customerRepository.findById(id).map(this::dtoMainAdrress).orElseThrow(
                () -> new ObjectNotFoundException("Cliente não existe na base de dados ! ")
        ));
    }


    @Override
    @Transactional
    public CustomerSaveResponse create(CustomerSaveRequest request) {
        List<Address> addressList = addressRepository.findAllById(request.getAddress());
        Customer customer = new Customer();
        Long idPrincipal = null;
        for (Address ids : addressList
        ) {
            if (ids.getIsPrincipalAddress()) {

                idPrincipal = ids.getId();
            }
        }

        if (idPrincipal != null) {
            Optional<Address> principalAddress = addressRepository.findById(idPrincipal);
            if (addressList.size() >= 6) {
                throw new FullmailingListException("Atenção:tamanho permitido para a lista de endereços foi excedido.");
            }
            customer = new Customer(request.getEmail(), addressList, request.getPhone(),
                    request.getCpfOrCnpj(), request.getPjOrPf(), principalAddress.get()
            );

        }else {
            throw new ObjectNotFoundException("Endereço principal não existe na base de dados ! ");
        }


        return mapper.convert().map(customerRepository.save(customer),CustomerSaveResponse.class);
    }

    @Transactional
    @Override
    public CustomerSaveResponse update(Long id,@Valid CustomerUpdateRequest request) {
        Assert.notNull(request.getId(), "Unable to update registration");
        Customer response = new Customer();

        Optional<Customer> optional = customerRepository.findById(request.getId());
        if (optional.isPresent()){
            List<Address> addressList = addressRepository.findAllById(request.getAddress());
            if (addressList.size() >= 6){
                throw new FullmailingListException("Você excedeu o limite máximo disponível de endereços ! Exclua endereços não utilizados para prosseguir.");
            }

            Address principal = optional.get().getAddressPrincipal();

            CustomerUpdateRequest update = new CustomerUpdateRequest();
            update.setFalse(addressList,principal);

            Customer db = optional.get();
            db.setEmail(request.getEmail());
            db.setAddress(addressList);
            db.setPhone(request.getPhone());
            db.setCpfOrCnpj(request.getCpfOrCnpj());
            db.setPjOrPf(PjOrPf.valueOf(request.getPjOrPf()));
            customerRepository.save(db);
            response = db;
        }

        return  mapper.convert().map(response,CustomerSaveResponse.class);
    }

    @Override
    @Transactional
    public CustomerSaveResponse  changePrincipalAddress(Long id, CustomerSaveRequest update) {
        Assert.notNull(id, "Não foi possivel atualizar o registro");
        Optional<Customer> optional = Optional.ofNullable(customerRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Cliente não existe na base de dados ! ")
        ));
        Optional<Address> addressUpdate = Optional.ofNullable(addressRepository.findById(update.getAddressPrincipal().getId()).orElseThrow(
                () -> new ObjectNotFoundException("Endereço não existe na base de dados ! ")));

        Customer response = new Customer();

        if (optional.isPresent()) {
            Customer db = optional.get();
            db.resetRealAddressers();
            if (addressUpdate.isPresent()) {
                db.setAddressPrincipal(addressUpdate.get());
                Address address = addressUpdate.get();
                address.setIsPrincipalAddress(true);
                customerRepository.save(db);
                response = db;
            }


        }
        return mapper.convert().map(response,CustomerSaveResponse.class);
    }

    @Override
    public Optional<AddressSaveResponse> getPrincipalAddress(Long id) {
        Optional<Customer> customer = Optional.ofNullable(customerRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Endereço não existe na base de dados ! ")
        ));

        if (customer.isPresent()) {
            Long address = customer.get().getAddressPrincipal().getId();
            return addressRepository.findById(address).map(this::addressDtoConverter);
        }
        return Optional.empty();
    }

    @Override
    public List<CustomerSaveResponse> findCustomerByEmail(String email) {
        return customerRepository.findByEmail("%" + email + "%").stream().map(this::dtoCustomerFullAddress).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteById(Long id) {

        Optional<Customer> customer = Optional.ofNullable(customerRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("Cliente não consta na base de dados")
        ));
        if (customer.isPresent()) {
            customerRepository.deleteById(id);
        }

    }


    public CustomerSaveResponse dtoCustomerFullAddress(Customer customer) {
        return mapper.convert().map(customer, CustomerSaveResponse.class);
    }

    public CustomerMainAddressResponse dtoMainAdrress(Customer customer) {
        return mapper.convert().map(customer, CustomerMainAddressResponse.class);
    }


    public AddressSaveResponse addressDtoConverter(Address address) {
        return mapper.convert().map(address, AddressSaveResponse.class);
    }

}
