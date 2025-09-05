package com.exam.carrenter.controller;

import com.exam.carrenter.model.Car;
import com.exam.carrenter.model.interfaces.CarRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    private final CarRepository repo;
    public CarController(CarRepository repo) { this.repo = repo; }


    @GetMapping public List<Car> list() { return repo.findAll(); }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Car create(@RequestBody @Valid Car r) { return repo.save(r); }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Car update(@PathVariable UUID id, @RequestBody Car r) {
        r.setId(id); return repo.save(r);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build(); // 204
    }
}
