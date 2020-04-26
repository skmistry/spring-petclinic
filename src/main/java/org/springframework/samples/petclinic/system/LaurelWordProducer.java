package org.springframework.samples.petclinic.system;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component("laurelWordProducer")
@Profile("Laurel")
public class LaurelWordProducer implements WordProducer {
    @Override
    public String produce() {
        return "Laurel";
    }
}
