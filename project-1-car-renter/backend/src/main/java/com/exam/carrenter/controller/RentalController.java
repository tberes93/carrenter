package com.exam.carrenter.controller;

import com.exam.carrenter.model.Rental;
import com.exam.carrenter.model.interfaces.RentalRepository;
import com.exam.carrenter.model.interfaces.UserRepository;
import com.exam.carrenter.services.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.exam.carrenter.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {
    private final RentalService service;
    private final UserRepository users;

    @Autowired
    private  RentalRepository rentalRepo;

    public RentalController(RentalService service, UserRepository users) { this.service = service; this.users = users; }

    @GetMapping
    public List<Rental> list(@RequestParam(required=false) @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                              @RequestParam(required=false) @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime to){
        return rentalRepo.findInRange(from, to);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Rental b, Authentication auth) {
        String email = (String)auth.getPrincipal();
        UUID userId = users.findByEmail(email).map(User::getId).orElseThrow();
        b.setUserId(userId);
        try { return ResponseEntity.ok(service.create(b)); }
        catch (IllegalArgumentException ex) { return ResponseEntity.status(409).body(ex.getMessage()); }
    }


}
