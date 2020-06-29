package br.com.codenation.v1.errorManager.service.interfaces;

import br.com.codenation.v1.errorManager.dto.ArquivaLogDTO;
import br.com.codenation.v1.errorManager.dto.LogDTO;
import br.com.codenation.v1.errorManager.dto.LogInfoDTO;
import br.com.codenation.v1.errorManager.entity.Log;
import br.com.codenation.v1.errorManager.enums.Level;

import java.util.List;

public interface LogServiceInterface extends ServiceInterface<Log> {

  LogInfoDTO findById(Long id);

  List<LogInfoDTO> findByApplicationId(Long applicationId);

  List<LogInfoDTO> findByApplicationUserId(Integer pagina, Integer tamanhoPagina, String campoOrdenacao, boolean archived);

  LogInfoDTO insert(LogDTO log);

  LogInfoDTO archive(Long id, ArquivaLogDTO archived);

  void delete(Long id);

  List<LogInfoDTO> findByApplicationUserIdAndLevel(Integer pagina, Integer tamanhoPagina, String orderBy, boolean archived, Level level);

  List<LogInfoDTO> findByApplicationUserIdAndDescription(Integer pagina, Integer tamanhoPagina, String campoOrdenacao, boolean archived, String description);

  List<LogInfoDTO> findByApplicationUserIdAndOrigin(Integer pagina, Integer tamanhoPagina, String campoOrdenacao, boolean archived, String origin);

}
