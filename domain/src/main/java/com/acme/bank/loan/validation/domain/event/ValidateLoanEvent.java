package com.acme.bank.loan.validation.domain.event;

import java.time.ZonedDateTime;
import java.util.UUID;

public class ValidateLoanEvent {

    private UUID uuid;
    private ZonedDateTime validatedTimestamp;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public ZonedDateTime getValidatedTimestamp() {
        return validatedTimestamp;
    }

    public void setValidatedTimestamp(ZonedDateTime validatedTimestamp) {
        this.validatedTimestamp = validatedTimestamp;
    }
}
