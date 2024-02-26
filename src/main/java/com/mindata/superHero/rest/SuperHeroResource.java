package com.mindata.superHero.rest;


import com.mindata.superHero.domain.SuperHero;
import com.mindata.superHero.rest.util.ResponseUtil;
import com.mindata.superHero.service.SuperHeroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/superheroes")
public class SuperHeroResource {

    private final Logger log = LoggerFactory.getLogger(SuperHeroResource.class);


    @Autowired
    private SuperHeroService superHeroService;

    @PostMapping
    public ResponseEntity<SuperHero> saveSuperHero(@RequestBody SuperHero superHero) {
        log.debug("REST request to save SuperHero: {}", superHero);
        SuperHero savedSuperHero = superHeroService.save(superHero);
        return new ResponseEntity<>(savedSuperHero, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<SuperHero> updateSuperHero(@RequestBody SuperHero superHero) {
        log.debug("REST request to update SuperHero: {}", superHero);
        SuperHero updatedSuperHero = superHeroService.update(superHero);
        return ResponseEntity.ok(updatedSuperHero);
    }

    @GetMapping
    public ResponseEntity<List<SuperHero>> getAllSuperHeroes() {
        log.debug("REST request to get all SuperHeroes");
        List<SuperHero> superHeroes = superHeroService.findAll();
        return ResponseEntity.ok(superHeroes);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SuperHero>> findSuperHeroByName(@RequestParam String name) {
        log.debug("REST request to search SuperHeros by name {}", name);
        List<SuperHero> superHeroes = superHeroService.findAllByName(name);
        return ResponseEntity.ok(superHeroes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuperHero> getSuperHeroById(@PathVariable Long id) {
        log.debug("REST request to get SuperHero by id {}", id);
        Optional<SuperHero> superHero = superHeroService.findOne(id);
        return ResponseUtil.wrapOrNotFound(superHeroService.findOne(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSuperHero(@PathVariable Long id) {
        log.debug("REST request to delete SuperHero by id {}", id);
        superHeroService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
