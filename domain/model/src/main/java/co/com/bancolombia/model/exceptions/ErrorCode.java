package co.com.bancolombia.model.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    CAMPO_OBLIGATORIO("USR-001", "El campo %s es obligatorio"),
    CORREO_INVALIDO("USR-002", "El correo electrónico no tiene un formato válido"),
    SALARIO_INVALIDO("USR-003", "El salario base debe estar entre 0 y 15.000.000"),
    CORREO_YA_REGISTRADO("USR-004", "El correo electrónico ya está registrado");

    private final String code;
    private final String message;
}