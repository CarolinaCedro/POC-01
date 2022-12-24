package io.github.CarolinaCedro.POC01.application.integration;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.CarolinaCedro.POC01.application.dto.request.CustomerSaveRequest;
import io.github.CarolinaCedro.POC01.application.dto.request.CustomerUpdateRequest;
import io.github.CarolinaCedro.POC01.application.dto.response.AddressSaveResponse;
import io.github.CarolinaCedro.POC01.application.dto.response.CustomerMainAddressResponse;
import io.github.CarolinaCedro.POC01.application.dto.response.CustomerSaveResponse;
import io.github.CarolinaCedro.POC01.application.util.address.AddressCreator;
import io.github.CarolinaCedro.POC01.application.util.address.CustomerCreator;
import io.github.CarolinaCedro.POC01.application.wrapper.PageableResponse;
import io.github.CarolinaCedro.POC01.domain.entities.Address;
import io.github.CarolinaCedro.POC01.domain.entities.Customer;
import io.github.CarolinaCedro.POC01.domain.enums.PjOrPf;
import io.github.CarolinaCedro.POC01.infra.repository.AddressRepository;
import io.github.CarolinaCedro.POC01.infra.repository.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.util.*;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CustomerControllerIT {


    public static final String EMAIL = "test@example.com";
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

    private RestTemplate patchRestTemplate;

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
        this.patchRestTemplate = testRestTemplate.getRestTemplate();
        startAddressInstance();
    }


    @Test
    void whenFindAllThenReturnAListOfCustomerResponse() {


        Customer savedCustomer = repository.save(customer);

        Long expectedId = savedCustomer.getId();

        PageableResponse<CustomerSaveResponse> CustomersPage = testRestTemplate.exchange("/api/customer", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<CustomerSaveResponse>>() {
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
    void findById_ReturnsCustomer_WhenSuccessfulV2() {


        Customer savedCustomer = repository.save(customer);

        Long expectedId = savedCustomer.getId();

        CustomerMainAddressResponse savedCustomerResponseMain = testRestTemplate.getForObject("/api/customer/v2/customer/{id}", CustomerMainAddressResponse.class, expectedId);

        Assertions.assertThat(savedCustomerResponseMain).isNotNull();

        Assertions.assertThat(savedCustomerResponseMain.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    void whenFindByEmailThenReturnSucessController() {

        Address savedAddress = addressRepository.save(AddressCreator.createAddressToBeSaved());
        Customer customerSaved = repository.save(new Customer(EMAIL,List.of(savedAddress),PHONE,CPF_OR_CNPJ,PJ_OR_PF,savedAddress));


        String expectedEmail = customerSaved.getEmail();

        String url = String.format("/api/customer/filter?email=%s", expectedEmail);

        List<CustomerSaveResponse> customer = testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<CustomerSaveResponse>>() {
                }).getBody();

        Assertions.assertThat(customer)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(customer.get(0).getEmail()).isEqualTo(expectedEmail);

    }

    @Test
    void findByEmail_ReturnsEmptyListOfCustomer_WhenEmailIsNotFound(){
        List<CustomerSaveResponse> animes = testRestTemplate.exchange("/api/customer/filter?email=dbz", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<CustomerSaveResponse>>() {
                }).getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isEmpty();
    }

    @Test
    void findById_getPrincipalAddress() {

        Address savedAddress = addressRepository.save(AddressCreator.createAddressToBeSaved());
        Customer customerSaved = repository.save(new Customer(EMAIL,List.of(savedAddress),PHONE,CPF_OR_CNPJ,PJ_OR_PF,savedAddress));

        Long expectedId = customerSaved.getAddressPrincipal().getId();

        AddressSaveResponse customerMainAddress = testRestTemplate.getForObject("/api/customer/address/principal/{id}", AddressSaveResponse.class, expectedId);

        Assertions.assertThat(customerMainAddress).isNotNull();

        Assertions.assertThat(customerMainAddress.getId()).isNotNull().isEqualTo(expectedId);
    }



    @Test
    void whenCreateThenReturnCreated() {

        Address addresSaved = addressRepository.save(new Address("Rua das flores","345","Bairro da paz","Santa Helena","75920000","GO",true));
        List<Address> addressList = new ArrayList<>();
        addressList.add(addresSaved);

        ResponseEntity<CustomerSaveResponse> responsePostForEntity = testRestTemplate.postForEntity("/api/customer",
                new CustomerUpdateRequest(ID,EMAIL,List.of(1L),PHONE,CPF_OR_CNPJ,PJ_OR_PF), CustomerSaveResponse.class);
        Assertions.assertThat(responsePostForEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }


    @Test
    void whenUpdateThenReturnUpdateSucess() {
        Address savedAddress = addressRepository.save(AddressCreator.createAddressToBeSaved());
        Customer customerSaved = repository.save(new Customer(EMAIL,List.of(savedAddress),PHONE,CPF_OR_CNPJ,PJ_OR_PF,savedAddress));
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(ID,EMAIL,List.of(1L),PHONE,CPF_OR_CNPJ,PJ_OR_PF);

        updateRequest.setPhone("64883907889");
        Long expectedId = savedAddress.getId();

        ResponseEntity<CustomerSaveResponse> customerResponseEntity = testRestTemplate.exchange("/api/customer/{id}",
                HttpMethod.PUT, new HttpEntity<>(updateRequest), CustomerSaveResponse.class, expectedId);

        Assertions.assertThat(customerResponseEntity).isNotNull();
        Assertions.assertThat(customerResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


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
