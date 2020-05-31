package br.com.codenation.v1.errorManager.application;

import br.com.codenation.v1.errorManager.exception.ApplicationNotFoundException;
import br.com.codenation.v1.errorManager.security.JWTUtil;
import br.com.codenation.v1.errorManager.user.User;
import br.com.codenation.v1.errorManager.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JWTUtil jwtUtil;

    public List<ApplicationInformationDTO> findApplications(Application filtro, String token){
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        filtro.setUser(this.convertUserByToken(token));

        Example criteria = Example.of(filtro, matcher);

        List<Application> applications = applicationRepository.findAll(criteria);

        return convertToResponseDTO(applications);
    }

    public Application saveApplication(ApplicationDTO dto, String token){
        Application application = new Application();
            application.setName(dto.getName());
            application.setUser(this.convertUserByToken(token));

        return applicationRepository.save(application);
    }

    private User convertUserByToken(String token){
        return userRepository.findByUsername(jwtUtil.getUsername(token));
    }

    public void deleteApplication(Long id, String token){
        applicationRepository.findByIdAndUserId(id, this.convertUserByToken(token).getId())
                .map(a -> {
                    a.setActive(false);
                    applicationRepository.save(a);
                    return a;
                }).orElseThrow(() -> new ApplicationNotFoundException());
    }

    private List<ApplicationInformationDTO> convertToResponseDTO(List<Application> applicationList){
        return applicationList.stream()
                    .map(this::convertToApplicationInformationDTO)
                    .collect(Collectors.toList());
    }

    private ApplicationInformationDTO convertToApplicationInformationDTO(Application application){
        ApplicationInformationDTO dto = new ApplicationInformationDTO();
            dto.setId(application.getId());
            dto.setCreatedAt(application.getCreatedAt());
            dto.setName(application.getName());

        return dto;
    }
}
