package org.springframework.samples.petclinic.system.java.based.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.system.WordListener;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(YanniLaurelProfileBasedTest.class)
@ActiveProfiles(YanniLaurelProfileBasedTest.ACTIVE_PROFILE)
@ComponentScan("org.springframework.samples.petclinic")
public class YanniLaurelProfileBasedTest {
    static final String ACTIVE_PROFILE = "Yanni";
    // uncomment below to activate laurel word producer to produce the word "Laurel"
    // static final String ACTIVE_PROFILE = "Laurel";

    @Autowired
    WordListener wordListener;

    @Test
    void testListenYanniOrLaurelBasedOnActiveProfile() {
        final String word = wordListener.sayItLoud();
        assertThat(word).isEqualTo(ACTIVE_PROFILE);
    }
}
