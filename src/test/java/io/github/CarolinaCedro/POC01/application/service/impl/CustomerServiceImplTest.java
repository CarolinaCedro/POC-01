//package io.github.CarolinaCedro.POC01.application.service.impl;
//
//import io.github.CarolinaCedro.POC01.application.dto.request.AddressSaveRequest;
//import io.github.CarolinaCedro.POC01.application.dto.request.CustomerSaveRequest;
//import io.github.CarolinaCedro.POC01.application.dto.response.CustomerSaveResponse;
//import io.github.CarolinaCedro.POC01.application.exception.ObjectNotFoundException;
//import io.github.CarolinaCedro.POC01.application.service.AddressServiceImpl;
//import io.github.CarolinaCedro.POC01.application.service.CustomerServiceImpl;
//import io.github.CarolinaCedro.POC01.config.modelMapper.ModelMapperConfig;
//import io.github.CarolinaCedro.POC01.domain.entities.Address;
//import io.github.CarolinaCedro.POC01.domain.entities.Customer;
//import io.github.CarolinaCedro.POC01.domain.enums.PjOrPf;
//import io.github.CarolinaCedro.POC01.infra.repository.AddressRepository;
//import io.github.CarolinaCedro.POC01.infra.repository.CustomerRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentMatchers;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.*;
//import static org.mockito.Mockito.times;
//
//@SpringBootTest
//class CustomerServiceImplTest {
//
//
//    public static final long ID = 1L;
//    public static final String EMAIL = "carolcedro@email.com";
//    public static final String PHONE = "987678787";
//    public static final String CPF_OR_CNPJ = "989898778";
//    public static final String STREET = "Rua das lagrimas";
//    public static final String NUMBER = "234";
//    public static final String NEIGHBORHOOD = "Bairro da paz";
//    public static final String CITY = "São paulo";
//    public static final String ZIP_CODE = "3453453453";
//    public static final String STATE = "São Paulo";
//    public static final boolean IS_PRINCIPAL_ADDRESS = true;
//    public static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";
//
//    @Autowired
//    private CustomerServiceImpl service;
//
//    @MockBean
//    private CustomerRepository repository;
//
//    @MockBean
//    private ModelMapper mapper;
//
//    private Customer customer;
//
//    private List<Customer> customerList;
//    private CustomerSaveRequest customerSaveRequest;
//    private CustomerSaveResponse customerSaveResponse;
//    private Optional<Customer> customerOptional;
//    private Address address;
//    private AddressSaveRequest addressSaveRequest;
//    private List<Address> addressList;
//    private List<Long> longList = new ArrayList<>();
//
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        startCustomer();
//    }
//
//
//    @Test
//    void whenFindByIdThenReturnAnCustomerInstance() {
//
//        Mockito.when((repository.findById(anyLong()))).thenReturn(customerOptional);
//        Customer response = service.findById(ID);
//        assertNotNull(response);
//        assertEquals(Customer.class, response.getClass());
//    }
//
//    @Test
//    void whenFindByIdReturnAnObjectNotFoundException() {
//        when(repository.findById(anyLong())).thenThrow(
//                new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO)
//        );
//        try {
//            service.findById(ID);
//        } catch (Exception ex) {
//            assertEquals(ObjectNotFoundException.class, ex.getClass());
//            assertEquals(OBJETO_NAO_ENCONTRADO, ex.getMessage());
//        }
//    }
//
//    @Test
//    void whenFindAllThenReturnAnListOfCustomers() {
//
//        when(repository.findAll()).thenReturn(List.of(customer));
//        List<CustomerSaveResponse> response = service.findAll();
//        Assertions.assertNotNull(response);
//        Assertions.assertEquals(1, response.size());
//
//
//    }
//
//    @Test
//    void whenCreateThenReturnSucess() {
//
//        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(customer);
//        Customer response = service.create(customerSaveRequest);
//        assertNotNull(response);
//        assertEquals(Customer.class,response.getClass());
//        assertEquals(ID,response.getId());
//        assertEquals(EMAIL,response.getEmail());
//        assertEquals(address,response.getAddressPrincipal());
//        assertEquals(addressList,response.getAddress());
//        assertEquals(PHONE,response.getPhone());
//        assertEquals(CPF_OR_CNPJ,response.getCpfOrCnpj());
//        assertEquals(PjOrPf.PJ,response.getPjOrPf());
//
//    }
//
//    @Test
//    void deleteWithSucess(){
//        when(repository.findById(anyLong())).thenReturn(customerOptional);
//        doNothing().when(repository).deleteById(anyLong());
//        service.deleteById(ID);
//        verify(repository,times(1)).deleteById(anyLong());
//    }
//
//    @Test
//    void deleteWithObjectNotFoundException(){
//        when(repository.findById(anyLong()))
//                .thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));
//
//        try {
//            service.deleteById(ID);
//        }catch (Exception ex){
//            assertEquals(ObjectNotFoundException.class,ex.getClass());
//            assertEquals(OBJETO_NAO_ENCONTRADO,ex.getMessage());
//        }
//    }
//
//
//
//    private void startCustomer() {
//
//        longList.add(ID);
//        address = new Address(ID, STREET, NUMBER, NEIGHBORHOOD, CITY, ZIP_CODE, STATE, IS_PRINCIPAL_ADDRESS);
//        addressList = List.of(address);
//        customer = new Customer(ID, EMAIL, address, addressList, PHONE, CPF_OR_CNPJ, PjOrPf.PJ);
//        customerOptional = Optional.of(new Customer(ID, EMAIL, address, addressList, PHONE, CPF_OR_CNPJ, PjOrPf.PJ));
//        addressSaveRequest = new AddressSaveRequest(ID, STREET, NUMBER, NEIGHBORHOOD, CITY, ZIP_CODE, STATE, IS_PRINCIPAL_ADDRESS);
//        customerSaveRequest = new CustomerSaveRequest(ID, EMAIL, longList, PHONE, CPF_OR_CNPJ, PjOrPf.PJ.toString(), addressSaveRequest);
//    }
//
//
//}