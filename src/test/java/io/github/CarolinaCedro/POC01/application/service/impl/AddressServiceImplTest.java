package io.github.CarolinaCedro.POC01.application.service.impl;

import io.github.CarolinaCedro.POC01.application.dto.request.AddressSaveRequest;
import io.github.CarolinaCedro.POC01.application.dto.response.AddressSaveResponse;
import io.github.CarolinaCedro.POC01.application.exception.ObjectNotFoundException;
import io.github.CarolinaCedro.POC01.application.service.AddressServiceImpl;
import io.github.CarolinaCedro.POC01.domain.entities.Address;
import io.github.CarolinaCedro.POC01.infra.repository.AddressRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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

    public static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";

    @Autowired
    private AddressServiceImpl service;

    @MockBean
    private AddressRepository repository;

    @MockBean
    ModelMap modelMap;

    private Address address;
    private Optional<Address> optionalAddress;

    private AddressSaveRequest addressSaveRequest;
    private AddressSaveResponse addressSaveResponse;


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
    void whenFindByIdReturnAnObjectNotFoundException() {
        when(repository.findById(anyLong())).thenThrow(
                new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO)
        );
        try {
            service.findById(ID);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJETO_NAO_ENCONTRADO, ex.getMessage());
        }
    }


    @Test
    void whenFindAllThenReturnAnListOfAddress() {

        when(repository.findAll()).thenReturn(List.of(address));
        List<Address> response = service.getAll();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals(Address.class, response.get(0).getClass());
        Assertions.assertEquals(ID, response.get(0).getId());
        Assertions.assertEquals(STREET, response.get(0).getStreet());
        Assertions.assertEquals(NUMBER, response.get(0).getNumber());
        Assertions.assertEquals(NEIGHBORHOOD, response.get(0).getNeighborhood());
        Assertions.assertEquals(CITY, response.get(0).getCity());
        Assertions.assertEquals(ZIP_CODE, response.get(0).getZipCode());
        Assertions.assertEquals(STATE, response.get(0).getState());
        Assertions.assertEquals(IS_PRINCIPAL_ADDRESS, response.get(0).getIsPrincipalAddress());
    }

    @Test
    void whenCreateThenReturnSucess() {

        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(address);
        Address response = service.save(addressSaveRequest);
        assertNotNull(response);
        assertEquals(Address.class,response.getClass());
        assertEquals(ID,response.getId());
        assertEquals(STREET,response.getStreet());
        assertEquals(NUMBER,response.getNumber());
        assertEquals(NEIGHBORHOOD,response.getNeighborhood());
        assertEquals(ZIP_CODE,response.getZipCode());
        assertEquals(STATE,response.getState());
        assertEquals(IS_PRINCIPAL_ADDRESS,response.getIsPrincipalAddress());
    }



//    @Test
//    void whenUpdateThenReturnSucess() {
//
//        when(repository.save(ArgumentMatchers.any())).thenReturn(address);
//        Address response = service.update(addressSaveRequest);
//        assertNotNull(response);
//        assertEquals(Address.class,response.getClass());
//        assertEquals(ID,response.getId());
//        assertEquals(STREET,response.getStreet());
//        assertEquals(NUMBER,response.getNumber());
//        assertEquals(NEIGHBORHOOD,response.getNeighborhood());
//        assertEquals(ZIP_CODE,response.getZipCode());
//        assertEquals(STATE,response.getState());
//        assertEquals(IS_PRINCIPAL_ADDRESS,response.getIsPrincipalAddress());
//    }

    @Test
    void deleteWithSucess(){
        when(repository.findById(anyLong())).thenReturn(optionalAddress);
        doNothing().when(repository).deleteById(anyLong());
        service.deleteById(ID);
        verify(repository,times(1)).deleteById(anyLong());
    }

    @Test
    void deleteWithObjectNotFoundException(){
        when(repository.findById(anyLong()))
                .thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));

        try {
            service.deleteById(ID);
        }catch (Exception ex){
          assertEquals(ObjectNotFoundException.class,ex.getClass());
          assertEquals(OBJETO_NAO_ENCONTRADO,ex.getMessage());
        }
    }





    private void startAddress() {
        address = new Address(ID, STREET, NUMBER, NEIGHBORHOOD, CITY, ZIP_CODE, STATE, IS_PRINCIPAL_ADDRESS);
        optionalAddress = Optional.of(new Address(ID, STREET, NUMBER, NEIGHBORHOOD, CITY, ZIP_CODE, STATE, IS_PRINCIPAL_ADDRESS));
        addressSaveRequest = new AddressSaveRequest(STREET, NUMBER, NEIGHBORHOOD, CITY, ZIP_CODE, STATE, IS_PRINCIPAL_ADDRESS);
        addressSaveResponse = new AddressSaveResponse(ID,STREET, NUMBER, NEIGHBORHOOD, CITY, ZIP_CODE, STATE, IS_PRINCIPAL_ADDRESS);
    }
}