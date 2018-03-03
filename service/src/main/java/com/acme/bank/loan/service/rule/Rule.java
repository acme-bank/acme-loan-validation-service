package com.acme.bank.loan.service.rule;

import com.acme.bank.loan.domain.event.RegisterLoanEvent;

public interface Rule {

    boolean evaluate(RegisterLoanEvent event);
}
