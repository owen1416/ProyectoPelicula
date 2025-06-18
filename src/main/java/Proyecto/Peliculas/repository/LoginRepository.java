package Proyecto.Peliculas.repository;

import Proyecto.Peliculas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Usuario,Integer> {

    Optional<Usuario> findByUsername(String Username);
}
