package com.acme.bank.loan.validation.domain.event;

import java.time.ZonedDateTime;
import java.util.UUID;

@SuppressWarnings({"unused"})
public class RegisterLoanEvent {

    private UUID uuid;
    private String personalId;
    private ZonedDateTime registeredTimestamp;
    private double amount;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public ZonedDateTime getRegisteredTimestamp() {
        return registeredTimestamp;
    }

    public void setRegisteredTimestamp(ZonedDateTime registeredTimestamp) {
        this.registeredTimestamp = registeredTimestamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
