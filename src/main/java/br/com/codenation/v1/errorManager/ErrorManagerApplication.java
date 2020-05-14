package br.com.codenation.v1.errorManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ErrorManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErrorManagerApplication.class, args);
	}

}
