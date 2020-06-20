package br.com.codenation.v1.errorManager.controller;

import br.com.codenation.v1.errorManager.dto.UserInfoDTO;
import br.com.codenation.v1.errorManager.entity.User;
import br.com.codenation.v1.errorManager.service.impl.UserService;
import br.com.codenation.v1.errorManager.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
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
  @ApiOperation("Lista um User dado um id.")
  @ApiResponses({
      @ApiResponse(code = 200, message = "Sucesso."),
      @ApiResponse(code = 401, message = "Não autorizado"),
      @ApiResponse(code = 403, message = "Acesso proibido."),
      @ApiResponse(code = 404, message = "Não encontrado.")
  })
  public UserInfoDTO findById(@PathVariable Long id){
    return userService.findByIdInfo(id);
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @GetMapping("/users")
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation("Busca todos os usuários ativos.")
  public List<UserInfoDTO> findAll(){
    return userService.findAll();
  }

  @PostMapping("/user")
  @ResponseStatus(HttpStatus.CREATED)
  @ApiResponses({
      @ApiResponse(code = 201, message = "User salvo com sucesso."),
      @ApiResponse(code = 400, message = "Erro de validação.")
  })
  public User insert(@Valid @RequestBody UserDTO userDTO){
    return userService.inset(userDTO);
  }

  @PutMapping("/user/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ApiOperation( value = "Update um User")
  @ApiResponses({
      @ApiResponse( code = 404, message = "Not found." )
  })
  public void update(@Valid @RequestBody UserDTO userDTO, @PathVariable Long id) {
    userService.update(userDTO, id);
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @DeleteMapping("/uder/{id}")
  @ApiOperation("Desativa um usuário com base no id")
  @ApiResponses({
      @ApiResponse( code = 404, message = "Not found." )
  })
  public void delete(@Param("id") Long id){
    userService.delete(id);
  }
}
