package io.github.CarolinaCedro.POC01.application.controller;

import io.github.CarolinaCedro.POC01.application.dto.request.AddressSaveRequest;
import io.github.CarolinaCedro.POC01.application.dto.request.CustomerSaveRequest;
import io.github.CarolinaCedro.POC01.application.dto.request.CustomerUpdateRequest;
import io.github.CarolinaCedro.POC01.application.dto.response.AddressConversorResponse;
import io.github.CarolinaCedro.POC01.application.dto.response.AddressSaveResponse;
import io.github.CarolinaCedro.POC01.application.dto.response.CustomerMainAddressResponse;
import io.github.CarolinaCedro.POC01.application.dto.response.CustomerSaveResponse;
import io.github.CarolinaCedro.POC01.application.service.impl.CustomerServiceImpl;
import io.github.CarolinaCedro.POC01.domain.entities.Address;
import io.github.CarolinaCedro.POC01.domain.entities.Customer;
import io.github.CarolinaCedro.POC01.domain.enums.PjOrPf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class CustomerControllerTest {


    public static final int INDEX = 0;
    public static final long ID = 1L;
    public static final String LOGRADOURO = "Rua da paciencia";
    public static final String NUMBER = "456";
    public static final String BAIRRO = "Bairro Brasil";
    public static final String LOCALIDADE = "Rio verde goiás";
    public static final String CEP = "9870766";
    public static final String UF = "Goiás";
    public static final boolean IS_PRINCIPAL_ADDRESS = false;
    public static final String EMAIL = "carol@email.com";
    public static final String PHONE = "9876567866";
    public static final String CPF_OR_CNPJ = "243.060.230-01";
    public static final String PJ_OR_PF = "PF";
    @Autowired
    private CustomerController controller;

    @MockBean
    private CustomerServiceImpl service;

    @MockBean
    private ModelMapper mapper;

    private Customer customer;

    private List<Customer> customerList;
    private List<CustomerSaveResponse> customerSaveResponses;
    private CustomerSaveRequest customerSaveRequest;
    private CustomerSaveResponse customerSaveResponse;

    private CustomerUpdateRequest customerUpdateRequest;
    private Optional<Customer> customerOptional;
    private Optional<CustomerSaveResponse> customerSaveResponseOptional;
    private Optional<AddressSaveResponse> addressSaveResponseOptional;

    private CustomerMainAddressResponse customerMainAddressResponse;

    private AddressSaveResponse addressSaveResponse;
    private List<AddressConversorResponse> addressConversorResponses = new ArrayList<>();
    private Address address;
    private AddressSaveRequest addressSaveRequest;
    private List<Address> addressList;
    private List<Long> longList = new ArrayList<>();


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startCustomer();
    }


    @Test
    void whenFindByIdThenReturnSucessControllerV1() {
        when(service.getById(anyLong())).thenReturn(customerSaveResponseOptional);
        when(mapper.map(any(), any())).thenReturn(customerSaveResponse);
        ResponseEntity<CustomerSaveResponse> response = controller.getById(ID);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(ResponseEntity.class, String.valueOf(response.getClass()));
        assertEquals(CustomerSaveResponse.class, response.getBody().getClass());
        assertEquals(ID, response.getBody().getId());
    }

    @Test
    void whenFindByIdThenReturnSucessControllerV2() {
        when(service.findByCustomerMainAddres(anyLong())).thenReturn(Optional.of(customerMainAddressResponse));
        when(mapper.map(any(), any())).thenReturn(customerMainAddressResponse);
        ResponseEntity<CustomerMainAddressResponse> response = controller.getCustomerWithMainAddress(ID);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(ResponseEntity.class, String.valueOf(response.getClass()));
        assertEquals(CustomerMainAddressResponse.class, response.getBody().getClass());
        assertEquals(ID, response.getBody().getId());
    }


    @Test
    void whenFindByPrincipalAddressThenReturnSucessController() {
        when(service.getPrincipalAddress(anyLong())).thenReturn(addressSaveResponseOptional);
        when(mapper.map(any(), any())).thenReturn(addressSaveResponse);
        ResponseEntity<AddressSaveResponse> response = controller.getPrincipalAddress(ID);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(ResponseEntity.class, String.valueOf(response.getClass()));
        assertEquals(AddressSaveResponse.class, response.getBody().getClass());
        assertEquals(ID, response.getBody().getId());
    }


