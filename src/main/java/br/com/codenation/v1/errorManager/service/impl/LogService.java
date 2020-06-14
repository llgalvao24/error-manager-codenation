package br.com.codenation.v1.errorManager.service.impl;

import br.com.codenation.v1.errorManager.entity.Application;
import br.com.codenation.v1.errorManager.entity.Log;
import br.com.codenation.v1.errorManager.repository.ApplicationRepository;
import br.com.codenation.v1.errorManager.repository.LogRepository;
import br.com.codenation.v1.errorManager.service.interfaces.LogServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogService implements LogServiceInterface {

  private final LogRepository logRepository;
  private final ApplicationRepository applicationRepository;

  @Autowired
  public LogService(LogRepository logRepository, ApplicationRepository applicationRepository) {
    this.logRepository = logRepository;
    this.applicationRepository = applicationRepository;
  }

  @Override
  public Optional<Log> findById(Long id) {
    return logRepository.findById(id);
  }

  @Override
  public List<Log> findByApplicationId(Long applicationId) {
    return logRepository.findLogByApplicationId(applicationId);
  }
}
