package com.devsu.hackerearth.backend.account.client;

import com.devsu.hackerearth.backend.account.model.dto.ClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "client-service", url  = "${clients.service.url:http://localhost:8001}")
public interface ClientServiceFeign {

    @GetMapping("/api/clients/{id}")
    ClientDto getById(@PathVariable("id") Long id);
}
