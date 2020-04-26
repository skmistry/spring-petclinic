package org.springframework.samples.petclinic.system;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component("yanniWordProducer")
@Primary
public class YanniWordProducer implements WordProducer {
    @Override
    public String produce() {
        return "Yanni";
    }
}
