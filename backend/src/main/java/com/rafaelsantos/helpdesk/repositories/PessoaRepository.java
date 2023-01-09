package com.rafaelsantos.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rafaelsantos.helpdesk.domain.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{

}
