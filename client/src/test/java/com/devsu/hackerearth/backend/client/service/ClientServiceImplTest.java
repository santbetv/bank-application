package com.devsu.hackerearth.backend.client.service;

import com.devsu.hackerearth.backend.client.exception.BussinesRuleException;
import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class ClientServiceImplTest {

    @MockBean
    private ClientRepository clientRepo;

    @Autowired
    private ClientService clientService;

    private Client client1, client2;

    @BeforeEach
    void setUp() {
        client1 = new Client();
        client1.setId(1L);
        client1.setName("Juan");
        client1.setDni("1234");
        client1.setGender("M");
        client1.setAge(30);
        client1.setAddress("Calle 1");
        client1.setPhone("3001112222");
        client1.setPassword("pass1");
        client1.setActive(true);

        client2 = new Client();
        client2.setId(2L);
        client2.setName("Ana");
        client2.setDni("5678");
        client2.setGender("F");
        client2.setAge(25);
        client2.setAddress("Calle 2");
        client2.setPhone("3003334444");
        client2.setPassword("pass2");
        client2.setActive(false);
    }

    @Test
    void getAllReturnsMappedDtos() {
        // dado
        when(clientRepo.findAll()).thenReturn(List.of(client1, client2));

        // cuando
        List<ClientDto> dtos = clientService.getAll();

        // entonces
        assertThat(dtos).hasSize(2);

        ClientDto dto1 = dtos.get(0);
        assertThat(dto1.getId()).isEqualTo(1L);
        assertThat(dto1.getName()).isEqualTo("Juan");
        assertThat(dto1.getDni()).isEqualTo("1234");
        assertThat(dto1.isActive()).isTrue();

        ClientDto dto2 = dtos.get(1);
        assertThat(dto2.getId()).isEqualTo(2L);
        assertThat(dto2.getName()).isEqualTo("Ana");
        assertThat(dto2.isActive()).isFalse();
    }

    @Test
    void getByIdExistingIdReturnsDto() throws BussinesRuleException {
        // dado
        when(clientRepo.findById(1L)).thenReturn(Optional.of(client1));

        // cuando
        ClientDto dto = clientService.getById(1L);

        // entonces
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("Juan");
        assertThat(dto.getDni()).isEqualTo("1234");
    }
}