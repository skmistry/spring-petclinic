package org.springframework.samples.petclinic.system;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class WordListener {
    private WordProducer wordProducer;

    public String sayItLoud() {
        String word = wordProducer.produce();
        log.info("What I heard is [" + word + "]");
        return word;
    }
}
