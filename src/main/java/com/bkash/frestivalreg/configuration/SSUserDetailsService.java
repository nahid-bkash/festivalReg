package com.bkash.frestivalreg.configuration;



import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import com.bkash.frestivalreg.domain.security.Role;
import com.bkash.frestivalreg.domain.security.User;
import com.bkash.frestivalreg.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;




/**
 * @author ekansh
 * @since 2/4/16
 */
@Transactional
public class SSUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SSUserDetailsService.class);

    private UserRepository userRepository;

    public SSUserDetailsService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByUsername(username);
            if (user == null) {
                LOGGER.info("FolkFestApp::SSUserDetailsService::loadUserByUsername::user not found with the provided username :"+username);
                return null;
            }
            LOGGER.info("FolkFestApp::SSUserDetailsService::loadUserByUsername::data loaded  db for user " + user.getUsername());
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user));
        }
        catch (Exception e){
            throw new UsernameNotFoundException("User not found");
        }
    }

    private Set<GrantedAuthority> getAuthorities(User user){
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        for(Role role : user.getRoles()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRole());
            authorities.add(grantedAuthority);
        }
        LOGGER.info("FolkFestApp::SSUserDetailsService::getAuthorities:: user authorities are " + authorities.toString());
        return authorities;
    }


}
