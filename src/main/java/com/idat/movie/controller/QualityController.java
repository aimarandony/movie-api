package com.idat.movie.controller;

import com.idat.movie.domain.Quality;
import com.idat.movie.service.QualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200","http://127.0.0.1:5500","https://aimarandony.github.io"})
@RestController
@RequestMapping("/api")
public class QualityController {

    @Autowired
    QualityService qualityService;

    @GetMapping("/qualities")
    public List<Quality> findAll(){
        return qualityService.findAll();
    }
}
