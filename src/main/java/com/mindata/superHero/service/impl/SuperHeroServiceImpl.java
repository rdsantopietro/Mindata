package com.mindata.superHero.service.impl;

import com.mindata.superHero.domain.SuperHero;
import com.mindata.superHero.repository.SuperHeroRepository;
import com.mindata.superHero.service.SuperHeroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;


@Service
public class SuperHeroServiceImpl implements SuperHeroService {

    private static Logger log = LoggerFactory.getLogger(SuperHeroServiceImpl.class);

    @Autowired
    private SuperHeroRepository superHeroRepository;


    @Override
    public SuperHero save(SuperHero superHero) {
        log.debug("Request to save SuperHero: {}", superHero);
        Assert.notNull(superHero, "Param shouldn't be null");
        return superHeroRepository.save(superHero);
    }

    @Override
    public SuperHero update(SuperHero superHero) {
        log.debug("Request to update SuperHero: {}", superHero);
        Assert.notNull(superHero, "Param shouldn't be null");
        return superHeroRepository.save(superHero);
    }


    @Override
    public List<SuperHero> findAll() {
        log.debug("Request to find all SuperHeros");
        return superHeroRepository.findAll();
    }

    @Override
    public List<SuperHero> findAllByName(String name) {
        log.debug("Request to find all SuperHeros by name: ", name);
        return superHeroRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public Optional<SuperHero> findOne(Long id) {
        log.debug("Request to find SuperHero by id : {}", id);
        return superHeroRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SuperHero by id : {}", id);
         superHeroRepository.deleteById(id);
    }
}
