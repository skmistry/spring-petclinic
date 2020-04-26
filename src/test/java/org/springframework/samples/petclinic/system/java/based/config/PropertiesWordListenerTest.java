package org.springframework.samples.petclinic.system.java.based.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.system.WordListener;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(PropertiesWordListenerTest.class)
@ActiveProfiles("configurable")
@ComponentScan("org.springframework.samples.petclinic")
@TestPropertySource({"classpath:application.properties",
                     "classpath:application-configurable.properties"})
@Slf4j
public class PropertiesWordListenerTest {
    @Autowired
    WordListener wordListener;

    @Test
    void testListenYanniOrLaurelBasedOnActiveProfile() {
        final String word = wordListener.sayItLoud();
        assertThat(word).isEqualTo("YANNI");
    }
}
