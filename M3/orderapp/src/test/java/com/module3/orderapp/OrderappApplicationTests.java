package com.module3.orderapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * Integration tests for the OrderApp application.
 * Tests the complete application context loading and basic functionality.
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class OrderappApplicationTests {

	/**
	 * Test that the Spring application context loads successfully.
	 * This verifies that all beans are properly configured and dependencies are satisfied.
	 */
	@Test
	void contextLoads() {
		// This test will pass if the application context loads without errors
		// It validates that all @Component, @Service, @Controller annotations are properly configured
	}

}
