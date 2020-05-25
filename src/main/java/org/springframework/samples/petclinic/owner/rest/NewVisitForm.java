package org.springframework.samples.petclinic.owner.rest;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.visit.Visit;
/**
 * 
 * @author Awadhesh Kumar
 *
 */
public class NewVisitForm {
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	@NotEmpty
	private String description;

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Visit getVisit(NewVisitForm visit, int petId) {
		Visit newVisit=new Visit();
		newVisit.setDate(visit.getDate());
		newVisit.setDescription(visit.getDescription());
		newVisit.setPetId(petId);
		return newVisit;
	}
}
