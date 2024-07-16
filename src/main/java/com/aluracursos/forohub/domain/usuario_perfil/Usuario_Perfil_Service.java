package com.aluracursos.forohub.domain.usuario_perfil;
import com.aluracursos.forohub.domain.perfiles.Perfil;
import com.aluracursos.forohub.domain.perfiles.PerfilRespository;
import com.aluracursos.forohub.domain.usuarios.Usuario;
import com.aluracursos.forohub.domain.usuarios.UsuarioRepository;
import com.aluracursos.forohub.infra.errores.ErrorDeConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class Usuario_Perfil_Service {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRespository perfilRespository;

    @Autowired
    private Usuario_Perfil_Repository usuarioPerfilRepository;

    public DatosListado_Usuario_perfil agregarPefil(DatosRegistro_Usuario_Perfil datosRegistroUsuarioPerfil) {

        Usuario usuario;
        Perfil perfil;

        if (usuarioRepository.findById(datosRegistroUsuarioPerfil.usuario_id()).isEmpty()) {
            throw new ErrorDeConsulta("NO SE ENCONTRÓ EL USUARIO");
        }
        if (perfilRespository.findById(datosRegistroUsuarioPerfil.perfil_id()).isEmpty()) {
            throw new ErrorDeConsulta("NO SE ENCONTRÓ EL PERFIL");
        }

        usuario = usuarioRepository.getReferenceById(datosRegistroUsuarioPerfil.usuario_id());
        perfil = perfilRespository.getReferenceById(datosRegistroUsuarioPerfil.perfil_id());

        Usuario_Perfil usuarioPerfil = new Usuario_Perfil(null, usuario, perfil);

        Usuario_Perfil usuarioPerfil1 = usuarioPerfilRepository.save(usuarioPerfil);

        return new DatosListado_Usuario_perfil(usuarioPerfil1);
    }

    public List<DatosListado_Usuario_perfil> mostrarUsuarioPerfil() {
        return usuarioPerfilRepository.findAll().stream().map(DatosListado_Usuario_perfil::new).toList();
    }

    public void borrarUsuarioPerfil(Long id) {
        usuarioPerfilRepository.deleteById(id);
    }
}