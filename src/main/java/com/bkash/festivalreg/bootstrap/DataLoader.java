package com.bkash.festivalreg.bootstrap;

import java.util.*;

import com.bkash.festivalreg.domain.Registration;
import com.bkash.festivalreg.domain.security.FolkFestAppRole;
import com.bkash.festivalreg.domain.security.FolkFestAppUser;
import com.bkash.festivalreg.repository.RegistrationRepository;
import com.bkash.festivalreg.repository.RoleRepository;
import com.bkash.festivalreg.repository.UserRepository;
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

		if (Arrays.stream(environment.getActiveProfiles()).anyMatch(env -> (env.equalsIgnoreCase("dev")))) {

			addSecurityIntitalData();

			addRestrationData();

		}
	}

	private void addRestrationData() {

		Registration testData1 = new Registration();
	//	testData1.setId(1L);
		testData1.setAccountBirthDate(new Date());
		testData1.setAccountFatherName("Late. Sakhawat Hossain");
		testData1.setAccountMotherName("Late. Jahanara Hossain ");
		testData1.setAccountFirstName("Nahid");
		testData1.setAccountLastName("Hossain");
		testData1.setDetailsOfOccupation("Test Details of occupation");
		testData1.setAccountNumber("01733400890");
		testData1.setEstimatedMonthlyIncome(Double.valueOf(50000));
		testData1.setPresentAddress("test present adddress");
		testData1.setPermanentAddress("test permanent address");
		testData1.setAccountHasbandWifeName("Husband or Wife Name 1");
		testData1.setIdNumber("ID8762334234234234");
		testData1.setIdType("NID");
		testData1.setGender("Male");
		testData1.setSourceOfFund("Test Source of fund");
		this.registrationRepository.save(testData1);

		Registration testData2 = new Registration();
		//testData2.setId(2L);
		testData2.setAccountBirthDate(new Date());
		testData2.setAccountFatherName("Kawser's father name");
		testData2.setAccountMotherName("Kawser's mother name");
		testData2.setAccountFirstName("Kawser");
		testData2.setAccountLastName("Ahmed");
		testData2.setDetailsOfOccupation("Test Details of occupation");
		testData2.setAccountNumber("01733400897");
		testData2.setEstimatedMonthlyIncome(Double.valueOf(40000));
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

		FolkFestAppRole roleAdmin = new FolkFestAppRole();
		roleAdmin.setRole("ADMIN");

		FolkFestAppRole roleUser = new FolkFestAppRole();
		roleUser.setRole("USER");


		// adding admin user
		Set<FolkFestAppRole> setAdminRole = new HashSet<FolkFestAppRole>();
		setAdminRole.add(roleAdmin);
		//setAdminRole.add(roleUser);

		FolkFestAppUser admin = new FolkFestAppUser();

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode("admin4321");
		admin.setPassword(hashedPassword);
		admin.setUsername("admin");
		admin.setRoles(setAdminRole);
		userRepository.save(admin);

        System.out.println("############admin passs"+hashedPassword);

		Set<FolkFestAppRole> setUserRole = new HashSet<FolkFestAppRole>();
		setUserRole.add(roleUser);

    //   for(int a=1;a <=10;a++)
	//   {
		   FolkFestAppUser user = new FolkFestAppUser();


		Random rand = new Random();

		int  n = rand.nextInt(5676) + 1;

		String userPass ="pass"+n;

		hashedPassword = passwordEncoder.encode(userPass);


		   user.setPassword(hashedPassword);

		   String username ="user"+2;
		   user.setUsername(username);
		   user.setRoles(setUserRole);
		   userRepository.save(user);

		   System.out.println("###########user name:"+username+"############user passs (without) "+"pass####"+userPass+"############user passs "+hashedPassword);
	 //  }



/*
		hashedPassword = passwordEncoder.encode("user4531");
		admin.setPassword(hashedPassword);
		admin.setUsername("user");
		admin.setRoles(setAdminRole);
		userRepository.save(admin);

		System.out.println("############user passs"+hashedPassword);

*/
	}


}
