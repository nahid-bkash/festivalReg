package com.bkash.frestivalreg.bootstrap;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.bkash.frestivalreg.domain.Registration;
import com.bkash.frestivalreg.domain.security.Role;
import com.bkash.frestivalreg.domain.security.User;
import com.bkash.frestivalreg.repository.RegistrationRepository;
import com.bkash.frestivalreg.repository.RoleRepository;
import com.bkash.frestivalreg.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;



@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {


	private UserRepository userRepository;

	private RoleRepository roleRepository;

	@Autowired
	private RegistrationRepository registrationRepository;



	private Logger log = Logger.getLogger(DataLoader.class);

	@Autowired
	private Environment environment;



	@Autowired
	public void setProductRepository(
			UserRepository userRepository, RoleRepository roleRepository) {


		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		//if (Arrays.stream(environment.getActiveProfiles()).anyMatch(env -> (env.equalsIgnoreCase("dev")))) {

			addSecurityIntitalData();

			addRestrationData();

		//}
	}

	private void addRestrationData() {

		Registration testData1 = new Registration();
		testData1.setId(1L);
		testData1.setAccountBirthDate(new Date());
		testData1.setAccountFatherName("Late. Sakhawat Hossain");
		testData1.setAccountMotherName("Late. Jahanara Hossain ");
		testData1.setAccountFirstName("Nahid");
		testData1.setAccountLastName("Hossain");
		testData1.setDetailsOfOccupation("Test Details of occupation");
		testData1.setAccountNumber("01733400896");
		testData1.setEstimatedMonthlyIncome(50000.00);
		testData1.setPresentAddress("test present adddress");
		testData1.setPermanentAddress("test permanent address");
		testData1.setAccountHasbandWifeName("Husband or Wife Name 1");
		testData1.setIdNumber("ID8762334234234234");
		testData1.setIdType("NID");
		testData1.setGender("Male");
		testData1.setSourceOfFund("Test Source of fund");
		this.registrationRepository.save(testData1);

		Registration testData2 = new Registration();
		testData2.setId(2L);
		testData2.setAccountBirthDate(new Date());
		testData2.setAccountFatherName("Kawser's father name");
		testData2.setAccountMotherName("Kawser's mother name");
		testData2.setAccountFirstName("Kawser");
		testData2.setAccountLastName("Ahmed");
		testData2.setDetailsOfOccupation("Test Details of occupation");
		testData2.setAccountNumber("01733400897");
		testData2.setEstimatedMonthlyIncome(40000.00);
        testData1.setAccountHasbandWifeName("Husband or Wife Name 2");
		testData2.setPresentAddress("test present adddress");
		testData2.setPermanentAddress("test permanent address");
		testData2.setIdNumber("ID8762334234555555");
		testData2.setIdType("DRIVING LICENCSE");
		testData2.setGender("Male");
		testData2.setSourceOfFund("Test Source of fund 2");
		this.registrationRepository.save(testData2);


	}

	private void addSecurityIntitalData() {

		Role roleAdmin = new Role();
		roleAdmin.setRole("ADMIN");

		Role rolePharma = new Role();
		rolePharma.setRole("USER");


		// adding admin user
		Set<Role> setAdminRole = new HashSet<Role>();
		setAdminRole.add(roleAdmin);
		// setAdminRole.add(roleManager);

		User admin = new User();

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode("admin");
		admin.setPassword(hashedPassword);
		admin.setUsername("admin");
		admin.setRoles(setAdminRole);
		userRepository.save(admin);

	}


}
