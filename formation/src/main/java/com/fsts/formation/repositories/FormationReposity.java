package com.fsts.formation.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fsts.formation.entities.Formation;

@Repository
public interface FormationReposity extends JpaRepository<Formation, Integer>{
   //List<Formation>findFormationByidFormateur()
}
