package br.com.codenation.v1.errorManager.security;

import br.com.codenation.v1.errorManager.user.User;
import br.com.codenation.v1.errorManager.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);

    if(user == null){
      throw new UsernameNotFoundException(username);
    }

    return new UserSecurity(user.getId(), user.getUsername(), user.getPassword(), user.getProfile());
  }
}
