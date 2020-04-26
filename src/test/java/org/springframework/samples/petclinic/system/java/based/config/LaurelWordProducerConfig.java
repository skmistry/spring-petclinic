package org.springframework.samples.petclinic.system.java.based.config;

import org.springframework.context.annotation.Bean;
import org.springframework.samples.petclinic.system.LaurelWordProducer;
import org.springframework.samples.petclinic.system.WordProducer;

class LaurelWordProducerConfig implements ITestBaseConfig {
    @Bean
    public WordProducer wordProducer() {
        return new LaurelWordProducer();
    }
}
