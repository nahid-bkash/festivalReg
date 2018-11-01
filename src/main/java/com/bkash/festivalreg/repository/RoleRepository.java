package com.bkash.festivalreg.repository;

import com.bkash.festivalreg.domain.security.FolkFestAppRole;
import com.bkash.festivalreg.domain.security.FolkFestAppUser;
import org.springframework.data.jpa.repository.JpaRepository;



/**
 * @author ekansh
 * @since 2/4/16
 */
public interface RoleRepository extends JpaRepository<FolkFestAppRole,Long> {

    FolkFestAppUser findByRole(String role);
    FolkFestAppRole findById(long id);
}
