package br.com.codenation.v1.errorManager.application;

import br.com.codenation.v1.errorManager.exception.UserNaoEncontradoException;
import br.com.codenation.v1.errorManager.user.User;
import br.com.codenation.v1.errorManager.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class ApplicationService {

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    UserRepository userRepository;

    public List<Application> findApplications(Application filtro){
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example criteria = Example.of(filtro, matcher);

        return applicationRepository.findAll(criteria);
    }

    public Application saveApplication(ApplicationDTO dto){
        User user = convertUser(dto.getUserId());

        Application application = new Application();
            application.setName(dto.getName());
            application.setUser(user);

        return applicationRepository.save(application);
    }

    private User convertUser(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNaoEncontradoException());
    }

    public void deleteApplication(Long id){
        applicationRepository.findById(id)
                .map(a -> {
                    a.setActive(false);
                    applicationRepository.save(a);
                    return a;
                }).orElseThrow(() -> new UserNaoEncontradoException());
    }
}
