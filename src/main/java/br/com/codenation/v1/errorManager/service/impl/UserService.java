package br.com.codenation.v1.errorManager.service.impl;

import br.com.codenation.v1.errorManager.entity.User;
import br.com.codenation.v1.errorManager.exception.UserNotFoundException;
import br.com.codenation.v1.errorManager.repository.UserRepository;
import br.com.codenation.v1.errorManager.dto.UserDTO;
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

  public void delete(Long id){
    userRepository.findById(id).map(u -> {
      u.setActive(false);
      userRepository.save(u);
      return u;
    }).orElseThrow(UserNotFoundException::new);
  }

  public User update(UserDTO userDTO, Long id) {
    userDTO.setId(id);
    User user = fromDTOUpdate(userDTO);
    User newUser = findById(user.getId());
    updateData(newUser, user);
    return userRepository.save(newUser);
  }

  private void updateData(User newUser, User user) {
    newUser.setUsername(user.getUsername());
  }

  public User fromDTOUpdate(UserDTO userDTO){
    return new User(
        userDTO.getId(),
        userDTO.getUsername(),
        null
    );
  }

  public User fromDTO(UserDTO userDTO) {
    return new User(
        userDTO.getId(),
        userDTO.getUsername(),
        bCryptPasswordEncoder.encode(userDTO.getPassword())
    );
  }
}
