package br.com.codenation.v1.errorManager.dbtest;

import br.com.codenation.v1.errorManager.entity.Application;
import br.com.codenation.v1.errorManager.entity.Log;
import br.com.codenation.v1.errorManager.entity.User;
import br.com.codenation.v1.errorManager.enums.Level;
import br.com.codenation.v1.errorManager.enums.Profile;
import br.com.codenation.v1.errorManager.repository.ApplicationRepository;
import br.com.codenation.v1.errorManager.repository.LogRepository;
import br.com.codenation.v1.errorManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DBService {

  private final BCryptPasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final ApplicationRepository appRepository;
  private final LogRepository logRepository;

  @Autowired
  public DBService(
      BCryptPasswordEncoder passwordEncoder,
      UserRepository userRepository,
      ApplicationRepository appRepository,
      LogRepository logRepository
  ) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.appRepository = appRepository;
    this.logRepository = logRepository;
  }

  public void instantiateTestDatabase() {

    User user1 = new User(null, "admin1@admin.com", passwordEncoder.encode("admin"));
    user1.addProfile(Profile.ADMIN);
    userRepository.save(user1);

    User user2 = new User(null, "test@test.com", passwordEncoder.encode("test"));
    userRepository.save(user2);

    Application app1 = new Application(null, "app1", user1);
    appRepository.save(app1);

    Application app2 = new Application(null, "app2", user2);
    appRepository.save(app2);

    Log log1 = new Log(null,
            "Erro Teste App 1",
            "Erro salvo no momento da criação como teste",
            "NullPointerException",
            Level.WARNING,
            app1,
            "PRODUÇÃO");
    log1.addEvent();

    Log log2 = new Log(null,
            "Segundo Erro Teste App 2",
            "Erro salvo no momento da criação como teste para avaliar o comportamento",
            "ApplicationNotFoundException",
            Level.ERROR,
            app2,
            "PRODUÇÃO");
    log2.addEvent();

    Log log3 = new Log(null,
            "Erro Teste App 2",
            "Erro salvo no momento da criação como teste",
            "NullPointerException",
            Level.WARNING,
            app2,
            "HOMOLOGAÇÃO");
    log3.addEvent();

    Log log4 = new Log(null,
            "Segundo Erro Teste App 1",
            "Erro salvo no momento da criação como teste para avaliar o comportamento",
            "ApplicationNotFoundException",
            Level.INFO,
            app1,
            "TESTE");
    log4.addEvent();

    logRepository.save(log1);
    logRepository.save(log2);
    logRepository.save(log3);
    logRepository.save(log4);
  }
}
