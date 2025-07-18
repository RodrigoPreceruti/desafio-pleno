package com.example.demo.service;

import com.example.demo.dto.CreateIncidentDTO;
import com.example.demo.dto.IncidentEntityDTO;
import com.example.demo.dto.UpdateIncidentDTO;
import com.example.demo.entity.Incident;
import com.example.demo.exception.custom.IncidentNotFoundException;
import com.example.demo.repository.IncidentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
                .orElseThrow(() -> new IncidentNotFoundException("Incident not found"));

        BeanUtils.copyProperties(request, incident);

        incident.setUpdatedAt(LocalDateTime.now());

        this.repository.save(incident);

        return new IncidentEntityDTO(incident);
    }

    public void deleteIncident(Long id) {
        Incident incident = this.repository
                .findById(id)
                .orElseThrow(() -> new IncidentNotFoundException("Incident not found"));

        incident.setClosedAt(LocalDateTime.now());

        this.repository.save(incident);
    }

    public Page<IncidentEntityDTO> getAllIncidents(Pageable pageable) {
        Page<Incident> incidents = this.repository.findAll(pageable);

        List<IncidentEntityDTO> incidentEntity = incidents.stream()
                .map(IncidentEntityDTO::new)
                .toList();

        return new PageImpl<>(incidentEntity, pageable, incidents.getTotalElements());
    }

    public IncidentEntityDTO getIncidentById(Long id) {
        Incident incident = this.repository
                .findById(id)
                .orElseThrow();

        return new IncidentEntityDTO(incident);
    }

    public Page<IncidentEntityDTO> getOrderedIncidents() {
        Pageable pageable = PageRequest.of(0, 20, Sort.by("createdAt").descending());

        Page<Incident> incidents = this.repository.findAll(pageable);

        List<IncidentEntityDTO> incidentEntity = incidents.stream()
                .map(IncidentEntityDTO::new)
                .toList();

        return new PageImpl<>(incidentEntity, pageable, incidents.getTotalElements());
    }
}
