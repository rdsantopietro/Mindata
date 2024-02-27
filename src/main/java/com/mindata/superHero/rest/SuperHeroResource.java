package com.mindata.superHero.rest;


import com.mindata.superHero.domain.SuperHero;
import com.mindata.superHero.error.IdNotNullException;
import com.mindata.superHero.error.IdNullException;
import com.mindata.superHero.rest.util.LogExecutionTime;
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
    @LogExecutionTime
    @ResponseStatus(HttpStatus.CREATED)
    public SuperHero saveSuperHero(@RequestBody SuperHero superHero) throws  IdNotNullException {
        if (superHero.getId() != null){
            throw new IdNotNullException() ;
        }
        log.debug("REST request to save SuperHero: {}", superHero);
        SuperHero savedSuperHero = superHeroService.save(superHero);
        return savedSuperHero;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public SuperHero updateSuperHero(@RequestBody SuperHero superHero)  throws  IdNullException{
        if (superHero.getId() == null){
            throw new IdNullException() ;
        }
        log.debug("REST request to update SuperHero: {}", superHero);
        SuperHero updatedSuperHero = superHeroService.update(superHero);
        return updatedSuperHero;
    }

    @GetMapping
    @LogExecutionTime
    @ResponseStatus(HttpStatus.OK)
    public List<SuperHero> getAllSuperHeroes() {
        log.debug("REST request to get all SuperHeroes");
        List<SuperHero> superHeroes = superHeroService.findAll();
        return superHeroes;
    }

    @GetMapping("/search")
    @LogExecutionTime
    @ResponseStatus(HttpStatus.OK)
    public List<SuperHero> findSuperHeroByName(@RequestParam String name) {
        log.debug("REST request to search SuperHeros by name {}", name);
        List<SuperHero> superHeroes = superHeroService.findAllByName(name);
        return superHeroes;
    }

    @GetMapping("/{id}")
    @LogExecutionTime
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
