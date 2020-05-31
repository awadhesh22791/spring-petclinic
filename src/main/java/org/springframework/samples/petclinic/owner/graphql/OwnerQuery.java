package org.springframework.samples.petclinic.owner.graphql;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.samples.petclinic.model.SearchCriteria;
import org.springframework.samples.petclinic.model.SearchOperation;
import org.springframework.samples.petclinic.model.EntitySpecification;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
@Component
public class OwnerQuery implements GraphQLQueryResolver{
	@Autowired OwnerRepository owners;
	
	public List<Owner> owners(Owner owner,int page,int size){
		Pageable pageable=PageRequest.of(page, size);
		EntitySpecification<Owner> ownerSpec=getOwnerSpecification(owner);
		Page<Owner> ownersPage = owners.findAll(ownerSpec,pageable);
		return ownersPage.getContent();
	}

	private EntitySpecification<Owner> getOwnerSpecification(Owner owner) {
		EntitySpecification<Owner> spec=new EntitySpecification<Owner>();
		if(owner.getId()!=null) {
			spec.add(new SearchCriteria("id", owner.getId(), SearchOperation.EQUAL));
		}
		if(owner.getAddress()!=null && !owner.getAddress().isBlank()) {
			spec.add(new SearchCriteria("address", owner.getAddress(), SearchOperation.MATCH_START));
		}
		if(owner.getCity()!=null && !owner.getCity().isBlank()) {
			spec.add(new  SearchCriteria("city", owner.getCity(), SearchOperation.MATCH_START));
		}
		if(owner.getFirstName()!=null && !owner.getFirstName().isBlank()) {
			spec.add(new SearchCriteria("firstName", owner.getFirstName(), SearchOperation.MATCH_START));
		}
		if(owner.getLastName()!=null && !owner.getLastName().isBlank()) {
			spec.add(new SearchCriteria("lastName", owner.getLastName(), SearchOperation.MATCH_START));
		}
		if(owner.getTelephone()!=null && !owner.getTelephone().isBlank()) {
			spec.add(new SearchCriteria("telephone", owner.getTelephone(), SearchOperation.MATCH_START));
		}
		return spec;
	}
}
