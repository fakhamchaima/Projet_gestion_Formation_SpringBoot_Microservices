package com.fsts.formation.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fsts.formation.entities.Formation;
import com.fsts.formation.feign.FeignUtilisateur;
import com.fsts.formation.model.Formateur;
import com.fsts.formation.model.Participant;
import com.fsts.formation.repositories.FormationReposity;


import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.NotFoundException;

@RestController
//@RequestMapping("formation")
public class FormationController {
	@Autowired
	FormationReposity formationRepositories;
	@Autowired
	FeignUtilisateur feignUtilisateur;


	// chercher le formateur d'un formation 
	@GetMapping("formation/{idFormateur}")
    public Formation getformationbyid(@PathVariable int idFormateur){
		Formation res =  formationRepositories.findById(idFormateur).get();
		Formateur c = feignUtilisateur.getFormateurById(res.getIdFormateur());
        res.setFormateur(c);
        return res;
    }
	@GetMapping("/idFormateur/{idFormateur}")
	public List<Formation> listFormationByIdFormateur(@PathVariable Integer idFormateur)
	{
		List<Formation> listFormation = formationRepositories.findAll();
		List<Formation> AllFormationFormateur = new ArrayList() ;
		for(Formation formation : listFormation) {
			if(formation.getIdFormateur() == idFormateur) {
				AllFormationFormateur.add(formation);
			}
		}
		return AllFormationFormateur;
	}
 

    @GetMapping("/byFormationId/{formationId}")
    public List<Participant> getParticipantsByFormationId(@PathVariable int formationId) {
       
    	return feignUtilisateur.getParticipantById(formationId);
    	
    	
    }
	
	
	//ajoute d'un formation 
	@PostMapping("createFormation")
	public ResponseEntity<Formation> save(@RequestBody Formation formation) throws NotFoundException {
	Formateur f= feignUtilisateur.getFormateurById(formation.getIdFormateur());
	if(f != null)
	{  Formation fr = formationRepositories.save(formation);
		return new ResponseEntity<>(fr,HttpStatus.CREATED); 
		
	}
	else 
	{
		 throw new EntityNotFoundException("Formateur  Not found");
	}
	
	}
	@GetMapping("allFormations")
	public List<Formation> listFormation()
	{
		return formationRepositories.findAll();
	}
	//Modifer une formation
	@PutMapping("updateFormation")
	public ResponseEntity<Formation> update(@RequestBody Formation formation) throws NotFoundException
	{
		Optional<Formation> optional =  formationRepositories.findById(formation.getIdFormation());
		if(optional.isEmpty())
			{
			throw new EntityNotFoundException("Formation  Not Exist"); 
			}
		
		else 
		{
		Formateur c = feignUtilisateur.getFormateurById(formation.getIdFormateur());
		if(c!=null)
		{
	    Formation fr=formationRepositories.save(formation);
		 return ResponseEntity.accepted().body(fr);
		}
	}
		 throw new EntityNotFoundException("Formateur  Not found");
		}
	//supprimer une Formation
	@DeleteMapping("/idFormation/{idFormation}")
	public ResponseEntity<Formation> delete(@PathVariable Integer idFormation) {
		formationRepositories.deleteById(idFormation);
		return ResponseEntity.noContent().build();
	}
	//list des formations d'un Participent
	@GetMapping("/idParticipent/{idParticipent}")
	public List<Formation> listFormationByIdParticipent(@PathVariable Integer idParticipent)
	{
		List<Formation> listFormation = formationRepositories.findAll();
		List<Formation> AllFormationParticipant = new ArrayList() ;
		for(Formation formation : listFormation) {
			if(formation.getIdParticipant() == idParticipent) {
				AllFormationParticipant.add(formation);
			}
		}
		return AllFormationParticipant;
	}

}
