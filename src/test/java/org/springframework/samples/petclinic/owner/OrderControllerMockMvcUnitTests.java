package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.util.Lists.emptyList;
import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class OrderControllerMockMvcUnitTests {
    private static final int TEST_OWNER_ID = 100;
    private static final String GET_OWNERS_URI = "/owners";

    @InjectMocks
    OwnerController ownerController;

    @Mock
    OwnerRepository ownerRepository;

    MockMvc mockMvc;

    Owner geoege;
    Owner benjamin;

    @BeforeEach
    void setUp() {
        initMocks(this);
        mockMvc = standaloneSetup(ownerController).build();

        geoege = createOwner("George", "Franklin", "110 W. Liberty St.",
                             "Madison", "6085551023");
        benjamin = createOwner("Benjamin", "Gullet", "210 E. Bullevad St.",
                               "New York", "6075551099");
    }

    private static Owner createOwner(String firstName, String lastName,
                                     String addr, String city, String telephone) {
        Owner owner = new Owner();

        owner.setId(TEST_OWNER_ID);
        owner.setFirstName(firstName);
        owner.setLastName(lastName);
        owner.setAddress(addr);
        owner.setCity(city);
        owner.setTelephone(telephone);

        return owner;
    }

    @Test
    void processFindFormWithoutLastNameCriteria() throws Exception {
        // given
        List<Owner> ownerList = newArrayList(geoege, benjamin);
        given(ownerRepository.findByLastName("")).willReturn(ownerList);

        // when & then
        mockMvc.perform(get(GET_OWNERS_URI))
               .andExpect(status().isOk())
               .andExpect(model().attribute("selections", ownerList))
               .andExpect(view().name("owners/ownersList"));
    }

    @Test
    void processFindFormWithLastNameMatchingTheOwner() throws Exception {
        // given
        List<Owner> ownerList = newArrayList(geoege);
        String franklin = "Franklin";
        given(ownerRepository.findByLastName(franklin)).willReturn(ownerList);

        // when & then
        mockMvc.perform(get(GET_OWNERS_URI).param("lastName", franklin))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name("redirect:/owners/" + TEST_OWNER_ID));
    }

    @Test
    void processFindFormWithLastNameNotMatchingAnyOwner() throws Exception {
        // given
        String nonExistingOwner = "Not-Existing-Owner";
        given(ownerRepository.findByLastName(nonExistingOwner)).willReturn(emptyList());

        // when & then
        mockMvc.perform(get(GET_OWNERS_URI).param("lastName", nonExistingOwner))
               .andExpect(status().isOk())
               .andExpect(view().name("owners/findOwners"));
    }
}
