package br.com.codenation.v1.errorManager.service.impl;

import br.com.codenation.v1.errorManager.dto.AlteraStatusApplicationDTO;
import br.com.codenation.v1.errorManager.dto.ApplicationDTO;
import br.com.codenation.v1.errorManager.dto.ApplicationInfoDTO;
import br.com.codenation.v1.errorManager.entity.Application;
import br.com.codenation.v1.errorManager.exception.ApplicationNotFoundException;
import br.com.codenation.v1.errorManager.exception.OwnershipException;
import br.com.codenation.v1.errorManager.mappers.ApplicationMapper;
import br.com.codenation.v1.errorManager.repository.ApplicationRepository;
import br.com.codenation.v1.errorManager.security.JWTUtil;
import br.com.codenation.v1.errorManager.entity.User;
import br.com.codenation.v1.errorManager.repository.UserRepository;
import br.com.codenation.v1.errorManager.service.interfaces.ApplicationServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationService implements ApplicationServiceInterface {

    private ApplicationRepository applicationRepository;
    private UserRepository userRepository;
    private JWTUtil jwtUtil;
    private ApplicationMapper applicationMapper;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository,
                              UserRepository userRepository,
                              JWTUtil jwtUtil,
                              ApplicationMapper applicationMapper) {
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.applicationMapper = applicationMapper;
    }

    public List<ApplicationInfoDTO> findByUserId(boolean isActive){
        List<Application> applications = applicationRepository.findByUserId(jwtUtil.getAuthenticatedUser().getId());

        applications = applications.stream()
                                    .filter(a -> a.isActive() == isActive)
                                    .collect(Collectors.toList());

        return applicationMapper.map(applications);
    }

    public Application saveApplication(ApplicationDTO name){
        Application application = new Application();
            application.setName(name.getName());
            application.setUser(jwtUtil.getAuthenticatedUser());

        return applicationRepository.save(application);
    }

    @Override
    public ApplicationInfoDTO updateActive(Long id, AlteraStatusApplicationDTO dto) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(ApplicationNotFoundException::new);
        jwtUtil.isAuthorized(application.getUser());
        application.setActive(dto.isActive());

        return applicationMapper.map(applicationRepository.save(application));
    }
}
