package io.github.CarolinaCedro.POC01.application.dto.request;

import io.github.CarolinaCedro.POC01.domain.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAddressPrincipalUpdate {

    private Long id;
    private Long addressPrincipal;
}
