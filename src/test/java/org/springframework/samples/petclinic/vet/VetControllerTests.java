/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.vet;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the {@link VetController}
 */
@WebMvcTest(VetController.class)
class VetControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VetRepository vets;

    @BeforeEach
    void setup() {
        Vet james = createVet(1, "James", "Carter");
        Vet helen = createVet(2, "Helen", "Leary");

        Specialty radiology = createSpeciality(1, "radiology");
        helen.addSpecialty(radiology);

        ArrayList<Vet> vetList = Lists.newArrayList(james, helen);
        given(this.vets.findAll()).willReturn(vetList);
    }

    private Vet createVet(int id, String firstName, String lastName) {
        Vet james = new Vet();
        james.setFirstName(firstName);
        james.setLastName(lastName);
        james.setId(id);
        return james;
    }

    private Specialty createSpeciality(int id, String speciality) {
        Specialty specialty = new Specialty();

        specialty.setId(id);
        specialty.setName(speciality);

        return specialty;
    }

    @Test
    void testShowVetListHtml() throws Exception {
        mockMvc.perform(get("/vets.html"))
               .andExpect(status().isOk())
               .andExpect(model().attributeExists("vets"))
               .andExpect(view().name("vets/vetList"));
    }

    @Test
    void testShowResourcesVetList() throws Exception {
        mockMvc.perform(get("/vets").accept(APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentType(APPLICATION_JSON))
               .andExpect(jsonPath("$.vetList[0].id").value(1));
    }
}
