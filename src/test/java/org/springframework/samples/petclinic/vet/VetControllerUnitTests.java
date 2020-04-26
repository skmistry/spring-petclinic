package org.springframework.samples.petclinic.vet;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.MockitoAnnotations.initMocks;

public class VetControllerUnitTests {
    @Mock
    VetRepository vetRepository;

    @InjectMocks
    VetController vetController;

    @Mock
    Map<String, Object> mockModel;

    Vets expectedVets;

    @BeforeEach
    void setUp() {
        initMocks(this);

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
    void testShowVetList() {
        // when & then
        assertThat(vetController.showVetList(mockModel)).isEqualTo("vets/vetList");

        then(vetRepository).should().findAll();
        then(mockModel).should().put(eq("vets"), any(Vets.class));
    }

    @Test
    void testShowResourcesVetList() {
        // when & then
        assertThat(vetController.showResourcesVetList()).isEqualTo(expectedVets);

        then(vetRepository).should().findAll();
    }
}
