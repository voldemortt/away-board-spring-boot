package com.awayboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The Class AwayBoardApplication.
 */
@SpringBootApplication
@EnableJpaRepositories
public class AwayBoardApplication {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(AwayBoardApplication.class, args);
	}
}
