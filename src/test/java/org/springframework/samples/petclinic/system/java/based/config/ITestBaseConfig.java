package org.springframework.samples.petclinic.system.java.based.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.samples.petclinic.system.WordListener;
import org.springframework.samples.petclinic.system.WordProducer;

@Configuration
interface ITestBaseConfig {
    @Bean
    default WordListener hearer(WordProducer wordProducer){
        return new WordListener(wordProducer);
    }

    @Bean
    WordProducer wordProducer();
}
