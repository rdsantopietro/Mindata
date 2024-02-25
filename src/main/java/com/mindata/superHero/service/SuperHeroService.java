package com.mindata.superHero.service;

import com.mindata.superHero.domain.SuperHero;

import java.util.List;
import java.util.Optional;

public interface SuperHeroService {

    SuperHero save(SuperHero superHero);

    Optional<SuperHero> update(SuperHero superHero);

    Optional<SuperHero> partialUpdate(SuperHero superHero);

    List<SuperHero> findAll();

    List<SuperHero> findAllByName(String name);

    Optional<SuperHero> findOne(Long id);

    void delete(Long id);

}
