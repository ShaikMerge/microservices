package com.shaik.productCatalog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shaik.productCatalog.dto.ProductRequest;
import com.shaik.productCatalog.dto.ProductResponse;
import com.shaik.productCatalog.repository.ProductRepository;
import org.junit.After;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductCatalogApplicationTests {


	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:latest");

	@Autowired
	MockMvc mockMvc;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ObjectMapper objectMapper;



	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dymDynamicPropertyRegistry) {
		dymDynamicPropertyRegistry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
		dymDynamicPropertyRegistry.add("spring.datasource.username", mySQLContainer::getUsername);
		dymDynamicPropertyRegistry.add("spring.datasource.password", mySQLContainer::getPassword);

	}

	@BeforeAll
	static void startContainer(){
		mySQLContainer.start();
	}

	@AfterAll
	static void endContainer(){
		mySQLContainer.stop();
	}
	@Test
	void contextLoads() {
	}

	@Test
	void testproductCreation() throws Exception {
		ProductRequest productRequest = new ProductRequest("Apple Air Pods pro" , "Special Version with AI", BigDecimal.valueOf(24000));
		String productRequestString = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(productRequestString))
				.andExpect(status().isCreated());
		Assertions.assertEquals(1, productRepository.findAll().size());
	}


}
