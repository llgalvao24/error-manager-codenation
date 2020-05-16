package br.com.codenation.v1.errorManager.dbtest;

import br.com.codenation.v1.errorManager.enums.Profile;
import br.com.codenation.v1.errorManager.user.User;
import br.com.codenation.v1.errorManager.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DBService {

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  private UserRepository userRepository;

  public void instantiateTestDatabase() {

    User user1 = new User("admin@admin.com", passwordEncoder.encode("admin"));
    user1.addProfile(Profile.ADMIN);
    userRepository.save(user1);
  }
}
