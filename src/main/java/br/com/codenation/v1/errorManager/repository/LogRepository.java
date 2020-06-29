package br.com.codenation.v1.errorManager.repository;

import br.com.codenation.v1.errorManager.entity.Application;
import br.com.codenation.v1.errorManager.entity.Log;
import br.com.codenation.v1.errorManager.enums.Level;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

  List<Log> findLogByApplicationId(Long applicationId);

  List<Log> findByApplicationUserIdAndArchived(Long userId, boolean archived, Pageable pageable);

  Log findByLevelAndDescriptionAndDetailsAndEnvironmentAndLogAndApplicationIdAndApplicationUserId(
          Integer level,
          String description,
          String details,
          String environment,
          String log,
          Long applicationId,
          Long userId
  );

  List<Log> findByApplicationUserIdAnAndLevel(Long userId, Integer level, Pageable pageable);

  List<Log> findByApplicationUserIdAnAndDescription(Long userId, String description, Pageable pageable);

  List<Log> findByApplicationUserIdAnAndOrigin(Long userId, String origin, Pageable pageable);

  Log findByLevelAndDescriptionAndDetailsAndEnvironmentAndLogAndApplicationIdAndApplicationUserId(
          Integer level,
          String description,
          String details,
          String environment,
          String log,
          Long applicationId,
          Long userId
  );

  @Query(" select count(l) as countLog from Log l " +
          " where l.level = :level and " +
          " l.application.id = :applicationId and " +
          " l.details = :details and " +
          " l.description = :description and " +
          " l.environment = :environment and " +
          " l.log = :log and " +
          " l.application.user.id = :userId")
  Long countExists(@Param("level") Integer level,
                   @Param("applicationId") Long applicationId,
                   @Param("details") String details,
                   @Param("description") String description,
                   @Param("environment") String environment,
                   @Param("log") String log,
                   @Param("userId") Long userId);

}
