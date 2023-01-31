package com.disneymovie.disneyJava.controllers;

import com.disneymovie.disneyJava.exceptions.DataValidationException;
import com.disneymovie.disneyJava.dtos.MovieModelDto;
import com.disneymovie.disneyJava.models.MovieModel;
import com.disneymovie.disneyJava.projections.MovieProjection;
import com.disneymovie.disneyJava.services.MovieService;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }


    //    5. Detalle de Personaje
//    Deberá mostrares el detalle de las películas en las que haya participado un personaje.
    @GetMapping("/characters/{idMovie}")
    public ResponseEntity<List<MovieModelDto>> getMoviesByCharacterId(@PathVariable("idMovie") final Integer idMovie) throws DataValidationException {
        if (idMovie > 0) {
            if (movieService.getMoviesByCharacterId(idMovie).isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok().body(movieService.getMoviesByCharacterId(idMovie));
            }
        } else {
            throw new DataValidationException("Character ID must be positive");
        }
    }

//    7. Listado de peliculas
//    Debera mostrar solamente los campos imagen, título y fecha de creación
//    El endpoint deberá ser:
//            •	GET /movies
    @GetMapping()
    public ResponseEntity<List<MovieProjection>> getAllMovieResume() throws SQLException {
        try {
            List<MovieProjection> response = movieService.getAllMovieResume();
            if (response.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok().body(response);
            }
        } catch (JpaSystemException e) {
            throw new SQLException(e.getCause().getCause().getMessage());
        }
    }

//    8. Detalle de película / serie con sus personajes
//    Devolverá todos los campos de la película o serie junto a los personajes asociados a la misma
    @GetMapping("/characters")
    public ResponseEntity<List<MovieModelDto>> getAllMoviesAndCharacters() throws SQLException {
        try {
            List<MovieModelDto> response = movieService.getAllMoviesAndCharacters();
            if (response.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok().body(response);
            }
        } catch (JpaSystemException e) {
            throw new SQLException(e.getCause().getCause().getMessage());
        }
    }

//    9. CRUD peliculas / serie
//CREATE
    @PostMapping()
    public ResponseEntity<MovieModelDto> createMovie(@RequestBody MovieModelDto newMovie) throws DataValidationException, SQLException {
        if (newMovie.isValid()) {
            try {
                Integer newMovieId = movieService.createMovie(newMovie);

                newMovie.setIdMovie(newMovieId);
                newMovie.setGenresIdList(null);
                newMovie.setCharactersIdList(null);
                newMovie.setGenres(movieService.getGenresModelByMovieId(newMovieId));
                newMovie.setCharacters(movieService.getCharactersModelByMovieId(newMovieId));

                return ResponseEntity.created(new URI("http://localhost:8080/movies/"+newMovieId)).body(newMovie);
            } catch (JpaSystemException e) {
                throw new SQLException(e.getCause().getCause().getMessage());
            } catch (URISyntaxException e) {
                throw new DataValidationException(e.getMessage());
            }
        } else {
            throw new DataValidationException("Some parameters are null, empty, blank or wrong");
        }
    }

//READ
//podes usar
//getMoviesByCharacterId
//getAllMovieResume
//getAllMoviesAndCharacters

//    UPDATE
    @PutMapping("/")
    public ResponseEntity<?> updateMovie(@RequestBody final MovieModelDto movieModified) throws SQLException {
        try {
            movieService.updateMovie(movieModified);
            return ResponseEntity.accepted().build();
        } catch (JpaSystemException e) {
            throw new SQLException(e.getCause().getCause().getMessage());
        }
    }

//    DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovieById(@PathVariable("id") final Integer id) throws SQLException, DataValidationException {
        if (id > 0) {
            try {
                movieService.deleteById(id);
                return ResponseEntity.accepted().build();
            } catch (JpaSystemException e) {
                throw new SQLException(e.getCause().getCause().getMessage());
            }
        } else {
            throw new DataValidationException("Movie ID must be positive");
        }
    }

//    Deberán permitir buscar por título y por género. Además, permitir ordenar los resultados por fecha de creación de forma ascendiente o descendente.
//    El termino de búsqueda, filtro u ordenación se deberán especificar como parámetros query:
//•	/movies?name=nombre
    @GetMapping(params = {"tittle"})
    public ResponseEntity<MovieModel> findByName(@RequestParam String tittle) throws DataValidationException, SQLException {
        if (!StringUtils.isBlank(tittle)) {
            try {
                MovieModel movie = movieService.findByTittle(tittle);
                if (movie == null) {
                    return ResponseEntity.notFound().build();
                } else {
                    return ResponseEntity.ok().body(movie);
                }
            } catch (JpaSystemException e) {
                throw new SQLException(e.getCause().getCause().getMessage());
            }
        } else {
            throw new DataValidationException("Name cant be null, void or blank");
        }
    }
//•	/movies?genre=idGenero
    @GetMapping(params = {"genreId"})
    public ResponseEntity<List<MovieModel>> findByGenreId(@RequestParam Integer genreId) throws DataValidationException {
        if (genreId > 0) {
            List<MovieModel> response = movieService.findByGenreId(genreId);
            if (response.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok().body(response);
            }
        } else {
            throw new DataValidationException("Movie Genre ID must be positive");
        }
    }
//•	/movies?order=ASC | DESC
    @GetMapping(params = {"order"})
    public ResponseEntity<List<MovieModel>> findAllByOrder(@RequestParam String order) throws DataValidationException, SQLException {
        if (order.equals("ASC")) {
            try {
                List<MovieModel> response = movieService.findAllByOrderByReleaseDateAsc();
                if (response.isEmpty()) {
                    return ResponseEntity.noContent().build();
                } else {
                    return ResponseEntity.ok().body(response);
                }
            } catch (JpaSystemException e) {
                throw new SQLException(e.getCause().getCause().getMessage());
            }
        } else if (order.equals("DESC")) {
            try {
                List<MovieModel> response = movieService.findAllByOrderByReleaseDateDesc();
                if (response.isEmpty()) {
                    return ResponseEntity.noContent().build();
                } else {
                    return ResponseEntity.ok().body(response);
                }
            } catch (JpaSystemException e) {
                throw new SQLException(e.getCause().getCause().getMessage());
            }
        } else {
            throw new DataValidationException("Wrong order");
        }
    }



}
