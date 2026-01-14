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

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.views.CreateOrUpdatePetForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.Collection;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Miguel Gamboa
 *
 * This controller is based on seminal implementation for Thymeleaf and modified for
 * HtmlFlow.
 */
@Controller
@RequestMapping("/owners/{ownerId}")
class PetController {

	private final CreateOrUpdatePetForm petsForm;

	private final PetRepository pets;

	private final OwnerRepository owners;

	public PetController(PetRepository pets, OwnerRepository owners, CreateOrUpdatePetForm petsForm) {
		this.pets = pets;
		this.owners = owners;
		this.petsForm = petsForm;
	}

	@ModelAttribute("types")
	public Collection<PetType> populatePetTypes() {
		return this.pets.findPetTypes();
	}

	@ModelAttribute("owner")
	public Owner findOwner(@PathVariable("ownerId") int ownerId) {
		return this.owners.findById(ownerId);
	}

	@InitBinder("owner")
	public void initOwnerBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@InitBinder("pet")
	public void initPetBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new PetValidator());
	}

	@GetMapping("/pets/new")
	@ResponseBody
	public ResponseEntity<String> initCreationForm(Owner owner, ModelMap model) {
		Pet pet = new Pet();
		owner.addPet(pet);
		return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(petsForm.view.render(pet));
	}

	@PostMapping("/pets/new")
	@ResponseBody
	public ResponseEntity<String> processCreationForm(Owner owner, @Valid Pet pet, BindingResult result, ModelMap model,
			HttpServletResponse response) throws IOException {
		// !!!! To Do: Validate duplications and present corresponding errors
		/*
		 * if (StringUtils.hasLength(pet.getName()) && pet.isNew() &&
		 * owner.getPet(pet.getName(), true) != null) { result.rejectValue("name",
		 * "duplicate", "already exists"); }
		 */
		owner.addPet(pet);
		if (result.hasErrors()) {
			// model.put("pet", pet);
			pet = new Pet(); // Clear pet to format view as add new operation.
			owner.addPet(pet);
			return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(petsForm.view.render(pet));
		}
		else {
			this.pets.save(pet);
			return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create("/owners/" + owner.getId())).build();
		}
	}

	@GetMapping("/pets/{petId}/edit")
	@ResponseBody
	public ResponseEntity<String> initUpdateForm(@PathVariable("petId") int petId) {
		Pet pet = this.pets.findById(petId);
		return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(petsForm.view.render(pet));
	}

	@PostMapping("/pets/{petId}/edit")
	@ResponseBody
	public ResponseEntity<String> processUpdateForm(@Valid Pet pet, BindingResult result, Owner owner, ModelMap model,
			HttpServletResponse response, @PathVariable("petId") int petId) throws IOException {
		if (result.hasErrors()) {
			pet.setOwner(owner);
			model.put("pet", pet);
			return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(petsForm.view.render(pet));
		}
		else {
			pet.setId(petId);
			pet.setOwner(owner);
			this.pets.save(pet);
			return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create("/owners/" + owner.getId())).build();
		}
	}

}
