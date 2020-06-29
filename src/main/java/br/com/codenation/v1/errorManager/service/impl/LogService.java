package br.com.codenation.v1.errorManager.service.impl;

import br.com.codenation.v1.errorManager.dto.ArquivaLogDTO;
import br.com.codenation.v1.errorManager.dto.LogDTO;
import br.com.codenation.v1.errorManager.dto.LogInfoDTO;
import br.com.codenation.v1.errorManager.entity.Application;
import br.com.codenation.v1.errorManager.entity.Log;
import br.com.codenation.v1.errorManager.enums.Level;
import br.com.codenation.v1.errorManager.exception.ApplicationNotFoundException;
import br.com.codenation.v1.errorManager.exception.LogNotFoundException;
import br.com.codenation.v1.errorManager.exception.PageableDefinitionException;
import br.com.codenation.v1.errorManager.mappers.LogMapper;
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
  private final LogMapper logMapper;

  @Autowired
  public LogService(LogRepository logRepository, ApplicationRepository applicationRepository, JWTUtil jwtUtil, LogMapper logMapper) {
    this.logRepository = logRepository;
    this.applicationRepository = applicationRepository;
    this.jwtUtil = jwtUtil;
    this.logMapper = logMapper;
  }

  @Override
  public LogInfoDTO findById(Long id) {
    Log log = logRepository.findById(id)
                .orElseThrow(LogNotFoundException::new);

    this.jwtUtil.isAuthorized(log.getApplication().getUser());

    return logMapper.map(log);
  }

  @Override
  public List<LogInfoDTO> findByApplicationId(Long applicationId) {
    Application application = applicationRepository.findById(applicationId)
                              .orElseThrow(ApplicationNotFoundException::new);

    this.jwtUtil.isAuthorized(application.getUser());

    List<Log> logs = logRepository.findLogByApplicationId(applicationId);

    return logMapper.map(logs);
  }

  @Override
  public List<LogInfoDTO> findByApplicationUserId(Integer pagina, Integer tamanhoPagina, String campoOrdenacao, boolean archived) {

    PageRequest pageRequest = createPageable(pagina - 1, tamanhoPagina, campoOrdenacao);

    List<Log> logs = logRepository.findByApplicationUserIdAndArchived(this.jwtUtil.getAuthenticatedUser().getId(),archived, pageRequest);

    return logMapper.map(logs);
  }

  @Override
  public List<LogInfoDTO> findByApplicationUserIdAndLevel(Integer pagina, Integer tamanhoPagina, String campoOrdenacao, boolean archived, Level level) {
    PageRequest pageRequest = createPageable(pagina - 1, tamanhoPagina, campoOrdenacao);

    List<Log> logs = logRepository.findByApplicationUserIdAndLevel(this.jwtUtil.getAuthenticatedUser().getId(),
                                                                      level.getCode(), pageRequest);

    return logMapper.map(logs);
  }

  @Override
  public List<LogInfoDTO> findByApplicationUserIdAndDescription(Integer pagina, Integer tamanhoPagina, String campoOrdenacao, boolean archived, String description) {
    PageRequest pageRequest = createPageable(pagina - 1, tamanhoPagina, campoOrdenacao);

    List<Log> logs = logRepository.findByApplicationUserIdAndDescription(this.jwtUtil.getAuthenticatedUser().getId(),
                                                                            description,
                                                                            pageRequest);

    return logMapper.map(logs);
  }

  @Override
  public List<LogInfoDTO> findByApplicationUserIdAndOrigin(Integer pagina, Integer tamanhoPagina, String campoOrdenacao, boolean archived, String origin) {
    PageRequest pageRequest = createPageable(pagina - 1, tamanhoPagina, campoOrdenacao);

    List<Log> logs = logRepository.findByApplicationUserIdAndOrigin(this.jwtUtil.getAuthenticatedUser().getId(),
                                                                      origin,
                                                                      pageRequest);

    return logMapper.map(logs);
  }

  private PageRequest createPageable(Integer pagina, Integer tamanhoPagina, String campoOrdenacao){
    if (pagina < 1){
      throw new PageableDefinitionException("Número da página precisa ser maior que 0");
    }
    if (tamanhoPagina < 1){
      throw new PageableDefinitionException("Tamanho da página precisa ser maior que 0.");
    }
    if (campoOrdenacao == null || campoOrdenacao.equals("")){
      throw new NullPointerException("Parâmetro orderby não pode ser nulo");
    }

    Sort sort = Sort.by(Sort.Direction.ASC, campoOrdenacao);

    return PageRequest.of(pagina - 1, tamanhoPagina, sort);
  }

  @Override
  public LogInfoDTO insert(LogDTO log) {
    Log newLog = fromDTO(log);
    Log oldLog = findLogIfExists(newLog);

    if (oldLog == null) {
      newLog.addEvent();
      return logMapper.map(logRepository.save(newLog));
    }else{
      oldLog.addEvent();
      return logMapper.map(logRepository.save(oldLog));
    }

  }
  
  @Override
  public LogInfoDTO archive(Long id, ArquivaLogDTO archived) {
    Log log = logRepository.findById(id)
            .orElseThrow(LogNotFoundException::new);

    jwtUtil.isAuthorized(log.getApplication().getUser());

    log.setArchived(archived.isArchived());

    return logMapper.map(logRepository.save(log));
  }

  @Override
  public void delete(Long id) {
    Log log = logRepository.findById(id)
            .orElseThrow(LogNotFoundException::new);

    jwtUtil.isAuthorized(log.getApplication().getUser());

    logRepository.delete(log);

  }

  private Log fromDTO(LogDTO dto){
    Application application = applicationRepository
            .findByNameAndUserId(dto.getApplication().getName(), this.jwtUtil.getAuthenticatedUser().getId())
            .orElseThrow(() -> new ApplicationNotFoundException("Usuário autenticado não possui a aplicação informada."));

    return new Log(
            null,
            dto.getDescription(),
            dto.getDetails(),
            dto.getLog(),
            Level.toEnum(dto.getLevel().getCode()),
            application,
            dto.getEnvironment()
    );
  }

  private Log findLogIfExists(Log log){
    return  logRepository.findByLevelAndDescriptionAndDetailsAndEnvironmentAndLogAndApplicationIdAndApplicationUserId(
            log.getLevel().getCode(),
            log.getDescription(),
            log.getDetails(),
            log.getEnvironment(),
            log.getLog(),
            log.getApplication().getId(),
            log.getApplication().getUser().getId()
    );
  }

}
