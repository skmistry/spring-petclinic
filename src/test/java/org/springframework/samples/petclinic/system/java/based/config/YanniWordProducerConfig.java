package org.springframework.samples.petclinic.system.java.based.config;

import org.springframework.context.annotation.Bean;
import org.springframework.samples.petclinic.system.WordProducer;
import org.springframework.samples.petclinic.system.YanniWordProducer;

class YanniWordProducerConfig implements IWordProducerConfig {
    @Bean
    public WordProducer wordProducer() {
        return new YanniWordProducer();
    }
}
