package com.bkash.frestivalreg.repository;

import com.bkash.frestivalreg.domain.Registration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends CrudRepository<Registration, Integer> {

    @Query("select d from Registration d where trim(d.accountNumber) like :accountNumber")
    Registration findRegistratoinByAccountNumber(@Param("accountNumber") String accountNumber);

}
