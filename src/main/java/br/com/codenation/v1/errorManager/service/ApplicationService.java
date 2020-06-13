package br.com.codenation.v1.errorManager.service;

import br.com.codenation.v1.errorManager.dto.ApplicationDTO;
import br.com.codenation.v1.errorManager.dto.ApplicationInfoDTO;
import br.com.codenation.v1.errorManager.entity.Application;
import br.com.codenation.v1.errorManager.exception.ApplicationNotFoundException;
import br.com.codenation.v1.errorManager.exception.OwnershipException;
import br.com.codenation.v1.errorManager.repository.ApplicationRepository;
import br.com.codenation.v1.errorManager.security.JWTUtil;
import br.com.codenation.v1.errorManager.entity.User;
import br.com.codenation.v1.errorManager.repository.UserRepository;
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

    public List<ApplicationInfoDTO> findApplications(ApplicationInfoDTO filtro){
        Application application = this.convertToApplication(filtro);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        application.setUser(jwtUtil.getAuthenticatedUser());

        Example criteria = Example.of(application, matcher);

        List<Application> applications = applicationRepository.findAll(criteria);

        return convertToResponseDTO(applications);
    }

    public List<ApplicationInfoDTO> findByUserId(){
        List<Application> applications = applicationRepository.findByUserId(jwtUtil.getAuthenticatedUser().getId());

        return convertToResponseDTO(applications);
    }

    public Application saveApplication(ApplicationDTO name){
        Application application = new Application();
            application.setName(name.getName());
            application.setUser(jwtUtil.getAuthenticatedUser());

        return applicationRepository.save(application);
    }

    public void deleteApplication(Long id){
        applicationRepository.findById(id)
                .map(a -> {
                    jwtUtil.isAuthorized(a.getUser());
                    a.setActive(false);
                    applicationRepository.save(a);
                    return a;
                }).orElseThrow(() -> new ApplicationNotFoundException());
    }


    private List<ApplicationInfoDTO> convertToResponseDTO(List<Application> applicationList){
        return applicationList.stream()
                    .map(this::convertToApplicationInformationDTO)
                    .collect(Collectors.toList());
    }
    private Application convertToApplication(ApplicationInfoDTO dto){
        Application application = new Application();
            application.setActive(dto.isActive());
            application.setCreatedAt(dto.getCreatedAt());
            application.setId(dto.getId());
        return application;
    }
    private ApplicationInfoDTO convertToApplicationInformationDTO(Application application){
        ApplicationInfoDTO dto = new ApplicationInfoDTO();
            dto.setId(application.getId());
            dto.setCreatedAt(application.getCreatedAt());
            dto.setName(application.getName());
            dto.setActive(application.isActive());

        return dto;
    }
}
