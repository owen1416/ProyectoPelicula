package Proyecto.Peliculas.service;

import Proyecto.Peliculas.model.Genero;
import Proyecto.Peliculas.model.Pelicula;
import Proyecto.Peliculas.repository.GeneroRepository;
import Proyecto.Peliculas.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PeliculaService {

    @Autowired
    PeliculaRepository peliculaRepository;

    @Autowired // <--- ¡Añade esta inyección de dependencia!
    private GeneroRepository generoRepository;

    public List<Pelicula> listaPelicula()
    {
        return peliculaRepository.findAll();
    }

    @Transactional // ¡CRUCIAL! Esto asegura que la operación se realice de forma transaccional.
    public Pelicula nuevaPelicula(Pelicula pelicula) {
        // Manejo de la relación con Genero (si es necesario)
        // Si recibes un objeto Pelicula con objetos Genero que solo tienen el ID,
        // debes cargar los Genero desde la base de datos y asociarlos.
        Set<Genero> generosManaged = new HashSet<>();
        if (pelicula.getGenero() != null) {
            for (Genero genero : pelicula.getGenero()) {
                Optional<Genero> generoExistente = generoRepository.findById(genero.getIdGenero());
                if (generoExistente.isPresent()) {
                    generosManaged.add(generoExistente.get());
                } else {
                    // Opcional: Manejar el caso donde el género no existe
                    System.err.println("Advertencia: Género con ID " + genero.getIdGenero() + " no encontrado. Se ignorará.");
                }
            }
        }
        pelicula.setGenero(generosManaged);

        return peliculaRepository.save(pelicula);
    }

    @Transactional
    public void eliminarPelicula(Integer id)
    {
        peliculaRepository.deleteById(id);
    }

    public Pelicula obtenerPelicula(Integer id)
    {
        return peliculaRepository.findById(id).orElse(null);
    }

    public List<Pelicula> buscarPeliculas(String titulo, Integer generoId, String clasificacionEdad) { // Eliminado Pageable
        // Lógica para determinar qué filtro(s) aplicar:

        // Si se proporciona título Y clasificación de edad
        if (titulo != null && !titulo.isEmpty() && clasificacionEdad != null && !clasificacionEdad.isEmpty()) {
            return peliculaRepository.findByTituloContainingIgnoreCaseAndClasificacionEdad(titulo, clasificacionEdad);
        }
        // Si solo se proporciona título
        else if (titulo != null && !titulo.isEmpty()) {
            return peliculaRepository.findByTituloContainingIgnoreCase(titulo);
        }
        // Si solo se proporciona clasificación de edad
        else if (clasificacionEdad != null && !clasificacionEdad.isEmpty()) {
            return peliculaRepository.findByClasificacionEdad(clasificacionEdad);
        }
        // Si solo se proporciona ID de género
        else if (generoId != null) {
            return peliculaRepository.findByGenero_IdGenero(generoId);
        }
        // Si no se proporciona ningún filtro, devuelve todas las películas.
        else {
            return peliculaRepository.findAll();
        }
    }
}
