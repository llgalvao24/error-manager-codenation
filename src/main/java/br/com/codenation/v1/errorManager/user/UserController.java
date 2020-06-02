package br.com.codenation.v1.errorManager.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Api("API User")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/user/{id}")
  @ResponseStatus(HttpStatus.OK)
  public User findById(@PathVariable Long id){
    return userService.findById(id);
  }

  @GetMapping("/users")
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation("Buscar todos os usuários ativos.")
  public List<User> findAll(){
    return userService.findAll();
  }

  @PostMapping("/user")
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation("Insere um novo usuário (necessário ter perfil de ADMIN).")
  public User insert(@Valid @RequestBody UserDTO userDTO){
    return userService.inset(userDTO);
  }

  @PutMapping("/user/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void update(@Valid @RequestBody UserDTO userDTO, @PathVariable Long id) {
    userService.update(userDTO, id);
  }

  @DeleteMapping("/user/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id){
    userService.delete(id);
  }
}
