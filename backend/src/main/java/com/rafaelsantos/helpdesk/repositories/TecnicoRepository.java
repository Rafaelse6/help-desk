package com.rafaelsantos.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rafaelsantos.helpdesk.domain.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer>{

}
