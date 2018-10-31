package com.bkash.frestivalreg.configuration;

import com.bkash.frestivalreg.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



/**
 * @author ekansh
 * @since 30/3/16
 */
@Configuration
@EnableWebSecurity

public class SpringSecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserRepository userRepository;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsServiceBean()).passwordEncoder(passwordEncoder());
	}

	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return new SSUserDetailsService(userRepository);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {


		http.authorizeRequests().antMatchers("/index/image/*").permitAll().
		        and().formLogin().loginPage("/login").permitAll().	   
		        and().authorizeRequests().antMatchers("/**/favicon.ico") .permitAll()           
                .and().authorizeRequests().antMatchers("/webjars/**").permitAll()
                .and().authorizeRequests().antMatchers("/static/css").permitAll()
                .and().authorizeRequests().antMatchers("/content/**").permitAll()
				.and().authorizeRequests().antMatchers("/warning/**").permitAll()
				.and().authorizeRequests().antMatchers("/console/**").permitAll()
				.and().authorizeRequests().antMatchers("/images/**").permitAll() //TODO: security check
				.and().authorizeRequests().antMatchers("/**").hasAnyAuthority("ADMIN","USER")
		        .and().authorizeRequests().antMatchers("/admin/**").hasAnyAuthority("ADMIN")
                .and().authorizeRequests().antMatchers("/js").permitAll()
				 .and().authorizeRequests().anyRequest().authenticated().
				and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/");


		http.csrf().disable();
		http.headers().frameOptions().disable();


	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	

}