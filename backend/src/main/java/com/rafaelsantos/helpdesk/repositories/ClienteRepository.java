package com.rafaelsantos.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rafaelsantos.helpdesk.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
