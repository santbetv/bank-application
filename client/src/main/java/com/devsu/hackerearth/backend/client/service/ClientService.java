package com.devsu.hackerearth.backend.client.service;

import java.util.List;

import com.devsu.hackerearth.backend.client.exception.BussinesRuleException;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;

public interface ClientService {

	List<ClientDto> getAll();
	ClientDto getById(Long id) throws BussinesRuleException;
	ClientDto create(ClientDto clientDto);
	ClientDto update(Long id, ClientDto clientDto) throws BussinesRuleException;
	ClientDto partialUpdate(Long id, PartialClientDto partialClientDto) throws BussinesRuleException;
	void deleteById(Long id) throws BussinesRuleException;
}
