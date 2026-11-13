package co.com.bancolombia.jpa;

import co.com.bancolombia.jpa.entity.UserEntity;
import co.com.bancolombia.model.exceptions.BusinessException;
import co.com.bancolombia.model.exceptions.ErrorCode;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class JPARepositoryAdapter implements UserRepository {

    private final JPARepository repository;

    @Override
    @Transactional
    public User save(User user) {
        try {
            UserEntity guardado = repository.saveAndFlush(toEntity(user));
            log.debug("[TRACE] Usuario persistido id={}", guardado.getIdUsuario());
            return toDomain(guardado);
        } catch (DataIntegrityViolationException e) {
            log.warn("[TRACE] Correo duplicado detectado por la BD: {}", user.getCorreoElectronico());
            throw new BusinessException(ErrorCode.CORREO_YA_REGISTRADO);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByCorreoElectronico(String correoElectronico) {
        return repository.existsByCorreoElectronico(correoElectronico);
    }

    private UserEntity toEntity(User u) {
        return UserEntity.builder()
                .idUsuario(u.getIdUsuario())
                .nombres(u.getNombres())
                .apellidos(u.getApellidos())
                .fechaNacimiento(u.getFechaNacimiento())
                .direccion(u.getDireccion())
                .telefono(u.getTelefono())
                .correoElectronico(u.getCorreoElectronico())
                .salarioBase(u.getSalarioBase())
                .build();
    }

    private User toDomain(UserEntity e) {
        return User.builder()
                .idUsuario(e.getIdUsuario())
                .nombres(e.getNombres())
                .apellidos(e.getApellidos())
                .fechaNacimiento(e.getFechaNacimiento())
                .direccion(e.getDireccion())
                .telefono(e.getTelefono())
                .correoElectronico(e.getCorreoElectronico())
                .salarioBase(e.getSalarioBase())
                .build();
    }
}