package org.springframework.samples.petclinic.owner.graphql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLResolver;

@Component
public class OwnerQueryResolver implements GraphQLResolver<Owner> {
	@Autowired
	private OwnerRepository owners;

	public Owner getOwner(Owner owner) {
		return owners.findById(owner.getId());
	}
}
