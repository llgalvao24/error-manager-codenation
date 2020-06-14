package br.com.codenation.v1.errorManager.dbtest;

import br.com.codenation.v1.errorManager.entity.Application;
import br.com.codenation.v1.errorManager.enums.Profile;
import br.com.codenation.v1.errorManager.entity.User;
import br.com.codenation.v1.errorManager.repository.ApplicationRepository;
import br.com.codenation.v1.errorManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DBService {

  private final BCryptPasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final ApplicationRepository appRepository;

  @Autowired
  public DBService(
      BCryptPasswordEncoder passwordEncoder,
      UserRepository userRepository,
      ApplicationRepository appRepository
  ) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.appRepository = appRepository;
  }

  public void instantiateTestDatabase() {

    User user1 = new User(null, "admin@admin.com", passwordEncoder.encode("admin"));
    user1.addProfile(Profile.ADMIN);
    userRepository.save(user1);

    User user2 = new User(null, "test@test.com", passwordEncoder.encode("test"));
    userRepository.save(user2);

    Application app1 = new Application(null, "app1", user2);
    appRepository.save(app1);

    Application app2 = new Application(null, "app2", user2);
    appRepository.save(app2);
  }
}
