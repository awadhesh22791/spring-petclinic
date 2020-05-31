package org.springframework.samples.petclinic.owner.graphql;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.samples.petclinic.model.EntitySpecification;
import org.springframework.samples.petclinic.model.SearchCriteria;
import org.springframework.samples.petclinic.model.SearchOperation;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetRepository;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
@Component
public class PetQuery implements GraphQLQueryResolver{
	@Autowired PetRepository pets;
	
	public List<Pet> pets(Pet pet,int page,int size){
		Pageable pageable=PageRequest.of(page, size);
		EntitySpecification<Pet>petSpec=getSpec(pet);
		Page<Pet> ownersPage = pets.findAll(petSpec,pageable);
		return ownersPage.getContent();
	}

	private EntitySpecification<Pet> getSpec(Pet pet) {
		EntitySpecification<Pet>spec=new EntitySpecification<Pet>();
		if(pet!=null) {
			if(pet.getId()!=null) {
				spec.add(new SearchCriteria("id", pet.getId(), SearchOperation.EQUAL));
			}
			if(pet.getName()!=null && !pet.getName().isBlank()) {
				spec.add(new SearchCriteria("name", pet.getName(), SearchOperation.MATCH_START));
			}
			if(pet.getBirthDate()!=null) {
				spec.add(new SearchCriteria("birthDate", pet.getBirthDate(), SearchOperation.EQUAL));
			}
		}
		return spec;
	}
}
