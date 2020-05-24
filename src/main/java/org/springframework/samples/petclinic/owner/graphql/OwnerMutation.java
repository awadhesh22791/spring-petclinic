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

	public Owner newOwner(String firstName, String lastName,String address,String city,String telephone) {
		Owner owner=new Owner();
		owner.setFirstName(firstName);
		owner.setLastName(lastName);
		owner.setAddress(address);
		owner.setCity(city);
		owner.setTelephone(telephone);
		owners.save(owner);
		return owner;
	}
}
