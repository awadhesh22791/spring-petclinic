package org.springframework.samples.petclinic.owner.graphql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.stereotype.Component;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;

@Component
public class OwnerMutation implements GraphQLMutationResolver {
	@Autowired
	OwnerRepository owners;

	public Owner newOwner(Owner owner) {
		owners.save(owner);
		return owner;
	}
}
