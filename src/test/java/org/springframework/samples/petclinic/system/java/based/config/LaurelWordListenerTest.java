package org.springframework.samples.petclinic.system.java.based.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.system.WordListener;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(LaurelWordProducerConfig.class)
public class LaurelWordListenerTest {
    @Autowired
    WordListener wordListener;

    @Test
    void heardLaurelTest() {
        assertThat(wordListener.sayItLoud()).isEqualTo("Laurel");
    }
}
