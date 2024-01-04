package com.fst.Commantaire.Controller;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fst.Commantaire.entities.Formation;
import com.fst.Commantaire.entities.Participant;
import com.fst.Commantaire.feign.FeignFormation;
import com.fst.Commantaire.feign.FeignParticipant;
import com.fst.Commantaire.model.Commentaire;
import com.fst.Commantaire.repository.CommentaireRepository;


import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.NotFoundException;


//@RequestMapping("/commantaire")
@RestController
public class CommentController {
	@Autowired
	CommentaireRepository Commentairerepository;
	@Autowired
	FeignFormation feingFormation ;
	@Autowired
	FeignParticipant feignParticipant;

	@PostMapping("createCommentaire")
	public ResponseEntity<Commentaire> save(@RequestBody Commentaire commentaire) throws NotFoundException {
		Formation formation=  feingFormation.getFormationById(commentaire.getIdFormation());
		Participant participant = feignParticipant.getParticipantById(commentaire.getIdParticipant());
		if(formation != null && participant!=null)
		{  
			Commentaire commentaire1 = Commentairerepository.save(commentaire);
			return new ResponseEntity<>(commentaire1,HttpStatus.CREATED); 
		}
		else 
		{
			 throw new EntityNotFoundException("Formation ou Participant  Not found");
		}
	
	}
	@GetMapping("all")
	public ResponseEntity<List<Commentaire>> allCommentaire()
	{
		return new ResponseEntity<>(Commentairerepository.findAll(),HttpStatus.ACCEPTED); 
	}
	//Afficher les commentaires d'un formation
		@GetMapping("/idFormation/{idFormation}")
		public List<Commentaire> listCommentaireByIdFormation(@PathVariable Integer idFormation)
		{
			List<Commentaire> listCommentaire = Commentairerepository.findAll();
			List<Commentaire> AllCommentaireFormation = new ArrayList() ;
			for(Commentaire commentaire : listCommentaire) {
				if(commentaire.getIdFormation() == idFormation) {
					AllCommentaireFormation.add(commentaire);
				}
			}
			return AllCommentaireFormation;
		}
		//afficher tous les commentaire d'un participant
		
		@GetMapping("/idparticipant/{idparticipant}")
		public List<Commentaire> listCommentaireByIdParticpant(@PathVariable Integer idparticipant)
		{
			List<Commentaire> listCommentaire = Commentairerepository.findAll();
			List<Commentaire> AllCommentaireParticipat= new ArrayList() ;
			for(Commentaire commentaire : listCommentaire) {
				if(commentaire.getIdParticipant() == idparticipant) {
					AllCommentaireParticipat.add(commentaire);
				}
			}
			return AllCommentaireParticipat;
		}
		
		//Modifier un commentaire
		@PutMapping("updateCommentaire")
		public ResponseEntity<Commentaire> update(@RequestBody Commentaire commentaire) throws NotFoundException {
			Formation formation=  feingFormation.getFormationById(commentaire.getIdFormation());
			Participant participant = feignParticipant.getParticipantById(commentaire.getIdParticipant());
			Optional<Commentaire> optional =  Commentairerepository.findById(commentaire.getId());
			if(optional.isEmpty())
			{
				throw new EntityNotFoundException("Commentaire  Not Exist"); 
			}
			
			else 
			{
				if(formation != null && participant!=null)
				{  
					Commentaire commentaire1 = Commentairerepository.save(commentaire);
					return new ResponseEntity<>(commentaire1,HttpStatus.CREATED); 
				}
				else 
				{
					 throw new EntityNotFoundException("Formation ou Participant  Not found");
				}
			}
		}
		
		//supprimer une Formation
		@DeleteMapping("/idCommantaire/{idCommantaire}")
		public ResponseEntity<Formation> delete(@PathVariable Integer idCommantaire) {
			Commentairerepository.deleteById(idCommantaire);
			return ResponseEntity.noContent().build();
		}
	
}
