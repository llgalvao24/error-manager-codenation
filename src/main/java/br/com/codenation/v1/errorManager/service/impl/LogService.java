package br.com.codenation.v1.errorManager.service.impl;

import br.com.codenation.v1.errorManager.dto.LogDTO;
import br.com.codenation.v1.errorManager.entity.Application;
import br.com.codenation.v1.errorManager.entity.Log;
import br.com.codenation.v1.errorManager.enums.Level;
import br.com.codenation.v1.errorManager.exception.ApplicationNotFoundException;
import br.com.codenation.v1.errorManager.exception.LogNotFoundException;
import br.com.codenation.v1.errorManager.exception.PageableDefinitionException;
import br.com.codenation.v1.errorManager.repository.ApplicationRepository;
import br.com.codenation.v1.errorManager.repository.LogRepository;
import br.com.codenation.v1.errorManager.security.JWTUtil;
import br.com.codenation.v1.errorManager.service.interfaces.LogServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService implements LogServiceInterface {

  private final LogRepository logRepository;
  private final ApplicationRepository applicationRepository;
  private final JWTUtil jwtUtil;

  @Autowired
  public LogService(LogRepository logRepository, ApplicationRepository applicationRepository, JWTUtil jwtUtil) {
    this.logRepository = logRepository;
    this.applicationRepository = applicationRepository;
    this.jwtUtil = jwtUtil;
  }

  @Override
  public Log findById(Long id) {
    Log log = logRepository.findById(id)
                .orElseThrow(LogNotFoundException::new);

    this.jwtUtil.isAuthorized(log.getApplication().getUser());

    return log;
  }

  @Override
  public List<Log> findByApplicationId(Long applicationId) {
    Application application = applicationRepository.findById(applicationId)
                              .orElseThrow(ApplicationNotFoundException::new);

    this.jwtUtil.isAuthorized(application.getUser());

    return logRepository.findLogByApplicationId(applicationId);
  }

  @Override
  public List<Log> findByApplicationUserId(Integer pagina, Integer tamanhoPagina, String campoOrdenacao) {

    if (pagina < 1){
      throw new PageableDefinitionException("Número da página precisa ser maior que 0");
    }
    if (tamanhoPagina < 1){
      throw new PageableDefinitionException("Tamanho da página precisa ser maior que 0.");
    }
    if (campoOrdenacao == null || campoOrdenacao == ""){
      throw new NullPointerException("Parâmetro orderby não pode ser nulo");
    }

    Sort sort = Sort.by(Sort.Direction.ASC, campoOrdenacao);
    PageRequest pageRequest = PageRequest.of(pagina - 1, tamanhoPagina, sort);

    return logRepository.findByApplicationUserId(this.jwtUtil.getAuthenticatedUser().getId(), pageRequest);
  }

  @Override
  public Log insert(LogDTO log) {
    return logRepository.save(fromDTO(log));
  }

  private Log fromDTO(LogDTO dto){
    Application application = applicationRepository.findByNameAndUserId(dto.getapplication(),
                                      this.jwtUtil.getAuthenticatedUser().getId())
                                      .orElseThrow(() -> new ApplicationNotFoundException("Usuário autenticado não possui a aplicação informada."));

    Log log = new Log(
            null,
            dto.getDescription(),
            dto.getDetails(),
            dto.getLog(),
            Level.toEnum(dto.getLevel()),
            application,
            dto.getEnvironment()
    );
    return log;
  }

}
