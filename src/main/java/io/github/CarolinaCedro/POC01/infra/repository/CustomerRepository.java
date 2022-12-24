package io.github.CarolinaCedro.POC01.infra.repository;


import io.github.CarolinaCedro.POC01.application.dto.response.CustomerSaveResponse;
import io.github.CarolinaCedro.POC01.domain.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(" select e from Customer e" +
            " where upper( e.email ) like upper( :email )")
    List<Customer> findByEmailIsLikeIgnoreCase(@Param("email") String email);

}
