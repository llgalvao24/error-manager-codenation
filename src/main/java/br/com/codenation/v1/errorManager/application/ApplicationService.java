package br.com.codenation.v1.errorManager.application;

import br.com.codenation.v1.errorManager.exception.UserNaoEncontradoException;
import br.com.codenation.v1.errorManager.user.User;
import br.com.codenation.v1.errorManager.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    UserRepository userRepository;

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
}
