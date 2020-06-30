package br.com.codenation.v1.errorManager.config;

import br.com.codenation.v1.errorManager.dbtest.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {

  private final DBService dbService;

  @Autowired
  public TestConfig(DBService dbService) {
    this.dbService = dbService;
  }

  @Bean
  public boolean instantiateDatabase() {
    dbService.instantiateTestDatabase();
    return true;
  }

}
