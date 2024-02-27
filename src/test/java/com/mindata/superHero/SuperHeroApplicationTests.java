package com.mindata.superHero;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindata.superHero.domain.SuperHero;
import com.mindata.superHero.error.IdNotNullException;
import com.mindata.superHero.error.IdNullException;
import com.mindata.superHero.utils.RandomSuperHeroGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SuperHeroApplicationTests {

	@Autowired
	MockMvc mvc;

	private RandomSuperHeroGenerator randomSuperHeroGenerator;
	private ObjectMapper objectMapper;

	private final static String RESOURCE_PATH = "/superheroes";

	@BeforeEach
    public void setup(){
		randomSuperHeroGenerator = new RandomSuperHeroGenerator();
		objectMapper = new ObjectMapper();

	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void createSuperHero_shouldToCreateSuperHero() throws Exception {
		SuperHero superHero = randomSuperHeroGenerator.getRandomSuperHero();
		SuperHero result = SuperHero.builder().id(1L)
				.name(superHero.getName())
				.alias(superHero.getAlias())
				.ability(superHero.getAbility()).build();

		mvc.perform(post(RESOURCE_PATH,superHero)
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(superHero)))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value(result.getName()));
	}

	@Test
	public void testPostSuperHeroWhitNotNullId_shouldToReturnBadRequest() throws  Exception {
		SuperHero superHero = randomSuperHeroGenerator.getRandomSuperHero();
		superHero.setId(1L);

		mvc.perform(post("/superheroes")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(superHero)))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof IdNotNullException))
				.andExpect(content().string(containsString("ID must be null for this operation")));
	}

	@Test
	public void testPutSuperHeroWhitNullId_shouldToReturnBadRequest() throws  Exception {
		SuperHero superHero = randomSuperHeroGenerator.getRandomSuperHero();

		mvc.perform(put("/superheroes")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(superHero)))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof IdNullException));

	}

	@Test
	public void testPutSuperHero_shouldToUpdateSuperHero() throws  Exception {
		SuperHero superHero = randomSuperHeroGenerator.getRandomSuperHero();
		superHero.setId(1L);

		mvc.perform(put("/superheroes")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(superHero)))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value(superHero.getName()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.alias").value(superHero.getAlias()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(superHero.getId()));
	}

}
