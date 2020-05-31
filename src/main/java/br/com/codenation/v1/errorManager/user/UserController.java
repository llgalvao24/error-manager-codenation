package br.com.codenation.v1.errorManager.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Api("API User")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/users")
  @ApiOperation("Buscar todos os usuários ativos.")
  public List<User> findAll(){
    return userService.findAll();
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @PostMapping("/user")
  @ApiOperation("Insere um novo usuário (necessário ter perfil de ADMIN).")
  public User insert(@Valid @RequestBody User user){
    return userService.inset(user);
  }


}
