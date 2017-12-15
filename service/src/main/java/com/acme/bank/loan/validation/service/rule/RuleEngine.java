package com.acme.bank.loan.validation.service.rule;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.acme.bank.loan.validation.domain.event.RegisterLoanEvent;

@Component
public class RuleEngine {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuleEngine.class);
    private final List<Rule> rules;

    @Autowired
    public RuleEngine(List<Rule> rules) {
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
