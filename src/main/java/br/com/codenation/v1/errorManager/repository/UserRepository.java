package br.com.codenation.v1.errorManager.repository;

import br.com.codenation.v1.errorManager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findById(Long userId);

  @Transactional(readOnly = true)
  User findByUsername(String email);
}
