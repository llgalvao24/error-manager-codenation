package br.com.codenation.v1.errorManager.service.interfaces;

import br.com.codenation.v1.errorManager.dto.LogDTO;
import br.com.codenation.v1.errorManager.entity.Log;

import java.util.List;

public interface LogServiceInterface extends ServiceInterface<Log> {

  LogDTO findById(Long id);

  List<LogDTO> findByApplicationId(Long applicationId);

  List<LogDTO> findByApplicationUserId(Integer pagina, Integer tamanhoPagina, String campoOrdenacao);

  Log insert(LogDTO log);

}
