package io.github.CarolinaCedro.POC01.application.controller;

import io.github.CarolinaCedro.POC01.application.dto.request.AddressSaveRequest;
import io.github.CarolinaCedro.POC01.application.dto.request.CustomerSaveRequest;
import io.github.CarolinaCedro.POC01.application.dto.response.AddressConversorResponse;
import io.github.CarolinaCedro.POC01.application.dto.response.AddressSaveResponse;
import io.github.CarolinaCedro.POC01.application.dto.response.CustomerSaveResponse;
import io.github.CarolinaCedro.POC01.application.service.CustomerServiceImpl;
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
    public static final String STREET = "Rua das flores";
    public static final String NUMBER = "897";
    public static final String NEIGHBORHOOD = "bairro do sol";
    public static final String CITY = "Juazeiro";
    public static final String ZIP_CODE = "87876455";
    public static final String STATE = "Belo horizonte";
    public static final boolean IS_PRINCIPAL_ADDRESS = true;
    public static final String EMAIL = "carol@email.com";
    public static final String PHONE = "9876567866";
    public static final String CPF_OR_CNPJ = "9878987767";
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
    private Optional<Customer> customerOptional;
    private Optional<AddressSaveResponse> addressSaveResponseOptional;
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
    void whenFindByIdThenReturnSucessController() {
        when(service.findById(anyLong())).thenReturn(customer);
        when(mapper.map(any(), any())).thenReturn(customerSaveResponse);
        ResponseEntity<CustomerSaveResponse> response = controller.getById(ID);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(ResponseEntity.class, String.valueOf(response.getClass()));
        assertEquals(CustomerSaveResponse.class, response.getBody().getClass());
        assertEquals(ID, response.getBody().getId());
    }


    @Test
    void whenFindByPrincipalAddressThenReturnSucessController() {
        when(service.getPrincipalAddress(anyLong())).thenReturn(addressSaveResponseOptional);
        when(mapper.map(any(), any())).thenReturn(customerSaveResponse);
        ResponseEntity<CustomerSaveResponse> response = controller.getPrincipalAddress(ID);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(ResponseEntity.class, String.valueOf(response.getClass()));
        assertEquals(CustomerSaveResponse.class, response.getBody().getClass());
        assertEquals(ID, response.getBody().getId());
    }


    @Test
    void whenFindByEmailThenReturnSucessController() {
        when(service.findCustomarByEmail(anyString())).thenReturn(List.of(customerSaveResponse));
        when(mapper.map(any(), any())).thenReturn(customerSaveResponse);
        ResponseEntity<CustomerSaveResponse> response = controller.getByEmail(EMAIL);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(ResponseEntity.class, String.valueOf(response.getClass()));
        assertEquals(CustomerSaveResponse.class, response.getBody().getClass());
        assertEquals(EMAIL, response.getBody().getEmail());
    }

    @Test
    void whenFindAllThenReturnAListOfAddresResponse() {
        when(service.findAll()).thenReturn(List.of(customerSaveResponse));
        when(mapper.map(any(), any())).thenReturn(customerSaveResponse);

        ResponseEntity<List<CustomerSaveResponse>> response = controller.getaAll();
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(CustomerSaveResponse.class, response.getBody().get(INDEX).getClass());

        assertEquals(ID, response.getBody().get(INDEX).getId());
    }

    @Test
    void whendCreateThenReturnCreated() {
        when(service.create(any())).thenReturn(customer);
        ResponseEntity<CustomerSaveRequest> response = controller.createCustomer(customerSaveRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void whenSetAddressPrincipalThenReturnUpdateSucess() {
        when(service.changePrincipalAddress(ID,customerSaveRequest)).thenReturn(customer);
        when(mapper.map(any(), any())).thenReturn(customerSaveRequest);
        ResponseEntity<CustomerSaveRequest> response =  controller.setAddressPrincipal(ID,customerSaveRequest);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(ResponseEntity.class,response.getClass());
        assertEquals(CustomerSaveRequest.class,response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
    }


    @Test
    void whenDeleteThenReturnSucess(){
        doNothing().when(service).deleteById(anyLong());

        ResponseEntity<CustomerSaveResponse> response = controller.deleteCustomer(ID);
        assertNotNull(response);
        assertEquals(ResponseEntity.class,response.getClass());
        assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
        verify(service,times(1)).deleteById(anyLong());
    }

    private void startCustomer() {

        longList.add(ID);
        address = new Address(ID, STREET, NUMBER, NEIGHBORHOOD, CITY, ZIP_CODE, STATE, IS_PRINCIPAL_ADDRESS);
        addressList = List.of(address);
        longList = List.of(address.getId());
        addressConversorResponses = List.of();
        customer = new Customer(ID, EMAIL, address, addressList, PHONE, CPF_OR_CNPJ, PjOrPf.PJ);
        customerOptional = Optional.of(new Customer(ID, EMAIL, address, addressList, PHONE, CPF_OR_CNPJ, PjOrPf.PJ));
        addressSaveResponseOptional = Optional.of(new AddressSaveResponse(ID, STREET, NUMBER, NEIGHBORHOOD, CITY, ZIP_CODE, STATE, IS_PRINCIPAL_ADDRESS));
        addressSaveRequest = new AddressSaveRequest(ID, STREET, NUMBER, NEIGHBORHOOD, CITY, ZIP_CODE, STATE, IS_PRINCIPAL_ADDRESS);
        customerSaveRequest = new CustomerSaveRequest(ID, EMAIL, longList, PHONE, CPF_OR_CNPJ, PjOrPf.PJ.toString(), addressSaveRequest);
        customerSaveResponse = new CustomerSaveResponse(ID,EMAIL,address,addressConversorResponses,PHONE,CPF_OR_CNPJ,PjOrPf.PF.toString());
    }

}