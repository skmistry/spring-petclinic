package org.springframework.samples.petclinic.system;

import org.springframework.stereotype.Component;

@Component("laurelWordProducer")
public class LaurelWordProducer implements WordProducer {
    @Override
    public String produce() {
        return "Laurel";
    }
}
