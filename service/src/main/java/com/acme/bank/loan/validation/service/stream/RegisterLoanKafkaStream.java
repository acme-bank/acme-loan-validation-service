package com.acme.bank.loan.validation.service.stream;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import java.util.Properties;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import com.acme.bank.loan.validation.domain.config.AcmeProperties;
import com.acme.bank.loan.validation.domain.event.RegisterLoanEvent;
import com.acme.bank.loan.validation.domain.event.RejectLoanEvent;
import com.acme.bank.loan.validation.domain.event.ValidateLoanEvent;
import com.acme.bank.loan.validation.service.helper.KafkaHelper;
import com.acme.bank.loan.validation.service.rule.RuleEngine;

@SuppressWarnings({"Duplicates", "unchecked", "unused"})
@Component
public class RegisterLoanKafkaStream {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterLoanKafkaStream.class);

    private String applicationName;
    private final AcmeProperties acmeProperties;
    private KafkaStreams streams;
    private final KafkaHelper kafkaHelper;
    private final ConversionService conversionService;
    private final RuleEngine ruleEngine;

    @Autowired
    public RegisterLoanKafkaStream(@Value("${spring.application.name}") String applicationName,
                                   final AcmeProperties acmeProperties,
                                   final KafkaHelper kafkaHelper,
                                   final ConversionService conversionService,
                                   final RuleEngine ruleEngine) {
        this.applicationName = applicationName;
        this.acmeProperties = acmeProperties;
        this.kafkaHelper = kafkaHelper;
        this.conversionService = conversionService;
        this.ruleEngine = ruleEngine;
    }

    @PostConstruct
    public void startStream() {
        AcmeProperties.Kafka.Topics topics = acmeProperties.getKafka().getTopics();

        StreamsBuilder streamBuilder = new StreamsBuilder();
        KStream<String, RegisterLoanEvent>[] streamBranch = streamBuilder
                .stream(topics.getRejectLoan(), kafkaHelper.cosumedWith(RegisterLoanEvent.class))
                .branch(this::validateClaim, this::rejectClaim);

        send(streamBranch[0], ValidateLoanEvent.class, topics.getValidateLoan());
        send(streamBranch[1], RejectLoanEvent.class, topics.getRejectLoan());

        streams = new KafkaStreams(streamBuilder.build(), properties());
        streams.start();
    }

    private boolean validateClaim(String key, RegisterLoanEvent event) {
        AcmeProperties.Kafka.Topics topics = acmeProperties.getKafka().getTopics();

        LOGGER.info("Received event with key {} on topic {}", key, topics.getRegisterLoan());

        return ruleEngine.evaluate(event);
    }

    private boolean rejectClaim(String key, RegisterLoanEvent event) { // NOSONAR
        return Boolean.TRUE; // Always reject if validation fails
    }

    private <T> void send(KStream<String, RegisterLoanEvent> stream, Class<T> clazz, String targetTopic) {
        stream.map((key, event) -> convert(key, event, clazz, targetTopic))
                .to(targetTopic, kafkaHelper.producedWith(clazz));
    }

    private <T> KeyValue<String, T> convert(String key, RegisterLoanEvent event, Class<T> clazz, String targetTopic) {
        LOGGER.info("Sending event with key {} to topic {}", key, targetTopic);

        return new KeyValue<>(key, conversionService.convert(event, clazz));
    }

    private Properties properties() {
        AcmeProperties.Kafka.Topics topics = acmeProperties.getKafka().getTopics();
        return kafkaHelper.properties(applicationName.concat("-").concat(topics.getRejectLoan()));
    }

    @PreDestroy
    public void closeStream() {
        if (streams != null) {
            streams.close();
        }
    }
}
