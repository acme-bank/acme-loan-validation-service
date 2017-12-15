package com.acme.bank.loan.validation.service.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({
        "com.acme.bank.loan.validation.service"
})
@Configurable
public class ServiceAutoConfig {

}
