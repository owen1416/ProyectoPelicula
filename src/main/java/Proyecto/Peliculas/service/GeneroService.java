package Proyecto.Peliculas.service;

import Proyecto.Peliculas.model.Genero;
import Proyecto.Peliculas.repository.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneroService
{

    @Autowired
    GeneroRepository generoRepository;

    public List<Genero> listaGenero()
    {
        return generoRepository.findAll();
    }

    public Genero nuevoGenero(Genero genero)
    {
        return generoRepository.save(genero);
    }

    public void eliminarGenero(Integer id)
    {
        generoRepository.deleteById(id);
    }

    public Genero obtenerGenero(Integer id)
    {
        return generoRepository.findById(id).orElse(null);
    }
}
