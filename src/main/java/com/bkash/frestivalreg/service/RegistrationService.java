package com.bkash.frestivalreg.service;

import com.bkash.frestivalreg.domain.Registration;

public interface RegistrationService {

    Registration getRegistrationByAccountNumber(String accountNumber);

    void saveRegistration(Registration registration);

}
