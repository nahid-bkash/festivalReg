package com.bkash.festivalreg.repository;

import com.bkash.festivalreg.domain.security.User;
import org.springframework.data.jpa.repository.JpaRepository;



/**
 * @author ekansh
 * @since 2/4/16
 */
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);
    User findById(long id);
    
}
