package com.bkash.frestivalreg.service;

import com.bkash.frestivalreg.domain.Registration;
import com.bkash.frestivalreg.repository.RegistrationRepository;
import com.bkash.frestivalreg.service.RegistrationService;
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