//    @Test
//    void whenFindByEmailThenReturnSucessController() {
//        when(service.findCustomerByEmail(anyString())).thenReturn(List.of(customerSaveResponse));
//        when(mapper.map(any(), any())).thenReturn(customerSaveResponse);
//        ResponseEntity<?> response = controller.getByEmail(EMAIL);
//        assertNotNull(response);
//        assertNotNull(response.getBody());
//        assertNotNull(ResponseEntity.class, String.valueOf(response.getClass()));
//        assertEquals(CustomerSaveResponse.class, response.getBody().getClass());
//        assertEquals(EMAIL, response.getBody());
//    }

    @Test
    void whenFindAllThenReturnAListOfAddresResponse() {
        // Test data.
        Pageable pageable = PageRequest.of(0, 5);
        Page<CustomerSaveResponse> page = mock(Page.class);
        // Test mocks.
        when(service.findAll(pageable)).thenReturn(page);
        // Tests.
        controller.getAll(pageable);
        verify(service, times(1)).findAll(pageable);
    }

    @Test
    void whendCreateThenReturnCreated() {
        when(service.create(any())).thenReturn(customerSaveResponse);
        ResponseEntity<CustomerSaveRequest> response = controller.createCustomer(customerSaveRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }




    @Test
    void whenSetAddressPrincipalThenReturnUpdateSucess() {
        when(service.changePrincipalAddress(ID, customerSaveRequest)).thenReturn(customerSaveResponse);
        when(mapper.map(any(), any())).thenReturn(customerSaveResponse);
        ResponseEntity<CustomerSaveResponse> response = controller.setAddressPrincipal(ID, customerSaveRequest);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(CustomerSaveResponse.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
    }

    @Test
    void whenUpdateAddressSucess() {
        when(service.update(ID, customerUpdateRequest)).thenReturn(customerSaveResponse);
        when(mapper.map(any(), any())).thenReturn(customerSaveResponse);
        ResponseEntity<CustomerSaveResponse> response = controller.updateAddress(ID, customerUpdateRequest);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(CustomerSaveResponse.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
    }


    @Test
    void whenDeleteThenReturnSucess() {
        doNothing().when(service).deleteById(anyLong());

        ResponseEntity<CustomerSaveResponse> response = controller.deleteCustomer(ID);
        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).deleteById(anyLong());
    }

    private void startCustomer() {

        longList.add(ID);
        address = new Address(ID, LOGRADOURO, NUMBER, BAIRRO, LOCALIDADE, CEP, UF, IS_PRINCIPAL_ADDRESS);
        addressList = List.of(address);
        longList = List.of(address.getId());
        addressConversorResponses = List.of();
        customer = new Customer(ID, EMAIL, address, addressList, PHONE, CPF_OR_CNPJ, PjOrPf.PJ);
        customerOptional = Optional.of(new Customer(ID, EMAIL, address, addressList, PHONE, CPF_OR_CNPJ, PjOrPf.PJ));
        customerSaveResponseOptional = Optional.of(new CustomerSaveResponse(ID,EMAIL, address, addressConversorResponses, PHONE, CPF_OR_CNPJ, PJ_OR_PF));
        addressSaveResponseOptional = Optional.of(new AddressSaveResponse(ID, LOGRADOURO, NUMBER, BAIRRO, LOCALIDADE, CEP, UF, IS_PRINCIPAL_ADDRESS));
        addressSaveRequest = new AddressSaveRequest(ID, LOGRADOURO, NUMBER, BAIRRO, LOCALIDADE, CEP, UF, IS_PRINCIPAL_ADDRESS);
        addressSaveResponse = new AddressSaveResponse(ID, LOGRADOURO, NUMBER, BAIRRO, LOCALIDADE, CEP, UF, IS_PRINCIPAL_ADDRESS);
        customerSaveRequest = new CustomerSaveRequest(ID, EMAIL, longList, PHONE, CPF_OR_CNPJ, PjOrPf.PJ.toString(), addressSaveRequest);
        customerUpdateRequest = new CustomerUpdateRequest(ID,EMAIL,longList,PHONE,CPF_OR_CNPJ,PJ_OR_PF);
        customerSaveResponse = new CustomerSaveResponse(ID, EMAIL, address, addressConversorResponses, PHONE, CPF_OR_CNPJ, PjOrPf.PF.toString());
        customerMainAddressResponse = new CustomerMainAddressResponse(ID,EMAIL,address,PHONE,CPF_OR_CNPJ,PjOrPf.PF);
    }

}