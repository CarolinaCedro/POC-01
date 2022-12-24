package io.github.CarolinaCedro.POC01.application.integration;

import io.github.CarolinaCedro.POC01.application.dto.request.AddressSaveRequest;
import io.github.CarolinaCedro.POC01.application.dto.response.AddressSaveResponse;
import io.github.CarolinaCedro.POC01.application.util.address.AddressCreator;
import io.github.CarolinaCedro.POC01.application.wrapper.PageableResponse;
import io.github.CarolinaCedro.POC01.domain.entities.Address;
import io.github.CarolinaCedro.POC01.infra.repository.AddressRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AddressControllerIT {


    @Autowired
    private AddressRepository repository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private ModelMapper mapper;


    @Test
    void whenFindAllThenReturnAListOfAddresResponse() {

        Address savedAddress = repository.save(AddressCreator.createAddressToBeSaved());

        String expectedName = savedAddress.getLogradouro();

        PageableResponse<Address> addressPage = testRestTemplate.exchange("/api/address", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Address>>() {
                }).getBody();

        Assertions.assertThat(addressPage).isNotNull();

        Assertions.assertThat(addressPage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(addressPage.toList().get(0).getLogradouro()).isEqualTo(expectedName);

    }

    @Test
    void findById_ReturnsAddress_WhenSuccessful() {
        Address savedAddres = repository.save(AddressCreator.createAddressToBeSaved());

        Long expectedId = savedAddres.getId();

        Address address = testRestTemplate.getForObject("/api/address/{id}", Address.class, expectedId);

        Assertions.assertThat(address).isNotNull();

        Assertions.assertThat(address.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    void whendCreateThenReturnCreated() {
        ResponseEntity<AddressSaveResponse> responsePostForEntity = testRestTemplate.postForEntity("/api/address",
                new AddressSaveRequest("Rua das flores","45b","Bairro martins","Santa Helena","75920000","GO",true), AddressSaveResponse.class);
        Assertions.assertThat(responsePostForEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }


    @Test
    void whenUpdateThenReturnUpdateSucess() {
        Address savedAddress = repository.save(AddressCreator.createAddressToBeSaved());

        savedAddress.setLogradouro("Rua das Flores");
        Long expectedId = savedAddress.getId();

        ResponseEntity<AddressSaveResponse> addressResponseEntity = testRestTemplate.exchange("/api/address/{id}",
                HttpMethod.PUT, new HttpEntity<>(savedAddress), AddressSaveResponse.class, expectedId);

        Assertions.assertThat(addressResponseEntity).isNotNull();
        Assertions.assertThat(addressResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    @Test
    void whenDeleteThenReturnSucess() {
        Address savedAddress = repository.save(AddressCreator.createAddressToBeSaved());

        ResponseEntity<Void> addressResponseEntity = testRestTemplate.exchange("/api/address/{id}",
                HttpMethod.DELETE, null, Void.class, savedAddress.getId());

        Assertions.assertThat(addressResponseEntity).isNotNull();

        Assertions.assertThat(addressResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}
