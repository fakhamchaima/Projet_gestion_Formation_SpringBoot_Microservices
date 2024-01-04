package com.fsts.formation.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fsts.formation.model.Formateur;
import com.fsts.formation.model.Participant;


@FeignClient(name = "utilisateur-service" ,url ="http://localhost:8081")
public interface FeignUtilisateur {
	@GetMapping("findformateurById/{idFormateur}")
	Formateur getFormateurById(@PathVariable int idFormateur);
	
//	
	@GetMapping("idformation/{idformation}") 
	List<Participant> getParticipantById(@PathVariable int idformation);
	 
}
