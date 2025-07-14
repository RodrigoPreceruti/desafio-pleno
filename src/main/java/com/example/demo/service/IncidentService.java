package com.example.demo.service;

import com.example.demo.dto.CreateIncidentDTO;
import com.example.demo.dto.IncidentEntityDTO;
import com.example.demo.dto.UpdateIncidentDTO;
import com.example.demo.entity.Incident;
import com.example.demo.repository.IncidentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IncidentService {
    private final IncidentRepository repository;

    public IncidentService(IncidentRepository repository) {
        this.repository = repository;
    }

    public IncidentEntityDTO createIncident(CreateIncidentDTO request) {
        Incident incident = new Incident();
        incident.setName(request.name());
        incident.setDescription(request.description());

        this.repository.save(incident);

        return new IncidentEntityDTO(incident);
    }
}
