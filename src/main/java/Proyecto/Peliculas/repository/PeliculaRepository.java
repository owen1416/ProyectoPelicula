package Proyecto.Peliculas.repository;


import Proyecto.Peliculas.model.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PeliculaRepository extends JpaRepository<Pelicula,Integer> {

    // Método para buscar películas por título (insensible a mayúsculas/minúsculas)
    List<Pelicula> findByTituloContainingIgnoreCase(String titulo); // Cambiado a List y sin Pageable

    // Método para buscar películas por clasificación de edad
    List<Pelicula> findByClasificacionEdad(String clasificacionEdad); // Cambiado a List y sin Pageable

    // Método para buscar películas por título Y clasificación de edad
    List<Pelicula> findByTituloContainingIgnoreCaseAndClasificacionEdad(String titulo, String clasificacionEdad); // Cambiado a List y sin Pageable

    // Método para buscar películas por género (filtrando por idGenero)
    List<Pelicula> findByGenero_IdGenero(Integer generoId); // Cambiado a List y sin Pageable
}
