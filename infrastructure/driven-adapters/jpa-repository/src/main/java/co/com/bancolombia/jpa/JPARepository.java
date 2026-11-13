package co.com.bancolombia.jpa;

import co.com.bancolombia.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPARepository extends JpaRepository<UserEntity, Long> {

    boolean existsByCorreoElectronico(String correoElectronico);
}