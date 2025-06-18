package Proyecto.Peliculas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int idUsuario;

    @Column(name = "nombre", nullable = false)
    private String  username;

    @Column(name = "email", nullable = false)
    private String  email;

    @Column(name = "password", nullable = false)
    private String  password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}) // ¡REMOVE añadido!
    @JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "idUsuario"),
            inverseJoinColumns = @JoinColumn(name = "idRol"))
    private Set<Rol> rol;


    public Usuario() {
    }

    public Usuario(int idUsuario, String username, String email, String password) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
