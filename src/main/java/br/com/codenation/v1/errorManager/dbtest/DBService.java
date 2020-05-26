package br.com.codenation.v1.errorManager.dbtest;

import br.com.codenation.v1.errorManager.enums.Profile;
import br.com.codenation.v1.errorManager.user.User;
import br.com.codenation.v1.errorManager.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DBService {

  private final BCryptPasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  @Autowired
  public DBService(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
  }

  public void instantiateTestDatabase() {

    User user1 = new User(null, "admin@admin.com", passwordEncoder.encode("admin"));
    user1.addProfile(Profile.ADMIN);
    userRepository.save(user1);

    User user2 = new User(null, "test@test.com", passwordEncoder.encode("test"));
    userRepository.save(user2);
  }
}
