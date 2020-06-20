package br.com.codenation.v1.errorManager.service.impl;

import br.com.codenation.v1.errorManager.dto.UserInfoDTO;
import br.com.codenation.v1.errorManager.entity.User;
import br.com.codenation.v1.errorManager.exception.UserNotFoundException;
import br.com.codenation.v1.errorManager.mappers.UserMapper;
import br.com.codenation.v1.errorManager.repository.UserRepository;
import br.com.codenation.v1.errorManager.dto.UserDTO;
import br.com.codenation.v1.errorManager.service.interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserServiceInterface {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final UserMapper userMapper;

  @Autowired
  public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, UserMapper userMapper) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.userMapper = userMapper;
  }

  public User findById(Long id) {
    return userRepository.findById(id)
            .orElseThrow(UserNotFoundException::new);
  }

  public UserInfoDTO findByIdInfo(Long id) {
    User user = userRepository.findById(id)
            .orElseThrow(UserNotFoundException::new);

    return userMapper.map(user);
  }

  public List<UserInfoDTO> findAll() {
    List<User> users = userRepository.findAll();
    return userMapper.map(users);
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
