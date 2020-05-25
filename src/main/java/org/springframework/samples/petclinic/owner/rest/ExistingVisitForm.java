package org.springframework.samples.petclinic.owner.rest;

import org.springframework.samples.petclinic.visit.Visit;

/**
 * 
 * @author Awadhesh Kumar
 *
 */
public class ExistingVisitForm extends NewVisitForm {
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ExistingVisitForm getVisit(Visit existingVisit) {
		ExistingVisitForm visit=new ExistingVisitForm();
		visit.setDate(existingVisit.getDate());
		visit.setDescription(existingVisit.getDescription());
		visit.setId(existingVisit.getId());
		return visit;
	}
}
