package io.github.CarolinaCedro.POC01.application.util.address;

import io.github.CarolinaCedro.POC01.domain.entities.Address;
import io.github.CarolinaCedro.POC01.domain.entities.Customer;
import io.github.CarolinaCedro.POC01.domain.enums.PjOrPf;
import io.github.CarolinaCedro.POC01.infra.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class CustomerCreator {

    public static Address address3 = new Address(1L,"Rua das moscas","45","bairro da paz","Santa helena","75920000","Go",false);


    public static Customer createCustomerToBeSaved(){
        Address address = new Address(1L,"Rua das moscas","45","bairro da paz","Santa helena","75920000","Go",false);


        return Customer.builder()
                .id(1L)
                .version(1)
                .email("carolcedro@email.com")
                .addressPrincipal(address)
                .address(List.of(address))
                .phone("68993456798")
                .cpfOrCnpj("706.008.490-82")
                .pjOrPf(PjOrPf.PF)
                .build();
    }

//    public static Customer createValidCustomer(){
//        return Customer.builder()
//                .id(1L)
//                .version(1)
//                .email("carolcedro@email.com")
//                .addressPrincipal(address1)
//                .address(addressList)
//                .phone("68993456798")
//                .cpfOrCnpj("706.008.490-82")
//                .pjOrPf(PjOrPf.PF)
//                .build();
//    }
//
//    public static Customer createValidUpdatedCustomer(){
//        return Customer.builder()
//                .id(1L)
//                .version(1)
//                .email("carolcedro@email.com")
//                .addressPrincipal(address1)
//                .address(addressList)
//                .phone("68993456798")
//                .cpfOrCnpj("706.008.490-82")
//                .pjOrPf(PjOrPf.PF)
//                .build();
//    }


}
