package br.com.codenation.v1.errorManager.service.interfaces;

import br.com.codenation.v1.errorManager.dto.AlteraStatusApplicationDTO;
import br.com.codenation.v1.errorManager.dto.ApplicationDTO;
import br.com.codenation.v1.errorManager.dto.ApplicationInfoDTO;
import br.com.codenation.v1.errorManager.entity.Application;

import java.util.List;

public interface ApplicationServiceInterface extends ServiceInterface<Application>{

    List<ApplicationInfoDTO> findByUserId(boolean active);

    Application saveApplication(ApplicationDTO name);

    ApplicationInfoDTO updateActive(Long id, AlteraStatusApplicationDTO dto);

}
