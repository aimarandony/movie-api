package com.idat.movie.controller;

import com.idat.movie.domain.Movie;
import com.idat.movie.service.MovieService;
import com.idat.movie.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200","http://127.0.0.1:5500","https://aimarandony.github.io/movie-app"})
@RestController
@RequestMapping("/api")
public class MovieController {

    @Autowired
    MovieService movieService;

    @Autowired
    UploadFileService uploadService;

    @GetMapping("/movies")
    public List<Movie> findAll(){
        return movieService.findAll();
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Map<String,Object> resp = new HashMap<>();

        try {
            Movie movie = movieService.findById(id);
            if (movie == null){
                resp.put("error","El id '".concat(id.toString()).concat("' no existe en la base de datos."));
                return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
            } else {
                resp.put("movie",movie);
                resp.put("resp","Película encontrada.");
                return new ResponseEntity<>(resp, HttpStatus.OK);
            }
        } catch (DataAccessException ex) {
            resp.put("mens","Se encontró un error al consultar con la base de datos.");
            resp.put("error","Error: ".concat(ex.getMessage()).concat(" - ").concat(ex.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/movies")
    public ResponseEntity<?> create(@RequestBody Movie movie){
        Map<String,Object> resp = new HashMap<>();
        try {
            Movie movieCreated = movieService.create(movie);
            resp.put("movie",movieCreated);
            resp.put("resp","Pelicula '".concat(movie.getName()).concat("' registrada correctamente."));
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (DataAccessException ex) {
            resp.put("mens","Se encontró un error al consultar con la base de datos.");
            resp.put("error","Error: ".concat(ex.getMessage()).concat(" - ").concat(ex.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/movies/{id}")
    public ResponseEntity<?> update(@RequestBody Movie movie, @PathVariable Long id){
        Map<String,Object> resp = new HashMap<>();
        try {
            Movie movieUpdated = movieService.update(movie, id);
            resp.put("movie",movieUpdated);
            resp.put("resp","Pelicula '".concat(movie.getName()).concat("' actualizada correctamente."));
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (DataAccessException ex) {
            resp.put("mens","Se encontró un error al consultar con la base de datos.");
            resp.put("error","Error: ".concat(ex.getMessage()).concat(" - ").concat(ex.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/movies/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id) throws IOException {
        Map<String, Object> response = new HashMap<>();

        Movie movie = movieService.findById(id);

//        if (file.getSize() * 0.00000095367432  > 8){
//            response.put("msg", "El tamaño de la imagen no puede exceder los 8MB.");
//            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//        }

        if (!file.isEmpty()) {
            String nameFile = null;
            try {
                nameFile = uploadService.copiar(file);
            } catch (IOException e) {
                response.put("msg", "Error al subir la imagen del producto");
                response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            String oldFileName = movie.getImage();

            uploadService.eliminar(oldFileName);

            movie.setImage(nameFile);
            movieService.create(movie);
            response.put("movie", movie);
            response.put("msg", "Se ha subido correctamente la imagen: " + nameFile);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @GetMapping("/uploads/img/{fileName:.+}")
    public ResponseEntity<Resource> seeImage(@PathVariable String fileName){

        Resource resource = null;
        try {
            resource = uploadService.cargar(fileName);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpHeaders head = new HttpHeaders();
        head.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = \""+ resource.getFilename()+"\"");

        return new ResponseEntity<Resource>(resource,head,HttpStatus.OK);
    }
}
