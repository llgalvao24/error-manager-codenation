package br.com.codenation.v1.errorManager.user;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService  {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  public User findById(Long id) {
    Optional<User> user = userRepository.findById(id);
    return user.orElseThrow(
        () -> new ObjectNotFoundException("User not found! Id: " + id, User.class.getName())
    );
  }

  public List<User> findAll() {
    return userRepository.findAll();
  }

  public User inset(UserDTO userDTO){
    return userRepository.save(fromDTO(userDTO));
  }

  public User fromDTO(UserDTO userDTO) {
    return new User(
        userDTO.getId(),
        userDTO.getUsername(),
        bCryptPasswordEncoder.encode(userDTO.getUsername())
    );
  }
}
