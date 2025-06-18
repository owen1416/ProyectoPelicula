package Proyecto.Peliculas.service;

import Proyecto.Peliculas.model.Rol;
import Proyecto.Peliculas.model.Usuario;
import Proyecto.Peliculas.repository.RolRepository;
import Proyecto.Peliculas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    RolRepository rolRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario registrarUsuario(String username, String password,String email, String rolNombre){
        // 1. Verificar si el usuario ya existe
        if(usuarioRepository.existsByUsername(username)){
            throw new RuntimeException("El nombre de usuario '" + username + "' ya está en uso.");
        }
        if (usuarioRepository.findByEmail(email).isPresent()) { // Opcional: verificar que el email no esté en uso
            throw new RuntimeException("El email '" + email + "' ya está en uso.");
        }



        // 2. Encriptar la contraseña
        String encodedPassword = passwordEncoder.encode(password);

        // 3. Crear el nuevo usuario
        Usuario nuevoUsuario = new Usuario(username, encodedPassword);
        nuevoUsuario.setEmail(email);

        // 4. Asignar roles
        Set<Rol> roles = new HashSet<>();
        Optional<Rol> rol = rolRepository.findByNombreRol(rolNombre);

        if (rol.isPresent()) {
            roles.add(rol.get());
        } else {
            // Si el rol no existe, puedes crear uno por defecto (ej. "USER")
            // O lanzar una excepción si el rol es obligatorio y no existe.
            Rol defaultRol = rolRepository.findByNombreRol("USER")
                    .orElseThrow(() -> new RuntimeException("Rol 'USER' no encontrado. Asegúrate de que los roles estén pre-cargados."));
            roles.add(defaultRol);
            System.err.println("Advertencia: El rol '" + rolNombre + "' no existe. Asignando rol por defecto 'USER'.");
        }
        nuevoUsuario.setRol(roles);

        // 5. Guardar el usuario en la base de datos
        return usuarioRepository.save(nuevoUsuario);


    }
}
