package com.example.demo.service;

import com.example.demo.dto.CreateIncidentDTO;
import com.example.demo.dto.IncidentEntityDTO;
import com.example.demo.dto.UpdateIncidentDTO;
import com.example.demo.entity.Incident;
import com.example.demo.repository.IncidentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    public IncidentEntityDTO updateIncident(Long idIncident, UpdateIncidentDTO request) {
        Incident incident = this.repository
                .findById(idIncident)
                .orElseThrow(RuntimeException::new);

        BeanUtils.copyProperties(request, incident);

        return new IncidentEntityDTO(incident);
    }

    public void deleteIncident(Long id) {
        Incident incident = this.repository
                .findById(id)
                .orElseThrow(RuntimeException::new);

        incident.setClosedAt(LocalDateTime.now());

        this.repository.save(incident);
    }
}
