package com.acme.bank.loan.service.service;

import com.acme.bank.loan.domain.event.RegisterLoanEvent;
import com.acme.bank.loan.service.rule.Rule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleService.class);
    private final List<Rule> rules;

    @Autowired
    public RuleService(List<Rule> rules) {
        this.rules = rules;
    }

    public boolean evaluate(final RegisterLoanEvent event) {
        return rules.stream()
                .allMatch(rule -> evaluate(rule, event));
    }

    private boolean evaluate(final Rule rule, final RegisterLoanEvent event) {
        boolean outcome = rule.evaluate(event);
        LOGGER.debug("Rule '{}' evaluated to '{}'", rule.getClass().getSimpleName(), outcome ? "PASS" : "FAIL");
        return outcome;
    }
}
