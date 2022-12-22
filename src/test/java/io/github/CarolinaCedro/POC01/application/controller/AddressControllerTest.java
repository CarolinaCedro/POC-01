package io.github.CarolinaCedro.POC01.application.controller;

import io.github.CarolinaCedro.POC01.application.dto.request.AddressSaveRequest;
import io.github.CarolinaCedro.POC01.application.dto.response.AddressSaveResponse;
import io.github.CarolinaCedro.POC01.application.exception.ObjectNotFoundException;
import io.github.CarolinaCedro.POC01.application.service.AddressServiceImpl;
import io.github.CarolinaCedro.POC01.config.modelMapper.ModelMapperConfig;
import io.github.CarolinaCedro.POC01.domain.entities.Address;
import jakarta.validation.Valid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class AddressControllerTest {


    public static final int INDEX = 0;
    public static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado.";
    @Autowired
    private AddressController controller;

    @MockBean
    private AddressServiceImpl service;

    @MockBean
    private ModelMapper mapper;

    private Address address;
    private AddressSaveRequest addressSaveRequest;
    private AddressSaveResponse addressSaveResponse;

    private Optional<AddressSaveResponse> addressSaveResponseOptional;
    private Page<AddressSaveResponse>addressPageResponse;
    public static final long ID = 1L;
    public static final String LOGRADOURO = "Rua da paciencia";
    public static final String NUMBER = "456";
    public static final String BAIRRO = "Bairro Brasil";
    public static final String LOCALIDADE = "Rio verde goiás";
    public static final String CEP = "9870766";
    public static final String UF = "Goiás";
    public static final boolean IS_PRINCIPAL_ADDRESS = false;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startAddressControler();
    }


    @Test
    void whenFindByIdThenReturnSucessController() {
        when(service.getById(anyLong())).thenReturn(addressSaveResponseOptional);
        when(mapper.map(any(), any())).thenReturn(addressSaveResponse);
        ResponseEntity<AddressSaveResponse> response = controller.getById(ID);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(ResponseEntity.class, String.valueOf(response.getClass()));
        assertEquals(AddressSaveResponse.class, response.getBody().getClass());
        assertEquals(ID, response.getBody().getId());
        assertEquals(LOGRADOURO, response.getBody().getLogradouro());
        assertEquals(NUMBER, response.getBody().getNumber());
        assertEquals(BAIRRO, response.getBody().getBairro());
        assertEquals(LOCALIDADE, response.getBody().getLocalidade());
        assertEquals(CEP, response.getBody().getCep());
        assertEquals(UF, response.getBody().getUf());
        assertEquals(IS_PRINCIPAL_ADDRESS, response.getBody().isPrincipalAddress());

    }

    @Test
    void whenFindAllThenReturnAListOfAddresResponse() {

        // Test data.
        Pageable pageable = PageRequest.of(0, 5);
        Page<AddressSaveResponse> page = mock(Page.class);
        // Test mocks.
        when(service.getAll(pageable)).thenReturn(page);
        // Tests.
        controller.getAll(pageable);
        verify(service, times(1)).getAll(pageable);
    }

    @Test
    void whendCreateThenReturnCreated() throws IOException {
        when(service.save(any())).thenReturn(addressSaveResponse);
        ResponseEntity<AddressSaveRequest> response = controller.createAddress(addressSaveRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }


    @Test
    void whenUpdateThenReturnUpdateSucess() throws IOException {
        when(service.update(ID,addressSaveRequest)).thenReturn(addressSaveResponse);
        when(mapper.map(any(), any())).thenReturn(addressSaveResponse);

        ResponseEntity<AddressSaveResponse> response = controller.updateAddress(ID,addressSaveRequest);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(AddressSaveResponse.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(LOGRADOURO, response.getBody().getLogradouro());
        assertEquals(NUMBER, response.getBody().getNumber());
        assertEquals(BAIRRO, response.getBody().getBairro());
        assertEquals(LOCALIDADE, response.getBody().getLocalidade());
        assertEquals(CEP, response.getBody().getCep());
        assertEquals(UF, response.getBody().getUf());
        assertEquals(IS_PRINCIPAL_ADDRESS, response.getBody().isPrincipalAddress());

    }


    @Test
    void whenDeleteThenReturnSucess() {
        doNothing().when(service).deleteById(anyLong());

        ResponseEntity<AddressSaveResponse> response = controller.deleteAddress(ID);
        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).deleteById(anyLong());
    }


    private void startAddressControler() {
        address = new Address(ID, LOGRADOURO, NUMBER, BAIRRO, LOCALIDADE, CEP, UF, IS_PRINCIPAL_ADDRESS);
        addressSaveResponseOptional = Optional.of(new AddressSaveResponse(ID, LOGRADOURO, NUMBER, BAIRRO, LOCALIDADE, CEP, UF, IS_PRINCIPAL_ADDRESS));
        addressSaveRequest = new AddressSaveRequest(ID, LOGRADOURO, NUMBER, BAIRRO, LOCALIDADE, CEP, UF, IS_PRINCIPAL_ADDRESS);
        addressSaveResponse = new AddressSaveResponse(ID, LOGRADOURO, NUMBER, BAIRRO, LOCALIDADE, CEP, UF, IS_PRINCIPAL_ADDRESS);


        addressPageResponse = new Page<AddressSaveResponse>() {
            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public <U> Page<U> map(Function<? super AddressSaveResponse, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return 0;
            }

            @Override
            public int getNumberOfElements() {
                return 0;
            }

            @Override
            public List<AddressSaveResponse> getContent() {
                return null;
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Iterator<AddressSaveResponse> iterator() {
                return null;
            }
        };
    }
}