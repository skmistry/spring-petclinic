package org.springframework.samples.petclinic.system.java.based.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.system.WordListener;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(YanniWordProducerConfig.class)
class YanniWordListenerTest {
    @Autowired
    private WordListener wordListener;

    @Test
    void heardYanniTest() {
        assertThat(wordListener.sayItLoud()).isEqualTo("Yanni");
    }
}
