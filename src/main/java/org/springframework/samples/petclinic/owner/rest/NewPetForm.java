package org.springframework.samples.petclinic.owner.rest;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.owner.Pet;
/**
 * 
 * @author Awadhesh Kumar
 *
 */
public class NewPetForm {
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;

	private int petTypeId;
	@NotEmpty
	private String name;

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public int getPetTypeId() {
		return petTypeId;
	}

	public void setPetTypeId(int petTypeId) {
		this.petTypeId = petTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Pet getPet(final NewPetForm pet) {
		Pet newPet=new Pet();
		newPet.setBirthDate(pet.getBirthDate());
		newPet.setName(pet.getName());
		return newPet;
	}

}
