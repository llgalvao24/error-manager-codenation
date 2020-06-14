package br.com.codenation.v1.errorManager.service.interfaces;

import br.com.codenation.v1.errorManager.dto.LogDTO;
import br.com.codenation.v1.errorManager.entity.Log;

import java.util.List;
import java.util.Optional;

public interface LogServiceInterface extends ServiceInterface<Log> {

  Log findById(Long id);

  List<Log> findByApplicationId(Long applicationId);

  List<Log> findByApplicationUserId();

  Log insert(LogDTO log);

}
