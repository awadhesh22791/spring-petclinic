package org.springframework.samples.petclinic.owner.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javassist.NotFoundException;
/**
 * 
 * @author Awadhesh Kumar
 *
 */

@RestController("PetRestController")
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
	
	@GetMapping("")
	public ResponseEntity<Object> getAllPets(@PathVariable("ownerid") int ownerid){
		Collection<ExistingPetForm>pets=getPets(ownerid);
		if(pets.size()>0) {
			return new ResponseEntity<>(pets,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("{petid}")
	public ResponseEntity<Object> getPet(@PathVariable("petid") int petid){
		ExistingPetForm pet=getOnePet(petid);
		if(pet!=null) {
			return new ResponseEntity<>(pet,HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("")
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
	
	@PutMapping("")
	public ResponseEntity<Object> processUpdateForm(@PathVariable("ownerid")int ownerid,@Valid @RequestBody ExistingPetForm pet, BindingResult result) throws NotFoundException {
		checkOwner(ownerid);
		if (result.hasErrors()) {
			return new ResponseEntity<>(result.getAllErrors(),HttpStatus.NOT_ACCEPTABLE);
		} else {
			updatePet(ownerid,pet);
			return new ResponseEntity<Object>(HttpStatus.OK);
		}
	}

	private Collection<ExistingPetForm> getPets(final int ownerid) {
		List<Pet>existingPets=this.pets.findByOwnerId(ownerid);
		List<ExistingPetForm>pets=new ArrayList<>();
		if(existingPets.size()>0) {
			for(Pet existingPet:existingPets) {
				ExistingPetForm pet=new ExistingPetForm();
				pet=pet.getPet(existingPet);
				pets.add(pet);
			}
		}
		return pets;
	}
	
	private ExistingPetForm getOnePet(int petid) {
		Pet existingPet=this.pets.findById(petid);
		ExistingPetForm pet=null;
		if(existingPet!=null) {
			pet=new ExistingPetForm();
			pet=pet.getPet(existingPet);
		}
		return pet;
	}
	
	private void updatePet(int ownerid, final ExistingPetForm pet) throws NotFoundException {
		Pet existingPet=this.pets.findById(pet.getId());
		if(existingPet==null || !existingPet.getOwner().getId().equals(ownerid)) {
			throw new NotFoundException("Pet not found");
		}
		existingPet.setBirthDate(pet.getBirthDate());
		existingPet.setName(pet.getName());
		PetType petType=this.pets.findPetType(pet.getPetTypeId());
		existingPet.setType(petType);
		this.pets.save(existingPet);
	}

	private void createNewPet(int ownerid, @Valid NewPetForm pet) {
		Optional<Owner> owner=this.owners.findById(ownerid);
		PetType petType=this.pets.findPetType(pet.getPetTypeId());
		Pet newPet=pet.getPet(pet);
		newPet.setType(petType);
		if(owner.isPresent()) {
			owner.get().addPet(newPet);
			this.pets.save(newPet);
		}
	}

	private boolean petAlreadyAvailable(final int ownerid, final NewPetForm pet) {
		Optional<Owner> owner=this.owners.findById(ownerid);
		if(owner.isPresent()) {
			return owner.get().getPet(pet.getName(), true) != null;
		}
		return false;
	}
	
	private void checkOwner(int ownerid) throws NotFoundException {
		Optional<Owner> owner=this.owners.findById(ownerid);
		if(!owner.isPresent()) {
			throw new NotFoundException("Owner not found");
		}
	}
}
