package Proyecto.Peliculas.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.persistence.criteria.Fetch;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "idPelicula"
)
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPelicula;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "fecha_lanzamiento", nullable = false)
    private Date fechaLanzamiento;

    @Column(name = "duracion_minutos", nullable = false)
    private int duracionMinutos;

    @Column(name = "clasificacion_edad", nullable = false)
    private String clasificacionEdad;

    @Column(name = "url_trailer", nullable = false)
    private String urlTrailer;

    @Column(name = "url_poster", nullable = false)
    private String urlPoster;

    @Column(name = "url_pelicula", nullable = false)
    private String urlPelicula;

    @Column(name = "calificacion_promedio", nullable = false)
    private Double calificacionPromedio;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "pelicula_genero",
            joinColumns = @JoinColumn(name = "idPelicula"),
            inverseJoinColumns = @JoinColumn(name = "idGenero"))
    @JsonIgnoreProperties("peliculas") // ← aquí ignoramos la lista de películas en el género
    private Set<Genero> genero = new HashSet<>();

    public Pelicula() {
    }

    public Pelicula(int idPelicula, Set<Genero> genero, Double calificacionPromedio, String urlPelicula, String urlPoster, String urlTrailer, String clasificacionEdad, int duracionMinutos, Date fechaLanzamiento, String descripcion, String titulo) {
        this.idPelicula = idPelicula;
        this.genero = genero;
        this.calificacionPromedio = calificacionPromedio;
        this.urlPelicula = urlPelicula;
        this.urlPoster = urlPoster;
        this.urlTrailer = urlTrailer;
        this.clasificacionEdad = clasificacionEdad;
        this.duracionMinutos = duracionMinutos;
        this.fechaLanzamiento = fechaLanzamiento;
        this.descripcion = descripcion;
        this.titulo = titulo;
    }
}
