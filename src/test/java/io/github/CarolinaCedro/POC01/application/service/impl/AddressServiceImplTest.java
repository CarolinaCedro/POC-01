package io.github.CarolinaCedro.POC01.application.service.impl;

import io.github.CarolinaCedro.POC01.application.dto.request.AddressSaveRequest;
import io.github.CarolinaCedro.POC01.application.dto.response.AddressSaveResponse;
import io.github.CarolinaCedro.POC01.application.exception.ObjectNotFoundException;
import io.github.CarolinaCedro.POC01.application.service.AddressServiceImpl;
import io.github.CarolinaCedro.POC01.config.app.AppConstants;
import io.github.CarolinaCedro.POC01.domain.entities.Address;
import io.github.CarolinaCedro.POC01.infra.repository.AddressRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.ui.ModelMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class AddressServiceImplTest {

    public static final long ID = 1L;
    public static final String STREET = "Rua da paciencia";
    public static final String NUMBER = "456";
    public static final String NEIGHBORHOOD = "Bairro Brasil";
    public static final String CITY = "Rio Verde";
    public static final String ZIP_CODE = "75914000";
    public static final String STATE = "GO";
    public static final boolean IS_PRINCIPAL_ADDRESS = true;

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

    private Pageable pageable = PageRequest.of(0, 5, Sort.by(AppConstants.UPDATED_ON).descending());




    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startAddress();
        updateValidAddres();
    }

    private Address updateValidAddres() {
        return Address.builder().id(ID).logradouro(STREET).number(NUMBER).bairro(NEIGHBORHOOD).cep(ZIP_CODE).uf(STATE).isPrincipalAddress(IS_PRINCIPAL_ADDRESS).build();
    }


    @Test
    void whenFindByIdThenReturnAnAddressInstanceV1() {

        Mockito.when((repository.findById(anyLong()))).thenReturn(optionalAddress);
        Optional<AddressSaveResponse> response = service.getById(ID);
        assertNotNull(response);

        assertTrue(response.isPresent());
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
    void whenCreateReturnsObjectNotFoundException() {

        when(repository.save(any())).thenThrow(
                new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO)
        );
        try {
            service.update(ID, addressSaveRequest);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJETO_NAO_ENCONTRADO, ex.getMessage());
        }
    }


    @Test
    void whenFindAllThenReturnAnListOfAddress() {

        // Test data.
        Page<Address> page = new PageImpl<>(Arrays.asList(new Address()));
        // Test mocks.
        when(repository.findAll(pageable)).thenReturn(page);
        // Tests.
        service.getAll(pageable);
        verify(repository, times(1)).findAll(pageable);

    }


    @Test
    void whenCreateThenReturnSucess() throws IOException {

        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(address);
        AddressSaveResponse response = service.save(addressSaveRequest);
        assertNotNull(response);
        assertEquals(AddressSaveResponse.class,response.getClass());
        assertEquals(ID,response.getId());
        assertEquals(STREET,response.getLogradouro());
        assertEquals(NUMBER,response.getNumber());
        assertEquals(NEIGHBORHOOD,response.getBairro());
        assertEquals(ZIP_CODE,response.getCep());
        assertEquals(CITY,response.getLocalidade());
        assertEquals(STATE,response.getUf());
        assertEquals(IS_PRINCIPAL_ADDRESS,response.isPrincipalAddress());
    }

    @Test
    void whenUpdateThenReturnSucess() throws IOException {

        Mockito.when(repository.save(address)).thenReturn(address);
        AddressSaveResponse response = service.update(address.getId(), addressSaveRequest);
        assertNotNull(response);
        assertEquals(AddressSaveResponse.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(STREET, response.getLogradouro());
        assertEquals(NUMBER, response.getNumber());
        assertEquals(NEIGHBORHOOD, response.getLocalidade());
        assertEquals(ZIP_CODE, response.getCep());
        assertEquals(STATE, response.getUf());
        assertEquals(IS_PRINCIPAL_ADDRESS, response.isPrincipalAddress());
    }


    @Test
    void deleteWithSucess() {
        when(repository.findById(anyLong())).thenReturn(optionalAddress);
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


    private void startAddress() {
        address = new Address(ID, STREET, NUMBER, NEIGHBORHOOD, CITY, ZIP_CODE, STATE, IS_PRINCIPAL_ADDRESS);
        optionalAddress = Optional.of(new Address(ID, STREET, NUMBER, NEIGHBORHOOD, CITY, ZIP_CODE, STATE, IS_PRINCIPAL_ADDRESS));
        addressSaveRequest = new AddressSaveRequest(ID, STREET, NUMBER, NEIGHBORHOOD, CITY, ZIP_CODE, STATE, IS_PRINCIPAL_ADDRESS);
        addressSaveResponse = new AddressSaveResponse(ID, STREET, NUMBER, NEIGHBORHOOD, CITY, ZIP_CODE, STATE, IS_PRINCIPAL_ADDRESS);
    }
}