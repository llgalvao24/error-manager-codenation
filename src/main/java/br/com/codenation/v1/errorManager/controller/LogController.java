package br.com.codenation.v1.errorManager.controller;

import br.com.codenation.v1.errorManager.dto.LogDTO;
import br.com.codenation.v1.errorManager.entity.Log;
import br.com.codenation.v1.errorManager.service.impl.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/log")
@Api("Log API")
public class LogController {

  private final LogService logService;

  @Autowired
  public LogController(LogService logService) {
    this.logService = logService;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation("Busca todos os logs do usuário autenticado.")
  @ApiResponse(code = 200, message = "Sucesso.")
  public List<LogDTO> findAll(
          @RequestParam(value = "page", defaultValue = "1") Integer pagina,
          @RequestParam(value = "size", defaultValue = "25") Integer tamanhoPagina,
          @RequestParam(value = "orderby", defaultValue = "id") String orderBy
  ){

    return logService.findByApplicationUserId(pagina, tamanhoPagina, orderBy);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation("Busca os detalhes do Log por Id")
  @ApiResponses({
          @ApiResponse(code = 200, message = "Sucesso."),
          @ApiResponse(code = 403, message = "Objeto não é de propriedade do usuário autenticado.")
  })
  public LogDTO findById(@ApiParam("Id do Log") @PathVariable Long id){
    return logService.findById(id);
  }

  @GetMapping("/application/{applicationId}")
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation("Busca os Logs de uma aplicação específica pelo Id desta.")
  @ApiResponses({
          @ApiResponse(code = 200, message = "Sucesso."),
          @ApiResponse(code = 403, message = "Objeto não é de propriedade do usuário autenticado.")
  })
  public List<LogDTO> findByApplicationId(@ApiParam("Id da aplicação") @PathVariable Long applicationId) {
    return logService.findByApplicationId(applicationId);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation("Persiste um novo log na base de dados.")
  @ApiResponses({
          @ApiResponse(code = 201, message = "Criado."),
          @ApiResponse(code = 400, message = "Erro de validação."),
          @ApiResponse(code = 403, message = "Token inválido.")
  })
  public Log insert(@RequestBody LogDTO dto){
    return logService.insert(dto);
  }
}
