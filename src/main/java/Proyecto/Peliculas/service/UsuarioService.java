package Proyecto.Peliculas.service;

import Proyecto.Peliculas.model.Usuario;
import Proyecto.Peliculas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService
{
    @Autowired
    UsuarioRepository usuarioRepository;

    public List<Usuario> listaUsuario(){
        return usuarioRepository.findAll();
    }

    public Usuario nuevoUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public void eliminarUsuario(Integer id){
       usuarioRepository.deleteById(id);
    }

    public Usuario obtenerUsuario(Integer id){
        return usuarioRepository.findById(id).orElse(null);
    }


    public Usuario editarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}
