package com.gestionUser.demo.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Formation {

	private Integer idFormation;

	private String TitreFormation;

	private String description;

	private Date dateDebut;

	private Date dateFin;
	
	private String statut;

	private long montantPayeFormateur;

	private long montantFormation;

}
