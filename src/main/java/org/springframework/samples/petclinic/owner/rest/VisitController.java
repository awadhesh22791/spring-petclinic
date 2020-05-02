package org.springframework.samples.petclinic.owner.rest;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetRepository;
import org.springframework.samples.petclinic.visit.Visit;
import org.springframework.samples.petclinic.visit.VisitRepository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javassist.NotFoundException;
/**
 * 
 * @author Awadhesh Kumar
 *
 */
@RestController("VisitRestController")
@RequestMapping("/api/owners/{ownerId}/pets/{petId}/visits")
public class VisitController {
	private final VisitRepository visits;

	private final PetRepository pets;
	private final OwnerRepository owners;

	public VisitController(VisitRepository visits, PetRepository pets,OwnerRepository owners) {
		this.visits = visits;
		this.pets = pets;
		this.owners=owners;
	}
	
	@PostMapping("")
	public ResponseEntity<Object>saveVisit(@PathVariable("petId")int petId,@PathVariable("ownerId")int ownerId,
			@Valid @RequestBody NewVisitForm visit,BindingResult result) throws NotFoundException{
		checkOwner(ownerId);
		checkOwnerPet(ownerId,petId);
		if(result.hasErrors()) {
			return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
		}else {
			saveVisit(petId,visit);
			return new ResponseEntity<>("Visit saved.",HttpStatus.OK);
		}
	}

	private void saveVisit(int petId, final NewVisitForm visit) {
		Visit newVisit=visit.getVisit(visit,petId);
		this.visits.save(newVisit);
	}

	private void checkOwnerPet(int ownerId, int petId) throws NotFoundException {
		Pet pet=this.pets.findById(petId);
		if(pet==null || pet.getOwner().getId()!=ownerId) {
			throw new NotFoundException("Pet not found.");
		}
	}

	private void checkOwner(int ownerId) throws NotFoundException {
		Owner owner=this.owners.findById(ownerId);
		if(owner==null) {
			throw new NotFoundException("Owner not found.");
		}
	}
}
