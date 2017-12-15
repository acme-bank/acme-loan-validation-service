package com.acme.bank.loan.validation.service.rule;

import com.acme.bank.loan.validation.domain.event.RegisterLoanEvent;

public interface Rule {

    boolean evaluate(RegisterLoanEvent event);
}
