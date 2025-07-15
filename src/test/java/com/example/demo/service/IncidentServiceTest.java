package com.example.demo.service;

import com.example.demo.dto.CreateIncidentDTO;
import com.example.demo.dto.IncidentEntityDTO;
import com.example.demo.dto.UpdateIncidentDTO;
import com.example.demo.entity.Incident;
import com.example.demo.exception.custom.IncidentNotFoundException;
import com.example.demo.repository.IncidentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class IncidentServiceTest {
    @Mock
    private IncidentRepository repository;

    @InjectMocks
    private IncidentService service;

    @Test
    void shouldCreateIncident() {
        CreateIncidentDTO dto = new CreateIncidentDTO("Password leak", "Investigate password leaks");

        Incident saved = new Incident();
        saved.setName(dto.name());
        saved.setDescription(dto.description());

        Mockito.when(this.repository.save(ArgumentMatchers.any(Incident.class))).thenReturn(saved);

        IncidentEntityDTO result = this.service.createIncident(dto);

        Assertions.assertEquals(dto.name(), result.name());
        Assertions.assertEquals(dto.description(), result.description());

        Mockito.verify(this.repository).save(ArgumentMatchers.any(Incident.class));
    }

    @Test
    void shouldUpdateIncident() {
        Long id = 1L;

        UpdateIncidentDTO dto = new UpdateIncidentDTO("Updated Name", "Updated Desc");

        Incident incident = new Incident();
        incident.setIdIncident(id);
        incident.setName("Password leak");
        incident.setDescription("Investigate password leaks");

        Mockito.when(this.repository.findById(id)).thenReturn(Optional.of(incident));
        Mockito.when(this.repository.save(ArgumentMatchers.any())).thenReturn(incident);

        IncidentEntityDTO result = service.updateIncident(id, dto);

        Assertions.assertEquals(dto.name(), result.name());
        Assertions.assertEquals(dto.description(), result.description());

        Mockito.verify(this.repository).save(incident);
    }

    @Test
    void shouldThrowWhenIncidentNotFoundOnUpdate() {
        Long id = 1L;

        UpdateIncidentDTO dto = new UpdateIncidentDTO("Updated", "Updated");

        Mockito.when(this.repository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(IncidentNotFoundException.class, () -> this.service.updateIncident(id, dto));
    }

    @Test
    void shouldDeleteIncident() {
        Long id = 1L;

        Incident incident = new Incident();
        incident.setIdIncident(id);

        Mockito.when(this.repository.findById(id)).thenReturn(Optional.of(incident));

        this.service.deleteIncident(id);

        Assertions.assertNotNull(incident.getClosedAt());

        Mockito.verify(this.repository).save(incident);
    }

    @Test
    void shouldThrowWhenIncidentNotFoundOnDelete() {
        Long id = 1L;

        Mockito.when(this.repository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(IncidentNotFoundException.class, () -> service.deleteIncident(id));
    }

    @Test
    void shouldReturnAllIncidents() {
        Pageable pageable = PageRequest.of(0, 10);

        Incident incident = new Incident();
        incident.setName("Password leak");

        Page<Incident> page = new PageImpl<>(List.of(incident));

        Mockito.when(this.repository.findAll(pageable)).thenReturn(page);

        Page<IncidentEntityDTO> result = this.service.getAllIncidents(pageable);

        Assertions.assertEquals(1, result.getTotalElements());

        Mockito.verify(this.repository).findAll(pageable);
    }

    @Test
    void shouldReturnIncidentById() {
        Long id = 1L;

        Incident incident = new Incident();
        incident.setIdIncident(id);
        incident.setName("Password leak");

        Mockito.when(this.repository.findById(id)).thenReturn(Optional.of(incident));

        IncidentEntityDTO result = this.service.getIncidentById(id);

        Assertions.assertEquals(id, result.idIncident());
    }

    @Test
    void shouldReturnOrderedIncidents() {
        Pageable pageable = PageRequest.of(0, 20, Sort.by("createdAt").descending());

        Incident incident = new Incident();
        incident.setName("Password leak");

        Mockito.when(this.repository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(incident)));

        Page<IncidentEntityDTO> result = this.service.getOrderedIncidents();

        Assertions.assertEquals(1, result.getTotalElements());
    }
}
