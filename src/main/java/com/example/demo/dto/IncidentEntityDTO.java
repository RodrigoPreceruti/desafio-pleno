package com.example.demo.dto;

import com.example.demo.entity.Incident;

import java.time.LocalDateTime;

public record IncidentEntityDTO(
        Long idIncident,
        String name,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime closedAt
) {
    public IncidentEntityDTO(Incident incident) {
        this(
                incident.getIdIncident(),
                incident.getName(),
                incident.getDescription(),
                incident.getCreatedAt(),
                incident.getUpdatedAt(),
                incident.getClosedAt()
        );
    }
}
