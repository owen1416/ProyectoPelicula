package Proyecto.Peliculas.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idGenero"
)
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idGenero;

    @Column(name = "genero  ", nullable = false, unique = true)
    private String nombreGenero;

    // Relación Many-to-Many con Pelicula
    @ManyToMany(mappedBy = "genero", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("genero") // ← aquí ignoramos la lista de géneros en la película
    private Set<Pelicula> peliculas = new HashSet<>();

    public Genero() {
    }

    public Genero(int idGenero, String nombreGenero) {
        this.idGenero = idGenero;
        this.nombreGenero = nombreGenero;
    }
}
