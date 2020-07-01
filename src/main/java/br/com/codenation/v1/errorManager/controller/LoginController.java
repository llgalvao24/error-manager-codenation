package br.com.codenation.v1.errorManager.controller;

import br.com.codenation.v1.errorManager.dto.LoginDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/login")
@Api("Login")
public class LoginController {

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Efetua o login do usuário.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sucesso."),
            @ApiResponse(code = 401, message = "Usuário ou senha inválidos."),
            @ApiResponse(code = 400, message = "Erro de validação.")
    })
    public void login(@RequestBody LoginDTO login){
        throw new IllegalStateException("Método não deve ser chamado.");
    }
}
