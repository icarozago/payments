package com.icaro.payments;

import java.util.ResourceBundle;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icaro.payments.containers.CustomPostgreSQLContainer;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BasicApplicationIntegrationTest {

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;
	
	@Autowired
	protected ResourceBundle bundle;

	static {

		PostgreSQLContainer<CustomPostgreSQLContainer> postgresSQLContainer = CustomPostgreSQLContainer.getInstance();

		if (!postgresSQLContainer.isRunning()) {
			postgresSQLContainer.start();
		}
	}
	
	

}
