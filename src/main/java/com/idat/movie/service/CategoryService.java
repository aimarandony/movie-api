package com.idat.movie.service;

import com.idat.movie.domain.Category;
import com.idat.movie.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public void init(){
        if (findAll().isEmpty()){
            Category category = new Category();
            category.setName("Comedia");
            create(category);
            category = new Category();
            category.setName("Terror");
            create(category);
            category = new Category();
            category.setName("Romance");
            create(category);
            category = new Category();
            category.setName("Acción");
            create(category);
            category = new Category();
            category.setName("Ciencia y Ficción");
            create(category);
            category = new Category();
            category.setName("Suspenso");
            create(category);
            category = new Category();
            category.setName("Animado");
            create(category);
            category = new Category();
            category.setName("Aventura");
            create(category);
            category = new Category();
            category.setName("Musicales");
            create(category);
        }
    }

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    public Category create(Category category){
        return categoryRepository.save(category);
    }
}
