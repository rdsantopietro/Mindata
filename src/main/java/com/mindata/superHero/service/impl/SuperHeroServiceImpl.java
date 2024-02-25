package com.mindata.superHero.service.impl;

import com.mindata.superHero.domain.SuperHero;
import com.mindata.superHero.repository.SuperHeroRepository;
import com.mindata.superHero.service.SuperHeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class SuperHeroServiceImpl implements SuperHeroService {

    @Autowired
    private SuperHeroRepository superHeroRepository;


    @Override
    public SuperHero save(SuperHero superHero) {
        Assert.notNull(superHero, "Param shouldn't be null");
        return superHeroRepository.save(superHero);
    }

    @Override
    public SuperHero update(SuperHero superHero) {
        Assert.notNull(superHero, "Param shouldn't be null");
        return superHeroRepository.save(superHero);
    }


    @Override
    public List<SuperHero> findAll() {
        return superHeroRepository.findAll();
    }

    @Override
    public List<SuperHero> findAllByName(String name) {
        return superHeroRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public Optional<SuperHero> findOne(Long id) {
        return superHeroRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
         superHeroRepository.deleteById(id);
    }
}
