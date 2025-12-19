package com.techelevator.controller;

import com.techelevator.entity.PetParent;
import com.techelevator.repository.PetParentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/petparents")
public class PetParentController {

    private final PetParentRepository repository;

    public PetParentController(PetParentRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<PetParent> getAllPetParents() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetParent> getPetParentById(@PathVariable Integer id) {
        Optional<PetParent> parent = repository.findById(id);
        return parent.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'VOLUNTEER')")
    @PostMapping("/addPetParent")
    public PetParent addPetParent(@RequestBody PetParent parent) {
        return repository.save(parent);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'VOLUNTEER')")
    @PutMapping("/{id}")
    public ResponseEntity<PetParent> updatePetParent(@PathVariable Integer id, @RequestBody PetParent parent) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        parent.setParentId(Long.valueOf(id));
        return ResponseEntity.ok(repository.save(parent));
    }
}
