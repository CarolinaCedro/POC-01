package io.github.CarolinaCedro.POC01.application.controller;

import io.github.CarolinaCedro.POC01.application.dto.request.AddressSaveRequest;
import io.github.CarolinaCedro.POC01.application.dto.response.AddressSaveResponse;
import io.github.CarolinaCedro.POC01.application.service.AddressServiceImpl;
import io.github.CarolinaCedro.POC01.domain.entities.Address;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class AddressControllerTest {


    public static final int INDEX = 0;
    @Autowired
    private AddressController controller;

    @MockBean
    private AddressServiceImpl service;

    @MockBean
    private ModelMapper mapper;

    private Address address;
    private AddressSaveRequest addressSaveRequest;
    private AddressSaveResponse addressSaveResponse;

    public static final long ID = 1L;
    public static final String STREET = "Rua da paciencia";
    public static final String NUMBER = "456";
    public static final String NEIGHBORHOOD = "Bairro Brasil";
    public static final String CITY = "Rio verde goiás";
    public static final String ZIP_CODE = "9870766";
    public static final String STATE = "Goiás";
    public static final boolean IS_PRINCIPAL_ADDRESS = false;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startAddressControler();
    }


    @Test
    void whenFindByIdThenReturnSucessController() {
        when(service.findById(anyLong())).thenReturn(address);
        when(mapper.map(any(), any())).thenReturn(addressSaveResponse);
        ResponseEntity<AddressSaveResponse> response = controller.getById(ID);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(ResponseEntity.class, String.valueOf(response.getClass()));
        assertEquals(AddressSaveResponse.class, response.getBody().getClass());
        assertEquals(ID, response.getBody().getId());
        assertEquals(STREET, response.getBody().getStreet());
        assertEquals(NUMBER, response.getBody().getNumber());
        assertEquals(NEIGHBORHOOD, response.getBody().getNeighborhood());
        assertEquals(CITY, response.getBody().getCity());
        assertEquals(ZIP_CODE, response.getBody().getZipCode());
        assertEquals(STATE, response.getBody().getState());
        assertEquals(IS_PRINCIPAL_ADDRESS, response.getBody().isPrincipalAddress());

    }

    @Test
    void whenFindAllThenReturnAListOfAddresResponse() {
        when(service.getAll()).thenReturn(List.of(address));
        when(mapper.map(any(), any())).thenReturn(addressSaveResponse);

        ResponseEntity<List<AddressSaveResponse>> response = controller.getAll();
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(AddressSaveResponse.class, response.getBody().get(INDEX).getClass());

        assertEquals(ID, response.getBody().get(INDEX).getId());
        assertEquals(STREET, response.getBody().get(INDEX).getStreet());
        assertEquals(NUMBER, response.getBody().get(INDEX).getNumber());
        assertEquals(NEIGHBORHOOD, response.getBody().get(INDEX).getNeighborhood());
        assertEquals(CITY, response.getBody().get(INDEX).getCity());
        assertEquals(ZIP_CODE, response.getBody().get(INDEX).getZipCode());
        assertEquals(STATE, response.getBody().get(INDEX).getState());
        assertEquals(IS_PRINCIPAL_ADDRESS, response.getBody().get(INDEX).isPrincipalAddress());


    }

    @Test
    void whendCreateThenReturnCreated() {
        when(service.save(any())).thenReturn(address);
        ResponseEntity<AddressSaveRequest> response = controller.createAddress(addressSaveRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void whenUpdateThenReturnUpdateSucess() {
        when(service.update(addressSaveRequest)).thenReturn(address);
        when(mapper.map(any(), any())).thenReturn(addressSaveRequest);

        ResponseEntity<AddressSaveRequest> response =  controller.updateAddress(ID,addressSaveRequest);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(ResponseEntity.class,response.getClass());
        assertEquals(AddressSaveRequest.class,response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(STREET, response.getBody().getStreet());
        assertEquals(NUMBER, response.getBody().getNumber());
        assertEquals(NEIGHBORHOOD, response.getBody().getNeighborhood());
        assertEquals(CITY, response.getBody().getCity());
        assertEquals(ZIP_CODE, response.getBody().getZipCode());
        assertEquals(STATE, response.getBody().getState());
        assertEquals(IS_PRINCIPAL_ADDRESS, response.getBody().getIsPrincipalAddress());

    }


    @Test
    void whenDeleteThenReturnSucess(){
        doNothing().when(service).deleteById(anyLong());

        ResponseEntity<AddressSaveResponse> response = controller.deleteAddress(ID);
        assertNotNull(response);
        assertEquals(ResponseEntity.class,response.getClass());
        assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
        verify(service,times(1)).deleteById(anyLong());
    }

    private void startAddressControler() {
        address = new Address(ID, STREET, NUMBER, NEIGHBORHOOD, CITY, ZIP_CODE, STATE, IS_PRINCIPAL_ADDRESS);
        addressSaveRequest = new AddressSaveRequest(STREET, NUMBER, NEIGHBORHOOD, CITY, ZIP_CODE, STATE, IS_PRINCIPAL_ADDRESS);
        addressSaveResponse = new AddressSaveResponse(ID, STREET, NUMBER, NEIGHBORHOOD, CITY, ZIP_CODE, STATE, IS_PRINCIPAL_ADDRESS);
    }
}