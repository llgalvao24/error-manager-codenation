package br.com.codenation.v1.errorManager.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/users")
  public List<User> findAll(){
    return userService.findAll();
  }

  @PostMapping("/user")
  public User insert(@Valid @RequestBody User user){
    return userService.inset(user);
  }


}
