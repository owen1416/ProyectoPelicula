package Proyecto.Peliculas.controller;

import Proyecto.Peliculas.JWT.JwtTokenProvider;
import Proyecto.Peliculas.model.Usuario;
import Proyecto.Peliculas.payload.JwtAuthResponse;
import Proyecto.Peliculas.payload.LoginRequest;
import Proyecto.Peliculas.payload.RegistroRequest;
import Proyecto.Peliculas.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class    AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    AuthService authService;

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Funcionando en Railway");
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> RegistroUsuario(@RequestBody LoginRequest loginRequest) {
        // Autentica al usuario usando el AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // Si la autenticación fue exitosa, establece la autenticación en el contexto de seguridad
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Genera el token JWT
        String jwt =tokenProvider.generateToken(authentication);

        // Retorna el token al cliente
        return ResponseEntity.ok(new JwtAuthResponse(jwt));
    }


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegistroRequest request) {
        try {
            String rolPorDefectoOEspecificado = (request.getRol() != null && !request.getRol().isEmpty()) ? request.getRol() : "USER";

            // Asegúrate de pasar el email al servicio
            Usuario nuevoUsuario = authService.registrarUsuario(request.getUsername(), request.getPassword(), request.getEmail(), rolPorDefectoOEspecificado);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario '" + nuevoUsuario.getUsername() + "' registrado exitosamente.");

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al registrar usuario: " + e.getMessage());
        }
    }


}
