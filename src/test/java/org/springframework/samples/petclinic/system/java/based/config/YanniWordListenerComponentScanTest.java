package org.springframework.samples.petclinic.system.java.based.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.system.WordListener;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(value = YanniWordListenerComponentScanTest.class)
@ComponentScan(basePackages = "org.springframework.samples.petclinic.system")
public class YanniWordListenerComponentScanTest {
    @Autowired
    WordListener wordListener;

    @Test
    void testListenLaurelWord() {
        final String word = wordListener.sayItLoud();
        assertThat(word).isEqualTo("Yanni");
    }
}
