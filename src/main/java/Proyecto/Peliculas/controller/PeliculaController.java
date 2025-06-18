package Proyecto.Peliculas.controller;

import Proyecto.Peliculas.model.Pelicula;
import Proyecto.Peliculas.service.PeliculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/pelicula")
public class PeliculaController {

    @Autowired
    PeliculaService peliculaService;

    @GetMapping

    public ResponseEntity<List<Pelicula>> listaPelicula( // Cambiado a List
                                                         @RequestParam(required = false) String titulo, // Parámetro de consulta 'titulo' (no es obligatorio)
                                                         @RequestParam(required = false) Integer generoId, // Parámetro de consulta 'generoId' (no es obligatorio)
                                                         @RequestParam(required = false) String clasificacionEdad) { // Parámetro de consulta 'clasificacionEdad' (no es obligatorio)
        // Eliminado Pageable pageable

        // Llama al servicio con todos los posibles filtros
        List<Pelicula> peliculas = peliculaService.buscarPeliculas(titulo, generoId, clasificacionEdad); // Eliminado pageable

        // Si no se encuentran películas, devuelve una respuesta 204 No Content
        if (peliculas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        // Si se encuentran películas, devuelve una respuesta 200 OK con la lista de películas
        return ResponseEntity.ok(peliculas);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Pelicula nuevaPelicula(@RequestBody Pelicula pelicula)
    {
        return peliculaService.nuevaPelicula(pelicula);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void eliminarPelicula(@PathVariable Integer id)
    {
        peliculaService.eliminarPelicula(id);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public Pelicula obtenerPelicula(@PathVariable Integer id)
    {
        return peliculaService.obtenerPelicula(id);
    }


}
