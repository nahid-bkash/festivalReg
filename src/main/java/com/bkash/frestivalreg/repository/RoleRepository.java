package com.bkash.frestivalreg.repository;

import com.bkash.frestivalreg.domain.security.Role;
import com.bkash.frestivalreg.domain.security.User;
import org.springframework.data.jpa.repository.JpaRepository;



/**
 * @author ekansh
 * @since 2/4/16
 */
public interface RoleRepository extends JpaRepository<Role,Long> {

    User findByRole(String role);
    Role findById(long id);
}
