package com.rafaelsantos.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rafaelsantos.helpdesk.domain.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer>{

}
