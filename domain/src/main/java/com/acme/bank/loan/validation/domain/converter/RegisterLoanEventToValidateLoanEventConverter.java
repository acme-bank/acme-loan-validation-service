package com.acme.bank.loan.validation.domain.converter;

import java.time.ZonedDateTime;

import org.springframework.stereotype.Component;

import com.acme.bank.loan.validation.domain.event.RegisterLoanEvent;
import com.acme.bank.loan.validation.domain.event.ValidateLoanEvent;

@Component
public class RegisterLoanEventToValidateLoanEventConverter extends AbstractConverter<RegisterLoanEvent, ValidateLoanEvent> {

    @Override
    public ValidateLoanEvent convert(RegisterLoanEvent registerLoanEvent) {
        ValidateLoanEvent validateLoanEvent = new ValidateLoanEvent();
        validateLoanEvent.setUuid(registerLoanEvent.getUuid());
        validateLoanEvent.setValidatedTimestamp(ZonedDateTime.now());
        return validateLoanEvent;
    }
}
