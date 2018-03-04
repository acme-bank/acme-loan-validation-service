package com.acme.bank.loan.domain.converter;

import com.acme.bank.loan.domain.event.RegisterLoanEvent;
import com.acme.bank.loan.domain.event.ValidateLoanEvent;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class RegisterLoanEventToValidateLoanEventConverter extends AbstractConverter<RegisterLoanEvent, ValidateLoanEvent> {

    @Override
    public ValidateLoanEvent convert(RegisterLoanEvent registerLoanEvent) {
        ValidateLoanEvent validateLoanEvent = new ValidateLoanEvent();
        validateLoanEvent.setEventId(registerLoanEvent.getEventId());
        validateLoanEvent.setPersonId(registerLoanEvent.getPersonId());
        validateLoanEvent.setValidatedTimestamp(ZonedDateTime.now());
        return validateLoanEvent;
    }
}
