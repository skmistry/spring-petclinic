package org.springframework.samples.petclinic.owner;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.util.Lists.emptyList;
import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@Slf4j
public class OwnerControllerMockMvcUnitTests {
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
    void processFindFormWithoutAnyCriteria() throws Exception {
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
    void processFindFormMatchingLastNameCriteria() throws Exception {
        // given
        List<Owner> ownerList = newArrayList(geoege);
        String franklin = "Franklin";
        given(ownerRepository.findByLastName(franklin)).willReturn(ownerList);

        // when & then
        mockMvc.perform(get(GET_OWNERS_URI)
                            .param("lastName", franklin))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name("redirect:/owners/" + TEST_OWNER_ID));
    }

    @Test
    void processFindFormWithoutAnyMatchingLastNameCriteria() throws Exception {
        // given
        String nonExistingOwner = "Not-Existing-Owner";
        given(ownerRepository.findByLastName(nonExistingOwner)).willReturn(emptyList());

        // when & then
        mockMvc.perform(get(GET_OWNERS_URI)
                            .param("lastName", nonExistingOwner))
               .andExpect(status().isOk())
               .andExpect(view().name("owners/findOwners"));
    }

    @Test
    void successfulProcessCreationForm() throws Exception {
        // given
        doAnswer(invocationOnMock -> {
            final Owner owner = (Owner) invocationOnMock.getArgument(0);
            owner.setId(TEST_OWNER_ID);
            return null;
        }).when(ownerRepository).save(any(Owner.class));

        // when & then
        mockMvc.perform(post("/owners/new")
                            .param("firstName", geoege.getFirstName())
                            .param("lastName", geoege.getLastName())
                            .param("address", geoege.getAddress())
                            .param("city", geoege.getCity())
                            .param("telephone", geoege.getTelephone()))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name("redirect:/owners/" + geoege.getId()));
    }

    @Test
    void processCreationFormHasErrors() throws Exception {
        final int expectedErrorCount = 5;
        mockMvc.perform(post("/owners/new")
                            .param("telephone", "invalid-telephone-number"))
               .andExpect(status().isOk())
               .andExpect(view().name("owners/createOrUpdateOwnerForm"))
               .andExpect(model().hasErrors())
               .andExpect(model().attributeHasErrors("owner"))
               .andExpect(model().attributeHasFieldErrors("owner", "firstName"))
               .andExpect(model().attributeHasFieldErrors("owner", "lastName"))
               .andExpect(model().attributeHasFieldErrors("owner", "address"))
               .andExpect(model().attributeHasFieldErrors("owner", "city"))
               .andExpect(model().attributeHasFieldErrors("owner", "telephone"))
               .andExpect(model().attributeErrorCount("owner", expectedErrorCount))
               .andExpect(model().errorCount(expectedErrorCount));
    }

    @Test
    void successfulProcessUpdateOwnerForm() throws Exception {
        mockMvc.perform(post("/owners/{ownerId}/edit", TEST_OWNER_ID)
                            .param("firstName", benjamin.getFirstName())
                            .param("lastName", benjamin.getLastName())
                            .param("address", benjamin.getAddress())
                            .param("city", benjamin.getCity())
                            .param("telephone", benjamin.getTelephone()))
               .andExpect(status().is3xxRedirection())
               .andExpect(view().name("redirect:/owners/{ownerId}"));
    }

    @Test
    void processUpdateOwnerFormHasErrors() throws Exception {
        final int expectedErrorCount = 5;
        mockMvc.perform(post("/owners/{ownerId}/edit", TEST_OWNER_ID)
                            .param("telephone", "invalid-telephone-number"))
               .andExpect(status().isOk())
               .andExpect(view().name("owners/createOrUpdateOwnerForm"))
               .andExpect(model().hasErrors())
               .andExpect(model().attributeHasErrors("owner"))
               .andExpect(model().attributeHasFieldErrors("owner", "firstName"))
               .andExpect(model().attributeHasFieldErrors("owner", "lastName"))
               .andExpect(model().attributeHasFieldErrors("owner", "address"))
               .andExpect(model().attributeHasFieldErrors("owner", "city"))
               .andExpect(model().attributeHasFieldErrors("owner", "telephone"))
               .andExpect(model().attributeErrorCount("owner", expectedErrorCount))
               .andExpect(model().errorCount(expectedErrorCount));
    }
}
