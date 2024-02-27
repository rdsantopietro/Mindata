package com.mindata.superHero.utils;

import com.github.javafaker.Faker;
import com.mindata.superHero.domain.SuperHero;

public class RandomSuperHeroGenerator {

    private  Faker faker;

    public RandomSuperHeroGenerator() {
        faker = new Faker();
    }


    public SuperHero getRandomSuperHero() {
        SuperHero superHero =
                SuperHero.builder()
                        .name(faker.superhero().name())
                        .alias(faker.superhero().name())
                        .ability(faker.superhero().power())
                        .build();
        return superHero;

    }
}
