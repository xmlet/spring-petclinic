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

import org.springframework.samples.petclinic.views.CreateOrUpdateOwnerForm;
import org.springframework.samples.petclinic.views.OwnerDetails;
import org.springframework.samples.petclinic.views.OwnersFind;
import org.springframework.samples.petclinic.views.OwnersList;
import org.springframework.samples.petclinic.visit.VisitRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @author Miguel Gamboa
 *
 * This controller is based on seminal implementation for Thymeleaf and modified for
 * HtmlFlow.
 */
@Controller
class OwnerController {

	private final OwnerRepository owners;

	private VisitRepository visits;

	public OwnerController(OwnerRepository clinicService, VisitRepository visits) {
		this.owners = clinicService;
		this.visits = visits;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping("/owners/new")
	@ResponseBody
	public String initCreationForm(Map<String, Object> model) {
		return CreateOrUpdateOwnerForm.view.render();
	}

	@PostMapping("/owners/new")
	@ResponseBody
	public String processCreationForm(@Valid Owner owner, BindingResult result, HttpServletResponse response)
			throws IOException {
		if (result.hasErrors()) {
			/**
			 * !!! To Do: add errors from BindingResult to the model to be rendered by the
			 * view.
			 */
			return CreateOrUpdateOwnerForm.view.render();
		}
		else {
			this.owners.save(owner);
			response.sendRedirect("/owners/" + owner.getId());
			return "";
		}
	}

	@GetMapping("/owners/find")
	@ResponseBody
	public String initFindForm(Map<String, Object> model) {
		return OwnersFind.view.render();
	}

	@GetMapping("/owners")
	@ResponseBody
	public String processFindForm(Owner owner, BindingResult result, Map<String, Object> model) {

		// allow parameterless GET request for /owners to return all records
		if (owner.getLastName() == null) {
			owner.setLastName(""); // empty string signifies broadest possible search
		}

		// find owners by last name
		Collection<Owner> results = this.owners.findByLastName(owner.getLastName());
		return OwnersList.view.render(results);
	}

	@GetMapping("/owners/{ownerId}/edit")
	@ResponseBody
	public String initUpdateOwnerForm(@PathVariable("ownerId") int ownerId, Model model) {
		Owner owner = this.owners.findById(ownerId);
		return CreateOrUpdateOwnerForm.view.render(owner);
	}

	@PostMapping("/owners/{ownerId}/edit")
	@ResponseBody
	public String processUpdateOwnerForm(@Valid Owner owner, BindingResult result, @PathVariable("ownerId") int ownerId,
			HttpServletResponse response) throws IOException {
		if (result.hasErrors()) {
			/**
			 * !!! To Do: add errors from BindingResult to the model to be rendered by the
			 * view.
			 */
			return CreateOrUpdateOwnerForm.view.render(owner);
		}
		else {
			owner.setId(ownerId);
			this.owners.save(owner);
			response.sendRedirect("/owners/" + owner.getId());
			return "";
		}
	}

	/**
	 * Custom handler for displaying an owner.
	 * @param ownerId the ID of the owner to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@GetMapping("/owners/{ownerId}")
	@ResponseBody
	public String showOwner(@PathVariable("ownerId") int ownerId) {
		Owner owner = this.owners.findById(ownerId);
		for (Pet pet : owner.getPets()) {
			pet.setVisitsInternal(visits.findByPetId(pet.getId()));
		}
		return OwnerDetails.view.render(owner);
	}

}
