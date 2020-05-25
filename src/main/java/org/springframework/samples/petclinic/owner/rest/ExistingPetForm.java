package org.springframework.samples.petclinic.owner.rest;

import org.springframework.samples.petclinic.owner.Pet;
/**
 * 
 * @author Awadhesh Kumar
 *
 */
public class ExistingPetForm extends NewPetForm {
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ExistingPetForm getPet(Pet existingPet) {
		ExistingPetForm pet=new ExistingPetForm();
		pet.setBirthDate(existingPet.getBirthDate());
		pet.setId(existingPet.getId());
		pet.setName(existingPet.getName());
		pet.setPetTypeId(existingPet.getType().getId());
		return pet;
	}
}
