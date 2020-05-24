package org.springframework.samples.petclinic.owner.graphql;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
@Component
public class OwnerQuery implements GraphQLQueryResolver{
	@Autowired OwnerRepository owners;
	
	public List<Owner>owners(){
		return owners.findAll();
	}
	
}
