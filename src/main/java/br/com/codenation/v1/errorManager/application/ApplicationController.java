package br.com.codenation.v1.errorManager.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.*;


@RestController
@RequestMapping("/api/v1/applications")
public class ApplicationController {

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    ApplicationService applicationService;

    @GetMapping
    public List<Application> findApplications(Application filtro){
        ExampleMatcher matcher = ExampleMatcher.matching()
                                        .withIgnoreCase()
                                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example criteria = Example.of(filtro, matcher);

        return applicationRepository.findAll(criteria);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public void insertApplication(@RequestBody ApplicationDTO dto){
        applicationService.saveApplication(dto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteApplication(@PathVariable  Long id){
        applicationRepository.findById(id)
                            .map(a -> {
                                applicationRepository.delete(a);
                                return a;
                            }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Aplicação não encontrada."));
    }


}
