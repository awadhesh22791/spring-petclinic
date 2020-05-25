package org.springframework.samples.petclinic.owner.graphql;

import java.util.Optional;

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
		Optional<Owner> result = owners.findById(owner.getId());
		if(result.isPresent()) {
			return result.get();
		}
		return null;
	}
}
