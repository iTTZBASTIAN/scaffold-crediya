package co.com.bancolombia.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "usuarios")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario") private Long idUsuario;
    @Column(nullable = false) private String nombres;
    @Column(nullable = false) private String apellidos;
    @Column(name = "fecha_nacimiento") private LocalDate fechaNacimiento;
    private String direccion;
    private String telefono;
    @Column(name = "correo_electronico", nullable = false, unique = true) private String correoElectronico;
    @Column(name = "salario_base", nullable = false) private BigDecimal salarioBase;
}