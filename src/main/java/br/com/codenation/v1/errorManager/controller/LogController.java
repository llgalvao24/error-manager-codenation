package br.com.codenation.v1.errorManager.controller;

import br.com.codenation.v1.errorManager.dto.ArquivaLogDTO;
import br.com.codenation.v1.errorManager.dto.LogDTO;
import br.com.codenation.v1.errorManager.dto.LogInfoDTO;
import br.com.codenation.v1.errorManager.service.impl.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/log", produces = "application/json")
@Api("Log API")
public class LogController {

  private final LogService logService;

  @Autowired
  public LogController(LogService logService) {
    this.logService = logService;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation("Busca todos os logs não arquivados do usuário autenticado.")
  @ApiResponses({
          @ApiResponse(code=200, message = "Sucesso."),
          @ApiResponse(code=400, message = "Erro de validação."),
  })
  public List<LogInfoDTO> findAll(
      @ApiParam("Número da página a ser visualizada.")    @RequestParam(value = "page", defaultValue = "1") Integer pagina,
      @ApiParam("Quantos logs por página.")    @RequestParam(value = "size", defaultValue = "25") Integer tamanhoPagina,
      @ApiParam("Por qual campo ordenar.")    @RequestParam(value = "orderby", defaultValue = "id") String orderBy
  ){

    return logService.findByApplicationUserId(pagina, tamanhoPagina, orderBy, false);
  }

  @GetMapping("/archived")
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation("Busca todos os logs arquivados do usuário autenticado")
  @ApiResponses({
          @ApiResponse(code=200, message = "Sucesso."),
          @ApiResponse(code=400, message = "Erro de validação."),
  })
  public List<LogInfoDTO> findAllArchived(
          @ApiParam("Número da página a ser visualizada.")    @RequestParam(value = "page", defaultValue = "1") Integer pagina,
          @ApiParam("Quantos logs por página.")    @RequestParam(value = "size", defaultValue = "25") Integer tamanhoPagina,
          @ApiParam("Por qual campo ordenar.")    @RequestParam(value = "orderby", defaultValue = "id") String orderBy
  ){
    return logService.findByApplicationUserId(pagina, tamanhoPagina, orderBy, true);
  }


  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation("Busca os detalhes do Log por Id")
  @ApiResponses({
          @ApiResponse(code = 200, message = "Sucesso."),
          @ApiResponse(code = 403, message = "Objeto não é de propriedade do usuário autenticado.")
  })
  public LogInfoDTO findById(@ApiParam("Id do Log") @PathVariable Long id){
    return logService.findById(id);
  }

  @GetMapping("/application/{applicationId}")
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation("Busca os Logs de uma aplicação específica pelo Id desta.")
  @ApiResponses({
          @ApiResponse(code = 200, message = "Sucesso."),
          @ApiResponse(code = 403, message = "Objeto não é de propriedade do usuário autenticado.")
  })
  public List<LogInfoDTO> findByApplicationId(@ApiParam("Id da aplicação") @PathVariable Long applicationId) {
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
  public LogInfoDTO insert(@ApiParam("Log a ser informado para a API.") @RequestBody LogDTO log){
    return logService.insert(log);
  }

  @PatchMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation("Arquiva/desarquiva um log na base de dados.")
  @ApiResponses({
          @ApiResponse(code = 200, message = "Sucesso"),
          @ApiResponse(code = 400, message = "Erro de validação."),
          @ApiResponse(code = 403, message = "Token inválido ou não informado.")
  })
  public LogInfoDTO archive(@PathVariable Long id, @RequestBody ArquivaLogDTO archived){
    return logService.archive(id, archived);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ApiOperation("Deleta um log na base de dados.")
  @ApiResponses({
          @ApiResponse(code = 204, message = "No content"),
          @ApiResponse(code = 400, message = "Erro de validação."),
          @ApiResponse(code = 403, message = "Token inválido ou não informado.")
  })
  public void delete(@PathVariable Long id){
    logService.delete(id);
  }
}
