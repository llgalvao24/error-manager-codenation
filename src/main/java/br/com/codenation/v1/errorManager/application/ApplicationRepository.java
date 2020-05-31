package br.com.codenation.v1.errorManager.application;

import br.com.codenation.v1.errorManager.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    Optional<Application> findByIdAndUserId(Long applicationId, Long userId);

    List<Application> findByUserId(@Param("userId") Long userId);

}
