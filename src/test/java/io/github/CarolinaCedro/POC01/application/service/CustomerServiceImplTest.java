package io.github.CarolinaCedro.POC01.application.service;

import io.github.CarolinaCedro.POC01.application.dto.request.AddressSaveRequest;
import io.github.CarolinaCedro.POC01.application.dto.request.CustomerSaveRequest;
import io.github.CarolinaCedro.POC01.application.dto.request.CustomerUpdateRequest;
import io.github.CarolinaCedro.POC01.application.dto.response.AddressConversorResponse;
import io.github.CarolinaCedro.POC01.application.dto.response.AddressSaveResponse;
import io.github.CarolinaCedro.POC01.application.dto.response.CustomerMainAddressResponse;
import io.github.CarolinaCedro.POC01.application.dto.response.CustomerSaveResponse;
import io.github.CarolinaCedro.POC01.application.errors.exception.ObjectNotFoundException;
import io.github.CarolinaCedro.POC01.application.service.impl.CustomerServiceImpl;
import io.github.CarolinaCedro.POC01.config.app.AppConstants;
import io.github.CarolinaCedro.POC01.domain.entities.Address;
import io.github.CarolinaCedro.POC01.domain.entities.Customer;
import io.github.CarolinaCedro.POC01.domain.enums.PjOrPf;
import io.github.CarolinaCedro.POC01.infra.repository.AddressRepository;
import io.github.CarolinaCedro.POC01.infra.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class CustomerServiceImplTest {


    public static final long ID = 1L;
    public static final String EMAIL = "carolcedro@email.com";
    public static final String PHONE = "987678787";
    public static final String CPF_OR_CNPJ = "731.016.625-67";
    public static final String LOGRADOURO = "Rua da paciencia";
    public static final String NUMBER = "456";
    public static final String BAIRRO = "Bairro Brasil";
    public static final String LOCALIDADE = "Rio Verde";
    public static final String CEP = "75914000";
    public static final String UF = "GO";
    public static final boolean IS_PRINCIPAL_ADDRESS = true;

    public static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";
    public static final String PJ_OR_PF = "PF";
    public static final int VERSION = 1;

    @Autowired
    private CustomerServiceImpl service;

    @MockBean
    private CustomerRepository repository;

    @MockBean
    private AddressRepository addressRepository;


    private ModelMapper modelMapper;

    private Customer customer;

    private List<Customer> customerList;
    private CustomerSaveRequest customerSaveRequest;
    private CustomerSaveResponse customerSaveResponse;
    private Optional<Customer> customerOptional;
    private Address address;
    private Address address2;
    private Address address3;
    private AddressSaveRequest addressSaveRequest;

    private CustomerMainAddressResponse customerMainAddressResponse;
    private CustomerUpdateRequest customerUpdateRequest;
    private List<Address> addressList;
    private AddressConversorResponse addressConversorResponses;
    private List<Long> longList = new ArrayList<>();
    private Pageable pageable = PageRequest.of(0, 5, Sort.by(AppConstants.UPDATED_ON).descending());


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        modelMapper = new ModelMapper();
        startCustomer();
    }


    @Test
    void whenFindByIdThenReturnAnCustomerInstanceV1() {

        Mockito.when((repository.findById(anyLong()))).thenReturn(customerOptional);
        Optional<CustomerSaveResponse> response = service.getById(ID);
        assertNotNull(response);
    }

    @Test
    void whenFindByIdThenReturnAnCustomerInstanceV2() {
        Mockito.when((repository.findById(anyLong()))).thenReturn(customerOptional);
        Optional<CustomerMainAddressResponse> response = service.findByCustomerMainAddres(ID);
        assertNotNull(response);
    }

    @Test
    void whenGetPrincipalAddress() {

        Mockito.when((repository.findById(anyLong()))).thenReturn(customerOptional);
        Optional<AddressSaveResponse> response = service.getPrincipalAddress(ID);
        assertNotNull(response);
    }

    @Test
    void whenFindByAddressPrincipalThenReturnSucess() {

        Mockito.when((repository.findById(anyLong()))).thenReturn(customerOptional);
        Optional<CustomerSaveResponse> response = service.getById(ID);
        assertNotNull(response);
    }

    @Test
    void whenFindCustomerByEmail() {
        Mockito.when((repository.findByEmailIsLikeIgnoreCase(anyString()))).thenReturn(List.of(customer));
        List<CustomerSaveResponse> response = service.findCustomerByEmail(EMAIL);
        assertNotNull(response);
    }

    @Test
    void whenFindByIdReturnAnObjectNotFoundException() {
        when(repository.findById(anyLong())).thenThrow(
                new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO)
        );
        try {
            service.getById(ID);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJETO_NAO_ENCONTRADO, ex.getMessage());
        }
    }



    @Test
    void whenFindByCustomerMainAddres() {

        Mockito.when((repository.findById(anyLong()))).thenReturn(customerOptional);
        Optional<CustomerMainAddressResponse> response = service.findByCustomerMainAddres(ID);
        assertNotNull(response);
    }


    @Test
    void whenFindAllThenReturnAnListOfCustomers() {

        // Test data.
        Page<Customer> page = new PageImpl<>(Arrays.asList(new Customer()));
        // Test mocks.
        when(repository.findAll(pageable)).thenReturn(page);
        // Tests.
        service.findAll(pageable);
        verify(repository, times(1)).findAll(pageable);
    }

    @Test
    void whenCreateThenReturnSucess() {

        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(customer);
        CustomerSaveResponse response = service.create(customerSaveRequest);
        assertNotNull(response);
        assertEquals(CustomerSaveResponse.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(address, response.getAddressPrincipal());
        assertEquals(PHONE, response.getPhone());
        assertEquals(CPF_OR_CNPJ, response.getCpfOrCnpj());
        assertEquals(PjOrPf.PJ.toString(), response.getPjOrPf());

    }


    @Test
    void whenUpdateThenReturnSucess() {

        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(customer);
        Mockito.when(repository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(customer));
        Mockito.when(addressRepository.findAllById(ArgumentMatchers.any())).thenReturn(addressList);

        CustomerSaveResponse response = service.update(ID, customerUpdateRequest);
        assertNotNull(response);
        assertEquals(CustomerSaveResponse.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(EMAIL,response.getEmail());
        assertEquals(address,response.getAddressPrincipal());
        assertEquals(PHONE,response.getPhone());
        assertEquals(CPF_OR_CNPJ,response.getCpfOrCnpj());
        assertEquals(PjOrPf.PF.toString(),response.getPjOrPf());
    }

    @Test
    void whenUpdateAddressPrincipalThenReturnSucess() {

        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(customer);
        Mockito.when(repository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(customer));
        Mockito.when(addressRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(address));

        CustomerSaveResponse response = service.changePrincipalAddress(ID, customerSaveRequest);
        assertNotNull(response);
        assertEquals(CustomerSaveResponse.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(EMAIL,response.getEmail());
        assertEquals(address,response.getAddressPrincipal());
        assertEquals(PHONE,response.getPhone());
        assertEquals(CPF_OR_CNPJ,response.getCpfOrCnpj());
        assertEquals(PjOrPf.PJ.toString(),response.getPjOrPf());
    }


    @Test
    void deleteWithSucess() {
        when(repository.findById(anyLong())).thenReturn(customerOptional);
        doNothing().when(repository).deleteById(anyLong());
        service.deleteById(ID);
        verify(repository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteWithObjectNotFoundException() {
        when(repository.findById(anyLong()))
                .thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));

        try {
            service.deleteById(ID);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJETO_NAO_ENCONTRADO, ex.getMessage());
        }
    }


    private void startCustomer() {

        longList.add(2L);
        longList.add(3L);
        address = new Address(ID, VERSION, LOGRADOURO, NUMBER, BAIRRO, LOCALIDADE, CEP, UF, IS_PRINCIPAL_ADDRESS);
        address2 = new Address(2L, VERSION, LOGRADOURO, NUMBER, BAIRRO, LOCALIDADE, CEP, UF, IS_PRINCIPAL_ADDRESS);
        address3 = new Address(3L, VERSION, LOGRADOURO, NUMBER, BAIRRO, LOCALIDADE, CEP, UF, IS_PRINCIPAL_ADDRESS);
        addressList = List.of(address);
        addressConversorResponses = new AddressConversorResponse(ID, VERSION, LOGRADOURO, NUMBER, BAIRRO, LOCALIDADE, CEP, UF, IS_PRINCIPAL_ADDRESS);
        customer = new Customer(ID, VERSION, EMAIL, address, addressList, PHONE, CPF_OR_CNPJ, PjOrPf.PJ);
        customerOptional = Optional.of(new Customer(ID, EMAIL, address, addressList, PHONE, CPF_OR_CNPJ, PjOrPf.PJ));
        addressSaveRequest = new AddressSaveRequest(3L, LOGRADOURO, NUMBER, BAIRRO, LOCALIDADE, CEP, UF, IS_PRINCIPAL_ADDRESS);
        customerSaveRequest = new CustomerSaveRequest(ID, EMAIL, longList, PHONE, CPF_OR_CNPJ, PJ_OR_PF, addressSaveRequest);
        customerSaveResponse = new CustomerSaveResponse(ID, EMAIL, List.of(addressConversorResponses), PHONE, CPF_OR_CNPJ, PJ_OR_PF);
        customerMainAddressResponse = new CustomerMainAddressResponse(ID, EMAIL, address, PHONE, CPF_OR_CNPJ, PjOrPf.PJ);
        customerUpdateRequest = new CustomerUpdateRequest(ID, EMAIL, longList, PHONE, CPF_OR_CNPJ, PJ_OR_PF);
    }


}