package com.acme.bank.loan.service.rule;

import com.acme.bank.loan.domain.event.RegisterLoanEvent;
import org.springframework.stereotype.Component;

@Component
public class LoanAmountRule implements Rule {

    private static final double MIN_LOAN_AMOUNT = 1000;
    private static final double MAX_LOAN_AMOUNT = 10000000;

    @Override
    public boolean evaluate(final RegisterLoanEvent event) {
        return event != null &&
                event.getAmount() >= MIN_LOAN_AMOUNT &&
                event.getAmount() <= MAX_LOAN_AMOUNT;
    }
}
