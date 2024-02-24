package com.mindata.superHero.service;

import com.mindata.superHero.domain.SuperHero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SuperHeroService {

    SuperHero save(SuperHero superHero);

    Optional<SuperHero> update(SuperHero superHero);

    Optional<SuperHero> partialUpdate(SuperHero superHero);

    Page<SuperHero> findAll();

    Page<SuperHero> findAllByName(String name);

    Optional<SuperHero> findOne(Long id);

    void delete(Long id);

}
