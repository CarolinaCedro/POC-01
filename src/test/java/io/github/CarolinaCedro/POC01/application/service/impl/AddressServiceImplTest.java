package io.github.CarolinaCedro.POC01.application.service.impl;

import io.github.CarolinaCedro.POC01.config.modelMapper.ModelMapperConfig;
import io.github.CarolinaCedro.POC01.domain.entities.Address;
import io.github.CarolinaCedro.POC01.infra.repository.AddressRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class AddressServiceImplTest {

    public static final long ID = 1L;
    public static final String STREET = "Rua da paciencia";
    public static final String NUMBER = "456";
    public static final String NEIGHBORHOOD = "Bairro Brasil";
    public static final String CITY = "Rio verde goiás";
    public static final String ZIP_CODE = "9870766";
    public static final String STATE = "Goiás";
    public static final boolean IS_PRINCIPAL_ADDRESS = false;

    @InjectMocks
    private AddressServiceImpl service;

    @Mock
    private AddressRepository repository;
    @Mock
    private ModelMapperConfig mapperConfig;

    private Address address ;
    private Address address2;
    private Optional<Address>optionalAddress = Optional.of(new Address(ID, STREET, NUMBER, NEIGHBORHOOD, CITY, ZIP_CODE, STATE, IS_PRINCIPAL_ADDRESS));



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startAddress();
    }


    @Test
    void whenFindByIdThenReturnAnAddressInstance() {

        Mockito.when((repository.findById(anyLong()))).thenReturn(optionalAddress);
        Address response = service.findById(ID);
        assertNotNull(response);

        assertEquals(Address.class, response.getClass());
    }


    @Test
    void whenFindAllThenReturnAnListOfUsers() {

        when(repository.findAll()).thenReturn(List.of(address));
        List<Address> response = service.getAll();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1,response.size());
        Assertions.assertEquals(Address.class,response.get(0).getClass());
        Assertions.assertEquals(ID,response.get(0).getId());
        Assertions.assertEquals(STREET,response.get(0).getStreet());
        Assertions.assertEquals(NUMBER,response.get(0).getNumber());
        Assertions.assertEquals(NEIGHBORHOOD,response.get(0).getNeighborhood());
        Assertions.assertEquals(CITY,response.get(0).getCity());
        Assertions.assertEquals(ZIP_CODE,response.get(0).getZipCode());
        Assertions.assertEquals(STATE,response.get(0).getState());
        Assertions.assertEquals(IS_PRINCIPAL_ADDRESS,response.get(0).getIsPrincipalAddress());
    }

    @Test
    void findById() {
    }

    @TestD
    void save() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void dto() {
    }

    private void startAddress() {
       address = new Address(ID, STREET, NUMBER, NEIGHBORHOOD, CITY, ZIP_CODE, STATE, IS_PRINCIPAL_ADDRESS);
        Address address1 = new Address(ID, STREET, NUMBER, NEIGHBORHOOD, CITY, ZIP_CODE, STATE, IS_PRINCIPAL_ADDRESS);
       address2 = new Address(ID, STREET, NUMBER, NEIGHBORHOOD, CITY, ZIP_CODE, STATE, IS_PRINCIPAL_ADDRESS);
       optionalAddress = Optional.of(new Address(ID, STREET, NUMBER, NEIGHBORHOOD, CITY, ZIP_CODE, STATE, IS_PRINCIPAL_ADDRESS));
    }
}