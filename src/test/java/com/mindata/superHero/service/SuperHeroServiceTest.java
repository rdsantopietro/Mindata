package com.mindata.superHero.service;

import com.github.javafaker.Faker;
import com.mindata.superHero.domain.SuperHero;
import com.mindata.superHero.repository.SuperHeroRepository;
import com.mindata.superHero.service.impl.SuperHeroServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
        //Arrange
        SuperHero result = getRandomSuperHero();
        when(superHeroRepository.save(result)).thenReturn(result);

        //Act
        SuperHero createdSuperHero = superHeroService.save(result);

        //Assert
        assertEquals(result.getName(), createdSuperHero.getName());
        assertEquals(result.getAlias(), createdSuperHero.getAlias());
        assertEquals(result, createdSuperHero);
    }



    @Test
    public void testGetSuperHeroById_shouldTogetSuperHero() {
        //Arrange
        Long id = 1L;
        SuperHero superHero = getRandomSuperHero();
        when(superHeroRepository.findById(id)).thenReturn(Optional.of(superHero));

        //Act
        Optional<SuperHero> retrievedSuperHero = superHeroService.findOne(id);

        //Assert
        assertThat(retrievedSuperHero.isPresent());
        assertEquals(superHero, retrievedSuperHero.get());
    }

    @Test
    public void testUpdateSuperHero_shouldUpdateSuperHero() {
        //Arrange
        SuperHero existingSuperHero = getRandomSuperHero();
        existingSuperHero.setId(1L);

        SuperHero updatedSuperHero = SuperHero.builder()
                .name(existingSuperHero.getName())
                .ability(existingSuperHero.getAbility())
                .alias("NEW ALIAS")
                .id(1L)
                .build();
        when(superHeroRepository.save(updatedSuperHero)).thenReturn(updatedSuperHero);

        //Act
        SuperHero updatedOptionalHero = superHeroService.update(updatedSuperHero);

        //Assert
        verify(superHeroRepository).save(updatedSuperHero);
        assertNotNull(updatedOptionalHero);
        assertEquals(updatedSuperHero, updatedOptionalHero);
    }

    @Test
    public void testCreateSuperHeroWithNullParam_shouldToThrowExecption() {
        // Act & Assert

        assertThrows(IllegalArgumentException.class, () -> {
            superHeroService.save (null);
        });
    }

    @Test
    public void testUpdateSuperHeroWithNullParam_shouldToThrowExecption() {
        // Arrange
        Long usuarioId = 1L;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            superHeroService.update(null);
        });
    }



    @Test
    public void testGetAllSuperHeroes_shloudToReturnAllSuperHeros() {
        //Arrange
        SuperHero superHero1 = getRandomSuperHero();
        SuperHero superHero2 = getRandomSuperHero();
        List<SuperHero> superHeroes = List.of(superHero1,superHero2);

        when(superHeroRepository.findAll()).thenReturn(superHeroes);

        //Act
        List<SuperHero> retrievedSuperHeroes = superHeroService.findAll();

        //Assert
        assertEquals(2, retrievedSuperHeroes.size());
        assertIterableEquals(superHeroes, retrievedSuperHeroes);
    }

    @Test
    public void testFindAllByName_shouldReturnSuperHeroesWithName() {
        //Arrange
        String name = "name";
        SuperHero superHero1 =  SuperHero.builder().name("name1").id(1L).build();
        SuperHero superHero2 =  SuperHero.builder().name("name2").id(2L).build();
        SuperHero superHero3 =  SuperHero.builder().name("SuperMan").id(3L).build();

        List<SuperHero> expectedSuperHeroes = List.of(superHero1,superHero2);

        when(superHeroRepository.findByNameContainingIgnoreCase(name)).thenReturn(expectedSuperHeroes);

        //Act
        List<SuperHero> actualSuperHeroes = superHeroService.findAllByName(name);

        //Assert
        assertEquals(expectedSuperHeroes, actualSuperHeroes);
        assertEquals(2, actualSuperHeroes.size());
    }

    @Test
    public void testFindAllByName_shouldReturnEmptyListIfNoMatches() {
        //Arrance
        String nameSearched = "NoExistingName";

        when(superHeroRepository.findByNameContainingIgnoreCase(nameSearched)).thenReturn(Collections.emptyList());

        //Act
        List<SuperHero> actualSuperHeroes = superHeroService.findAllByName(nameSearched);

        //Assert
        assertTrue(actualSuperHeroes.isEmpty());
    }

    @Test
    public void testDeleteSuperHero() {
        //Act
        superHeroService.delete(1L);

        //Assert
        verify(superHeroRepository, times(1)).deleteById(1L);
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
