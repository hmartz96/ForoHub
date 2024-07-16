package com.aluracursos.forohub.domain.perfiles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "perfil")
@Entity(name = "Perfil")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Perfil {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;


    public Perfil(DatosRegistroPerfil datosRegistroPerfil) {
        this.nombre = datosRegistroPerfil.nombre();
    }


    public void actualizarPerfil(DatosActualizarPerfil datosActualizarPerfil) {
        if (datosActualizarPerfil.nombre() != null) {
            this.nombre = datosActualizarPerfil.nombre();
        }
    }
}