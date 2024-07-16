package com.aluracursos.forohub.domain.respuestas;
import com.aluracursos.forohub.domain.topicos.Topico;
import com.aluracursos.forohub.domain.usuarios.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Table(name = "respuesta")
@Entity(name = "Respuesta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    private Topico topico;
    private LocalDateTime fecha;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    private Boolean solucion;

    public void actualiza(DatosActualizarRespuesta datosActualizarRespuesta) {
        Boolean check = false;
        if (datosActualizarRespuesta.mensaje() != null) {
            this.mensaje = datosActualizarRespuesta.mensaje();
            check = true;
        }
        if (datosActualizarRespuesta.solucion() != null) {
            this.solucion = datosActualizarRespuesta.solucion();
            check = true;
        }
        if (check) {
            this.fecha = LocalDateTime.now();
        }


    }

}