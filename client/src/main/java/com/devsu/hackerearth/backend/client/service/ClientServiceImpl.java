package com.devsu.hackerearth.backend.client.service;

import java.util.List;

import com.devsu.hackerearth.backend.client.exception.BussinesRuleException;
import com.devsu.hackerearth.backend.client.mapper.ClientResponseMapper;
import com.devsu.hackerearth.backend.client.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.repository.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService {
	private static final Logger LOG = LoggerFactory.getLogger(ClientServiceImpl.class);
	private final ClientRepository clientRepository;
	private final ClientResponseMapper clientResponseMapper;

	@Autowired
	public ClientServiceImpl(ClientRepository clientRepository, ClientResponseMapper clientResponseMapper) {
		this.clientRepository = clientRepository;
		this.clientResponseMapper = clientResponseMapper;
	}

	@Override
	public List<ClientDto> getAll() {
		return clientResponseMapper.toListClientDTORequest(clientRepository.findAll());
	}

	@Override
	public ClientDto getById(Long id) throws BussinesRuleException {
		Client entity = clientRepository.findById(id)
				.orElseThrow(() -> new BussinesRuleException(
						1L,
						"001",
						"Cliente con id " + id + " no encontrado",
						HttpStatus.NOT_ACCEPTABLE
				));
		return clientResponseMapper.toClientDto(entity);
	}

	@Override
	public ClientDto create(ClientDto clientDto) {
		Client saved = clientRepository.save(clientResponseMapper.toClient(clientDto));
		return clientResponseMapper.toClientDto(saved);
	}

	@Override
	public ClientDto update(Long id, ClientDto clientDto) throws BussinesRuleException {
		Client existing = clientRepository.findById(id)
				.orElseThrow(() -> new BussinesRuleException(
						1L,
						"001",
						"Cliente con id " + id + " no encontrado",
						HttpStatus.NOT_FOUND
				));
		existing.setName(clientDto.getName());
		existing.setDni(clientDto.getDni());
		existing.setGender(clientDto.getGender());
		existing.setAge(clientDto.getAge());
		existing.setAddress(clientDto.getAddress());
		existing.setPhone(clientDto.getPhone());
		existing.setPassword(clientDto.getPassword());
		existing.setActive(clientDto.isActive());
		Client saved = clientRepository.save(existing);
		return clientResponseMapper.toClientDto(saved);
	}

	@Override
	public ClientDto partialUpdate(Long id, PartialClientDto partial) throws BussinesRuleException {
		Client existing = clientRepository.findById(id)
				.orElseThrow(() -> new BussinesRuleException(
						1L,
						"001",
						"Cliente con id " + id + " no encontrado",
						HttpStatus.NOT_FOUND
				));
		existing.setActive(partial.getIsActive());
		Client saved = clientRepository.save(existing);
		return clientResponseMapper.toClientDto(saved);
	}

	@Override
	public void deleteById(Long id) throws BussinesRuleException {
		if (!clientRepository.existsById(id)) {
			throw new BussinesRuleException(
					1L,
					"001",
					"Cliente con id " + id + " no encontrado",
					HttpStatus.NOT_FOUND
			);
		}
		clientRepository.deleteById(id);
	}
}
