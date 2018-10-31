package com.bkash.festivalreg.service;

import com.bkash.festivalreg.domain.Registration;

public interface RegistrationService {

    Registration getRegistrationByAccountNumber(String accountNumber);

    void saveRegistration(Registration registration);

}
