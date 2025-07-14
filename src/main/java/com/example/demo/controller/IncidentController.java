package com.example.demo.controller;

import com.example.demo.dto.CreateIncidentDTO;
import com.example.demo.dto.IncidentEntityDTO;
import com.example.demo.dto.UpdateIncidentDTO;
import com.example.demo.service.IncidentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/incidents")
public class IncidentController {
    private final IncidentService service;

    public IncidentController(IncidentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<IncidentEntityDTO> createIncident(@RequestBody CreateIncidentDTO request) {
        var incident = this.service.createIncident(request);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(incident.idIncident())
                .toUri();

        return ResponseEntity.created(uri).body(incident);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncidentEntityDTO> updateEntity(@PathVariable Long id, @RequestBody UpdateIncidentDTO request) {
        return ResponseEntity.ok(this.service.updateIncident(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncident(@PathVariable Long id) {
        this.service.deleteIncident(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<IncidentEntityDTO>> getAllIncidents(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(this.service.getAllIncidents(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncidentEntityDTO> getIncidentById(@PathVariable Long id) {
        return ResponseEntity.ok(this.service.getIncidentById(id));
    }

    @GetMapping("/ordered")
    public ResponseEntity<Page<IncidentEntityDTO>> getOrderedIncidents() {
        return ResponseEntity.ok(this.service.getOrderedIncidents());
    }
}
