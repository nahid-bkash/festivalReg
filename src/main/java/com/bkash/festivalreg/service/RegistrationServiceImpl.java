package com.bkash.festivalreg.service;

import com.bkash.festivalreg.domain.Registration;
import com.bkash.festivalreg.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Override
    public Registration getRegistrationByAccountNumber(String accountNumber) {
        return this.registrationRepository.findRegistratoinByAccountNumber(accountNumber);
    }

    @Override
    public void saveRegistration(Registration registration) {
        this.registrationRepository.save(registration);
    }
}
