package br.com.codenation.v1.errorManager.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  @Query(value = "SELECT id, name, email, created_at FROM users WHERE id = :userId", nativeQuery = true)
  Optional<User> findByIdSQL(@Param("userId") Long userId);

  Optional<User> findById(Long userId);
}
