package br.com.codenation.v1.errorManager.controller;

import br.com.codenation.v1.errorManager.dto.AlteraStatusApplicationDTO;
import br.com.codenation.v1.errorManager.dto.ApplicationDTO;
import br.com.codenation.v1.errorManager.dto.ApplicationInfoDTO;
import br.com.codenation.v1.errorManager.service.impl.ApplicationService;
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

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/v1/applications")
@Api("API Application")
public class ApplicationController {

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    @ApiOperation("Obtém todos as aplicações do usuário, ativa ou inativa")
    @ApiResponse(code = 200, message = "Sucesso.")
    public List<ApplicationInfoDTO> findAll(@RequestParam(value = "active", defaultValue = "true") boolean active){
        return applicationService.findByUserId(active);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Persiste uma nova aplicação.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Aplicação salva com sucesso."),
            @ApiResponse(code = 400, message = "Erro de validação.")
    })
    public void insertApplication(@Valid @RequestBody ApplicationDTO name){
        applicationService.saveApplication(name);
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Desativa/Reativa uma aplicação.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Aplicação desativada com sucesso."),
            @ApiResponse(code = 400, message = "Erro de validação."),
            @ApiResponse(code = 403, message = "O objeto solicitado não é de propriedade do usuário autenticado.")
    })
    public ApplicationInfoDTO updateActive(@ApiParam("Id da aplicação") @PathVariable  Long id,
                                           @ApiParam("Se application deve ser ativo ou não") @RequestBody AlteraStatusApplicationDTO status){
        return applicationService.updateActive(id, status);
    }
}
