package org.springframework.samples.petclinic.owner.rest;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetRepository;
import org.springframework.samples.petclinic.owner.PetType;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

@RestController("PetController")
@RequestMapping("/api/owners/{ownerid}/pets")
public class PetController {
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	private final PetRepository pets;

	private final OwnerRepository owners;

	public PetController(PetRepository pets, OwnerRepository owners) {
		this.pets = pets;
		this.owners = owners;
	}
	
	@GetMapping("/types")
	public Collection<PetType> populatePetTypes(@PathVariable("ownerid")int ownerid) throws NotFoundException {
		checkOwner(ownerid);
		return this.pets.findPetTypes();
	}
	
	@PostMapping("/new")
	public ResponseEntity<Object> processCreationForm(@PathVariable("ownerid")int ownerid,@Valid @RequestBody NewPetForm pet, BindingResult result) throws NotFoundException {
		checkOwner(ownerid);
		if (petAlreadyAvailable(ownerid,pet)) {
			result.rejectValue("name", "duplicate", "already exists");
		}
		if (result.hasErrors()) {
			return new ResponseEntity<Object>(result.getAllErrors(),HttpStatus.BAD_REQUEST);
		}
		else {
			createNewPet(ownerid,pet);
			return new ResponseEntity<Object>("Ped created.",HttpStatus.OK);
		}
	}

	private void createNewPet(int ownerid, @Valid NewPetForm pet) {
		Owner owner=this.owners.findById(ownerid);
		PetType petType=this.pets.findPetType(pet.getPetTypeId());
		Pet newPet=pet.getPet(pet);
		newPet.setType(petType);
		owner.addPet(newPet);
		this.pets.save(newPet);
	}

	private boolean petAlreadyAvailable(final int ownerid, final NewPetForm pet) {
		Owner owner=this.owners.findById(ownerid);
		return owner.getPet(pet.getName(), true) != null;
	}
	
	private void checkOwner(int ownerid) throws NotFoundException {
		Owner owner=this.owners.findById(ownerid);
		if(owner==null) {
			throw new NotFoundException("Owner not found");
		}
	}
}
