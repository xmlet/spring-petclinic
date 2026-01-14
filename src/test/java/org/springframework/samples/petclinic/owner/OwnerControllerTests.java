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

package org.springframework.samples.petclinic.owner;

import java.time.LocalDate;
import java.util.Collections;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.views.CreateOrUpdateOwnerForm;
import org.springframework.samples.petclinic.views.OwnerDetails;
import org.springframework.samples.petclinic.views.OwnersFind;
import org.springframework.samples.petclinic.views.OwnersList;
import org.springframework.samples.petclinic.visit.Visit;
import org.springframework.samples.petclinic.visit.VisitRepository;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for {@link OwnerController}
 *
 * @author Colin But
 */
@WebMvcTest(value = OwnerController.class,
		includeFilters = @ComponentScan.Filter(
				value = { CreateOrUpdateOwnerForm.class, OwnerDetails.class, OwnersFind.class, OwnersList.class },
				type = FilterType.ASSIGNABLE_TYPE))
class OwnerControllerTests {

	private static final int TEST_OWNER_ID = 1;

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private OwnerRepository owners;

	@MockitoBean
	private VisitRepository visits;

	private Owner george;

	@BeforeEach
	void setup() {
		george = new Owner();
		george.setId(TEST_OWNER_ID);
		george.setFirstName("George");
		george.setLastName("Franklin");
		george.setAddress("110 W. Liberty St.");
		george.setCity("Madison");
		george.setTelephone("6085551023");
		Pet max = new Pet();
		PetType dog = new PetType();
		dog.setName("dog");
		max.setId(1);
		max.setType(dog);
		max.setName("Max");
		max.setBirthDate(LocalDate.now());
		max.setOwner(george);
		george.setPetsInternal(Collections.singleton(max));
		given(this.owners.findById(TEST_OWNER_ID)).willReturn(george);
		Visit visit = new Visit();
		visit.setDate(LocalDate.now());
		visit.setDescription("Visit checkup routine.");
		visit.setPetId(max.getId());
		given(this.visits.findByPetId(max.getId())).willReturn(Collections.singletonList(visit));
	}

	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/owners/new"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
			.andExpect(content().string(containsString("Owner")))
			.andExpect(content().string(containsString("First Name")))
			.andExpect(content().string(containsString("Last Name")));
	}

	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc
			.perform(post("/owners/new").param("firstName", "Joe")
				.param("lastName", "Bloggs")
				.param("address", "123 Caramel Street")
				.param("city", "London")
				.param("telephone", "01316761638"))
			.andExpect(status().is3xxRedirection());
	}

	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc
			.perform(post("/owners/new").param("firstName", "Joe").param("lastName", "Bloggs").param("city", "London"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
			.andExpect(content().string(containsString("Owner")));
		// .andExpect(content().string(containsString("is required")));
	}

	@Test
	void testInitFindForm() throws Exception {
		mockMvc.perform(get("/owners/find"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
			.andExpect(content().string(containsString("Find Owners")))
			.andExpect(content().string(containsString("Last name")));
	}

	@Test
	void testProcessFindFormSuccess() throws Exception {
		mockMvc.perform(get("/owners"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
			.andExpect(content().string(containsString("Owners")));
	}

	@Test
	void testProcessFindFormByLastName() throws Exception {
		given(this.owners.findByLastName(george.getLastName())).willReturn(Lists.newArrayList(george));
		mockMvc.perform(get("/owners").param("lastName", "Franklin")).andExpect(status().isOk());
	}

	@Test
	void testProcessFindFormNoOwnersFound() throws Exception {
		mockMvc.perform(get("/owners").param("lastName", "Unknown Surname"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
			.andExpect(content().string(containsString("Owners")));
	}

	@Test
	void testInitUpdateOwnerForm() throws Exception {
		mockMvc.perform(get("/owners/{ownerId}/edit", TEST_OWNER_ID))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
			.andExpect(content().string(containsString("Owner")))
			.andExpect(content().string(containsString("Franklin")))
			.andExpect(content().string(containsString("George")))
			.andExpect(content().string(containsString("110 W. Liberty St.")))
			.andExpect(content().string(containsString("Madison")))
			.andExpect(content().string(containsString("6085551023")));
	}

	@Test
	void testProcessUpdateOwnerFormSuccess() throws Exception {
		mockMvc
			.perform(post("/owners/{ownerId}/edit", TEST_OWNER_ID).param("firstName", "Joe")
				.param("lastName", "Bloggs")
				.param("address", "123 Caramel Street")
				.param("city", "London")
				.param("telephone", "01616291589"))
			.andExpect(status().is3xxRedirection());
	}

	@Test
	void testProcessUpdateOwnerFormHasErrors() throws Exception {
		mockMvc
			.perform(post("/owners/{ownerId}/edit", TEST_OWNER_ID).param("firstName", "Joe")
				.param("lastName", "Bloggs")
				.param("city", "London"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
		// .andExpect(content().string(containsString("Owner")))
		// .andExpect(content().string(containsString("is required")));
	}

	@Test
	void testShowOwner() throws Exception {
		mockMvc.perform(get("/owners/{ownerId}", TEST_OWNER_ID))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
			.andExpect(content().string(containsString("Franklin")))
			.andExpect(content().string(containsString("George")))
			.andExpect(content().string(containsString("110 W. Liberty St.")))
			.andExpect(content().string(containsString("Madison")))
			.andExpect(content().string(containsString("6085551023")))
			.andExpect(content().string(containsString("Max")));
	}

}
