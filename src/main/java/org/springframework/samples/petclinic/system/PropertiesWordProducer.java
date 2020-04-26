package org.springframework.samples.petclinic.system;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component("propertiesWordProducer")
@Profile("configurable")
public class PropertiesWordProducer implements WordProducer {
    @Value("${say.word}")
    String word;

    @Override
    public String produce() {
        return word;
    }
}
