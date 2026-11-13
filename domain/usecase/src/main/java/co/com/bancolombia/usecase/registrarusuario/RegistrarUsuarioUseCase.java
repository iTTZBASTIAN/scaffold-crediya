package co.com.bancolombia.usecase.registrarusuario;

import co.com.bancolombia.model.exceptions.BusinessException;
import co.com.bancolombia.model.exceptions.ErrorCode;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class RegistrarUsuarioUseCase {

    private static final Pattern EMAIL = Pattern.compile("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$");
    private static final BigDecimal SALARIO_MAX = new BigDecimal("15000000");

    private final UserRepository userRepository;

    public User registrar(User user) {
        validar(user);
        if (userRepository.existsByCorreoElectronico(user.getCorreoElectronico())) {
            throw new BusinessException(ErrorCode.CORREO_YA_REGISTRADO);
        }
        return userRepository.save(user);
    }

    private void validar(User u) {
        requerido(u.getNombres(), "nombres");
        requerido(u.getApellidos(), "apellidos");
        requerido(u.getCorreoElectronico(), "correo_electronico");
        if (u.getSalarioBase() == null) {
            throw new BusinessException(ErrorCode.CAMPO_OBLIGATORIO, "salario_base");
        }
        if (!EMAIL.matcher(u.getCorreoElectronico()).matches()) {
            throw new BusinessException(ErrorCode.CORREO_INVALIDO);
        }
        if (u.getSalarioBase().compareTo(BigDecimal.ZERO) < 0
                || u.getSalarioBase().compareTo(SALARIO_MAX) > 0) {
            throw new BusinessException(ErrorCode.SALARIO_INVALIDO);
        }
    }

    private void requerido(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new BusinessException(ErrorCode.CAMPO_OBLIGATORIO, campo);
        }
    }
}