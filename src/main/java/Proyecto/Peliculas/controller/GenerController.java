package Proyecto.Peliculas.controller;

import Proyecto.Peliculas.model.Genero;
import Proyecto.Peliculas.service.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/genero")
public class GenerController {

    @Autowired
    GeneroService generoService;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public List<Genero> listaGenero()
    {
        return generoService.listaGenero();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Genero nuevoGenero(@RequestBody Genero genero)
    {
        return generoService.nuevoGenero(genero);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void eliminarGenero(@PathVariable Integer id)
    {
        generoService.eliminarGenero(id);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Genero obtenerGenero(@PathVariable Integer id)
    {
        return generoService.obtenerGenero(id);
    }


}
