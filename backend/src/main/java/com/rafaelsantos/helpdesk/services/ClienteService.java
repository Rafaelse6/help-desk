package com.rafaelsantos.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rafaelsantos.helpdesk.domain.Cliente;
import com.rafaelsantos.helpdesk.domain.Pessoa;
import com.rafaelsantos.helpdesk.domain.dto.ClienteDTO;
import com.rafaelsantos.helpdesk.repositories.ClienteRepository;
import com.rafaelsantos.helpdesk.repositories.PessoaRepository;
import com.rafaelsantos.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.rafaelsantos.helpdesk.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Cliente findById(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found! Id: " + id));
	}
	
	public List<Cliente> findAll(){
		return repository.findAll();
	}

	public Cliente create(ClienteDTO objDTO) {
		objDTO.setId(null);
		validaPorCpfEEmail(objDTO);
		Cliente newObj = new Cliente(objDTO);
		return repository.save(newObj); 
	}
	
	public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
		objDTO.setId(id);
		Cliente oldObj = findById(id);
		validaPorCpfEEmail(objDTO);
		oldObj = new Cliente(objDTO);
		return repository.save(oldObj);
	}
	
	public void delete(Integer id) {
		Cliente obj = findById(id);

		if (obj.getChamados().size() > 0) {
			throw new DataIntegrityViolationException("Cliente possui ordens de serviço e não pode ser deletado!");
		}

		repository.deleteById(id);
	}
	
	private void validaPorCpfEEmail(ClienteDTO objDTO) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
		if(obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já está cadastrado no sistema");
		}
		
		obj = pessoaRepository.findByEmail(objDTO.getEmail());
		if(obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("E-mail já está cadastrado no sistema");
		}
	}
}
