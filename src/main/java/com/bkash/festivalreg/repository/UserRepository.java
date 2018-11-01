package com.bkash.festivalreg.repository;

import com.bkash.festivalreg.domain.security.FolkFestAppUser;
import org.springframework.data.jpa.repository.JpaRepository;



/**
 * @author ekansh
 * @since 2/4/16
 */
public interface UserRepository extends JpaRepository<FolkFestAppUser,Long> {

    FolkFestAppUser findByUsername(String username);
    FolkFestAppUser findById(long id);
    
}
