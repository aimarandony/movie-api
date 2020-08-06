package com.idat.movie.config;

import com.idat.movie.service.CategoryService;
import com.idat.movie.service.QualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PopulateConfig {

    @Autowired
    CategoryService categoryService;

    @Autowired
    QualityService qualityService;

    @PostConstruct
    public void init(){
        categoryService.init();
        qualityService.init();
    }
}
