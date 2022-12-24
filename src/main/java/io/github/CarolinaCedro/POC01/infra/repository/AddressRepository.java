package io.github.CarolinaCedro.POC01.infra.repository;


import io.github.CarolinaCedro.POC01.domain.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
