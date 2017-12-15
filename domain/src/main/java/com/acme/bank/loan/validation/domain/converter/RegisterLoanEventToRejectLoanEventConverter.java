package com.acme.bank.loan.validation.domain.converter;

import java.time.ZonedDateTime;

import org.springframework.stereotype.Component;

import com.acme.bank.loan.validation.domain.event.RegisterLoanEvent;
import com.acme.bank.loan.validation.domain.event.RejectLoanEvent;

@Component
public class RegisterLoanEventToRejectLoanEventConverter extends AbstractConverter<RegisterLoanEvent, RejectLoanEvent> {

    @Override
    public RejectLoanEvent convert(RegisterLoanEvent registerLoanEvent) {
        RejectLoanEvent rejectLoanEvent = new RejectLoanEvent();
        rejectLoanEvent.setUuid(registerLoanEvent.getUuid());
        rejectLoanEvent.setRejectedTimestamp(ZonedDateTime.now());
        return rejectLoanEvent;
    }
}
