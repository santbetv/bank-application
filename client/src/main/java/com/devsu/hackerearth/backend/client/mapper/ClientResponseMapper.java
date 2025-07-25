package com.devsu.hackerearth.backend.client.mapper;


import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientResponseMapper {

    Client toClient(ClientDto clientDto);

    @InheritInverseConfiguration
    @Mappings({})
    List<ClientDto> toListClientDTORequest(List<Client> clients);

    @InheritInverseConfiguration
    @Mappings({})
    ClientDto toClientDto(Client client);
}
