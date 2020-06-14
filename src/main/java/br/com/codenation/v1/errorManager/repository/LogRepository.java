package br.com.codenation.v1.errorManager.repository;

import br.com.codenation.v1.errorManager.entity.Application;
import br.com.codenation.v1.errorManager.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

  List<Log> findLogByApplicationId(Long applicationId);

  List<Log> findByApplicationUserId(Long userId);
}
