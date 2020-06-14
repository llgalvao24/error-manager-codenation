package br.com.codenation.v1.errorManager.service.interfaces;

import br.com.codenation.v1.errorManager.entity.Log;

import java.util.List;
import java.util.Optional;

public interface LogServiceInterface extends ServiceInterface<Log> {

  Optional<Log> findById(Long id);

  List<Log> findByApplicationId(Long applicationId);
}
