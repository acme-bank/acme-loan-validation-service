package com.acme.bank.loan.validation.service.rule;

import org.springframework.stereotype.Component;

import com.acme.bank.loan.validation.domain.event.RegisterLoanEvent;

@Component
public class LoanAmountIsNotTooLargeRule implements Rule {

    private static final double MAX_LOAN_AMOUNT = 10000000;

    @Override
    public boolean evaluate(final RegisterLoanEvent event) {
        return event != null && event.getAmount() < MAX_LOAN_AMOUNT;
    }
}
