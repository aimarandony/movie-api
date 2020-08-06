package com.idat.movie.service;

import com.idat.movie.domain.Movie;
import com.idat.movie.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    public void init(){
        if (findAll().isEmpty()){
            Movie movie = new Movie();
            movie.setName("Pelicula 01");
            movie.setPrice(3.00);
            movie.setStock(25);
        }
    }

    public List<Movie> findAll(){
        return movieRepository.findAll();
    }

    public Movie findById(Long id){
        return movieRepository.findById(id).orElse(null);
    }

    public Movie create(Movie movie){
        return movieRepository.save(movie);
    }

    public Movie update(Movie movie, Long id){
        Movie updateMovie = movieRepository.findById(id).orElse(null);
        updateMovie.setName(movie.getName());
        updateMovie.setPrice(movie.getPrice());
        updateMovie.setStock(movie.getStock());
        updateMovie.setCategory(movie.getCategory());
        updateMovie.setQuality(movie.getQuality());
        return movieRepository.save(updateMovie);
    }
}
