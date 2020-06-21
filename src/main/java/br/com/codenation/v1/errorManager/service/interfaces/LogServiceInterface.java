package br.com.codenation.v1.errorManager.service.interfaces;

import br.com.codenation.v1.errorManager.dto.LogDTO;
import br.com.codenation.v1.errorManager.dto.LogInfoDTO;
import br.com.codenation.v1.errorManager.entity.Log;

import java.util.List;

public interface LogServiceInterface extends ServiceInterface<Log> {

  LogInfoDTO findById(Long id);

  List<LogInfoDTO> findByApplicationId(Long applicationId);

  List<LogInfoDTO> findByApplicationUserId(Integer pagina, Integer tamanhoPagina, String campoOrdenacao);

  LogInfoDTO insert(LogDTO log);

}
