package org.springframework.samples.petclinic.owner.graphql;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
@Component
public class OwnerQuery implements GraphQLQueryResolver{
	@Autowired OwnerRepository owners;
	
	public List<Owner> owners(Owner owner,int page,int size){
		Pageable pageable=PageRequest.of(page, size);
		Page<Owner> ownersPage = owners.findAll(pageable);
		return ownersPage.getContent();
	}
}
