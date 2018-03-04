package com.acme.bank.loan.domain.converter;

import java.time.ZonedDateTime;

import com.acme.bank.loan.domain.event.RegisterLoanEvent;
import com.acme.bank.loan.domain.event.RejectLoanEvent;
import org.springframework.stereotype.Component;

@Component
public class RegisterLoanEventToRejectLoanEventConverter extends AbstractConverter<RegisterLoanEvent, RejectLoanEvent> {

    @Override
    public RejectLoanEvent convert(RegisterLoanEvent registerLoanEvent) {
        RejectLoanEvent rejectLoanEvent = new RejectLoanEvent();
        rejectLoanEvent.setEventId(registerLoanEvent.getEventId());
        rejectLoanEvent.setRejectedTimestamp(ZonedDateTime.now());
        return rejectLoanEvent;
    }
}
