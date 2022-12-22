package io.github.CarolinaCedro.POC01.application.integration;

import io.github.CarolinaCedro.POC01.application.dto.request.CustomerSaveRequest;
import io.github.CarolinaCedro.POC01.application.dto.request.CustomerUpdateRequest;
import io.github.CarolinaCedro.POC01.application.dto.response.CustomerSaveResponse;
import io.github.CarolinaCedro.POC01.application.util.address.AddressCreator;
import io.github.CarolinaCedro.POC01.application.wrapper.PageableResponse;
import io.github.CarolinaCedro.POC01.domain.entities.Address;
import io.github.CarolinaCedro.POC01.domain.entities.Customer;
import io.github.CarolinaCedro.POC01.domain.enums.PjOrPf;
import io.github.CarolinaCedro.POC01.infra.repository.AddressRepository;
import io.github.CarolinaCedro.POC01.infra.repository.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CustomerControllerIT {


    public static final String EMAIL = "carol@email.com";
    public static final String PHONE = "064993456789";
    public static final String CPF_OR_CNPJ = "706.008.490-82";
    public static final String PJ_OR_PF = "PF";
    public static final long ID = 1L;
    @Autowired
    private CustomerRepository repository;
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private ModelMapper mapper;

    private Address address;
    private Address addresUpdate;

    private Customer customer;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startAddressInstance();
    }


    @Test
    void whenFindAllThenReturnAListOfCustomerResponse() {


        Customer savedCustomer = repository.save(customer);

        Long expectedId = savedCustomer.getId();

        PageableResponse<Customer> CustomersPage = testRestTemplate.exchange("/api/customer", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Customer>>() {
                }).getBody();

        Assertions.assertThat(CustomersPage).isNotNull();

        Assertions.assertThat(CustomersPage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(CustomersPage.toList().get(0).getId()).isEqualTo(expectedId);

    }

    @Test
    void findById_ReturnsCustomer_WhenSuccessfulV1() {


        Customer savedCustomer = repository.save(customer);

        Long expectedId = savedCustomer.getId();

        CustomerSaveResponse savedCustomerResponse = testRestTemplate.getForObject("/api/customer/v1/customer/{id}", CustomerSaveResponse.class, expectedId);

        Assertions.assertThat(savedCustomerResponse).isNotNull();

        Assertions.assertThat(savedCustomerResponse.getId()).isNotNull().isEqualTo(expectedId);
    }



    @Test
    void whendCreateThenReturnCreated() {

        Address addresSaved = addressRepository.save(new Address("Rua das flores","345","Bairro da paz","Santa Helena","75920000","GO",true));
        List<Address> addressList = new ArrayList<>();
        addressList.add(addresSaved);

        ResponseEntity<CustomerSaveResponse> responsePostForEntity = testRestTemplate.postForEntity("/api/customer",
                new CustomerUpdateRequest(ID,EMAIL,List.of(1L),PHONE,CPF_OR_CNPJ,PJ_OR_PF), CustomerSaveResponse.class);
        Assertions.assertThat(responsePostForEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

//        Customer responsePostForObject = testRestTemplate.postForObject("/api/customer",
//                new Customer(ID,EMAIL,address,List.of(address),PHONE,CPF_OR_CNPJ, PjOrPf.PF), Customer.class);
//        Assertions.assertThat(responsePostForObject.getPhone()).isEqualTo("064993456789");

    }

//    @Test
//    void whenUpdateThenReturnUpdateSucess() {
//
//        Customer savedCustomers = repository.save(updateRequest);
//
//        savedCustomers.setPhone("998456574");
//        Long expectedId = savedCustomers.getId();
//
//        ResponseEntity<CustomerSaveResponse> customerResponseEntity = testRestTemplate.exchange("/api/customer/{id}",
//                HttpMethod.PUT, new HttpEntity<>(savedCustomers), CustomerSaveResponse.class, expectedId);
//
//        Assertions.assertThat(customerResponseEntity).isNotNull();
//        Assertions.assertThat(customerResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//    }


    @Test
    void whenDeleteThenReturnSucess() {
        Customer customerAddress = repository.save(customer);

        ResponseEntity<Void> customerResponseEntity = testRestTemplate.exchange("/api/customer/{id}",
                HttpMethod.DELETE, null, Void.class, customerAddress.getId());

        Assertions.assertThat(customerResponseEntity).isNotNull();

        Assertions.assertThat(customerResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }



    private void startAddressInstance() {
        address = addressRepository.save(AddressCreator.createAddressToBeSaved());
        addresUpdate = addressRepository.save(AddressCreator.createValidUpdatedAddress());
        customer = new Customer(ID,EMAIL,address,List.of(address),PHONE,CPF_OR_CNPJ, PjOrPf.PF);
    }
}
