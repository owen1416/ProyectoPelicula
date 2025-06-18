package Proyecto.Peliculas.controller;

import Proyecto.Peliculas.model.Usuario;
import Proyecto.Peliculas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<Usuario> listaUsuario() {
        return usuarioService.listaUsuario();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Usuario nuevoUsuario(@RequestBody Usuario usuario) {
        return usuarioService.nuevoUsuario(usuario);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void eliminarUsuario(@PathVariable Integer id) {
        usuarioService.eliminarUsuario(id);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Usuario obtenerUsuario(@PathVariable Integer id) {
        return usuarioService.obtenerUsuario(id);
    }



}
