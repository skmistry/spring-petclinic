package org.springframework.samples.petclinic.vet;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class VetControllerMockMvcStandaloneUnitTests {
    @Mock
    VetRepository vetRepository;

    @InjectMocks
    VetController vetController;

    MockMvc mockMvc;

    Vets expectedVets;

    @BeforeEach
    void setUp() {
        initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(vetController).build();

        Vet saurabh = createVet(100, "Saurabh", "Mistry");
        Vet chaitra = createVet(101, "Chaitra", "Rao");

        ArrayList<Vet> expectedVetList = Lists.newArrayList(saurabh, chaitra);
        expectedVets = new Vets();
        expectedVets.getVetList().addAll(expectedVetList);

        given(vetRepository.findAll()).willReturn(expectedVetList);
    }

    private Vet createVet(int id, String firstName, String lastName) {
        Vet vet = new Vet();

        vet.setFirstName(firstName);
        vet.setLastName(lastName);
        vet.setId(id);

        return vet;
    }

    @Test
    void testShowVetList() throws Exception {
        mockMvc.perform(get("/vets.html"))
               .andExpect(status().isOk())
               .andExpect(view().name("vets/vetList"))
               .andExpect(model().attributeExists("vets"));
    }

    @Test
    void testShowResourcesVetList() throws Exception {
        mockMvc.perform(get("/vets").accept(APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentType(APPLICATION_JSON))
               .andExpect(jsonPath("$.vetList[0].id").value(100))
               .andExpect(jsonPath("$.vetList[1].id").value(101));
    }
}
