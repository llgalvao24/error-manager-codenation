package br.com.codenation.v1.errorManager.user;

import br.com.codenation.v1.errorManager.application.ApplicationRepository;
import br.com.codenation.v1.errorManager.exception.UserNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService  {

  @Autowired
  UserRepository userRepository;

  @Autowired
  ApplicationRepository applicationRepository;

  @Autowired
  BCryptPasswordEncoder bCryptPasswordEncoder;

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public User inset(User user){
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    userRepository.save(user);
    return user;
  }
  public void delete(Long id){
    userRepository.findById(id)
                .map(u -> {
                  u.setActive(false);
                  userRepository.save(u);
                  return u;
                }).orElseThrow(() -> new UserNaoEncontradoException());
  }
}
