package com.mindata.superHero.service;

import com.github.javafaker.Faker;
import com.mindata.superHero.domain.SuperHero;
import com.mindata.superHero.repository.SuperHeroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SuperHeroServiceTest {

    @InjectMocks
    private SuperHeroServiceImpl superHeroService;

    @Mock
    private SuperHeroRepository superHeroRepository;
    private Faker faker;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        faker = new Faker();
    }

    @Test
    public void testCreateSuperHero_shouldSaveSuperHero() {
        SuperHero result = getRandomSuperHero();
        when(superHeroRepository.save(result)).thenReturn(result);
        SuperHero createdSuperHero = superHeroService.save(result);
        assertEquals(result.getName(), createdSuperHero.getName());
        assertEquals(result.getAlias(), createdSuperHero.getAlias());
        assertEquals(result, createdSuperHero);
    }



    @Test
    public void testGetSuperHeroById_shouldToUpdateSuperHero() {

        Long id = 1L;
        SuperHero superHero = getRandomSuperHero();
        when(superHeroRepository.findById(id)).thenReturn(Optional.of(superHero));

        Optional<SuperHero> retrievedSuperHero = superHeroService.findOne(id);

        assertEquals(superHero, retrievedSuperHero);
    }

    @Test
    public void testUpdate_shouldUpdateSuperHero() {
        SuperHero existingSuperHero = getRandomSuperHero();
        existingSuperHero.setId(1L);

        when(superHeroRepository.findById(1L)).thenReturn(Optional.of(existingSuperHero));

        SuperHero updatedSuperHero = SuperHero.builder()
                .name(existingSuperHero.getName())
                .ability(existingSuperHero.getAbility())
                .alias("NEW ALIAS")
                .id(1L)
                .build();

        Optional<SuperHero> updatedOptionalHero = superHeroService.update(updatedSuperHero);

        verify(superHeroRepository).save(updatedSuperHero);
        assertTrue(updatedOptionalHero.isPresent());
        assertEquals(updatedSuperHero, updatedOptionalHero.get());
    }

    @Test
    public void testPartialUpdate_shouldUpdatePartialFields() {
        // Arrange
        SuperHero existingSuperHero = getRandomSuperHero();
        existingSuperHero.setId(2L);
        existingSuperHero.setAlias("OLD ALIAS");

        when(superHeroRepository.findById(2L)).thenReturn(Optional.of(existingSuperHero));

        SuperHero partialUpdateHero = SuperHero.builder().id(2L).alias("NEW ALIAS").build();

        Optional<SuperHero> updatedOptionalHero = superHeroService.partialUpdate(partialUpdateHero);

        verify(superHeroRepository).save(existingSuperHero);
        assertTrue(updatedOptionalHero.isPresent());
        assertNotEquals(existingSuperHero,updatedOptionalHero.get());
        assertEquals("NEW ALIAS", updatedOptionalHero.get().getAlias());
    }

    @Test
    public void testGetAllSuperHeroes_shloudToReturnAllSuperHeros() {
        SuperHero superHero1 = getRandomSuperHero();
        SuperHero superHero2 = getRandomSuperHero();
        List<SuperHero> superHeroes = List.of(superHero1,superHero2);


        when(superHeroRepository.findAll()).thenReturn(superHeroes);
        List<SuperHero> retrievedSuperHeroes = superHeroService.findAll();

        assertEquals(2, retrievedSuperHeroes.size());
        assertIterableEquals(superHeroes, retrievedSuperHeroes);
    }

    @Test
    public void testFindAllByName_shouldReturnSuperHeroesWithName() {
        String name = "name";
        SuperHero superHero1 =  SuperHero.builder().name("name1").id(1L).build();
        SuperHero superHero2 =  SuperHero.builder().name("name2").id(2L).build();
        SuperHero superHero3 =  SuperHero.builder().name("SuperMan").id(3L).build();

        List<SuperHero> expectedSuperHeroes = List.of(superHero1,superHero2);

        when(superHeroRepository.findByNameContainingIgnoreCase(name)).thenReturn(expectedSuperHeroes);

        List<SuperHero> actualSuperHeroes = superHeroService.findAllByName(name);

        assertEquals(expectedSuperHeroes, actualSuperHeroes);
        assertEquals(2, actualSuperHeroes.size());
    }

    @Test
    public void testFindAllByName_shouldReturnEmptyListIfNoMatches() {
        String nameSearched = "NoExistingName";

        when(superHeroRepository.findByNameContainingIgnoreCase(nameSearched)).thenReturn(Collections.emptyList());

        List<SuperHero> actualSuperHeroes = superHeroService.findAllByName(nameSearched);

        assertTrue(actualSuperHeroes.isEmpty());
    }

    @Test
    public void testDeleteSuperHero() {
        superHeroService.delete(1L);

        verify(superHeroRepository, times(1)).deleteById(id);
    }


    private SuperHero getRandomSuperHero() {
        SuperHero superHero =
                SuperHero.builder()
                    .name(faker.superhero().name())
                    .alias(faker.superhero().name())
                    .ability(faker.superhero().power())
                    .build();
        return superHero;

    }

}
