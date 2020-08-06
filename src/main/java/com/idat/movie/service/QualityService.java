package com.idat.movie.service;

import com.idat.movie.domain.Quality;
import com.idat.movie.repository.QualityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QualityService {

    @Autowired
    QualityRepository qualityRepository;

    public void init(){
        if (findAll().isEmpty()){
            Quality quality = new Quality();
            quality.setName("HD");
            quality.setExtraPrice(0.00);
            create(quality);
            quality = new Quality();
            quality.setName("FULL HD");
            quality.setExtraPrice(1.00);
            create(quality);
            quality = new Quality();
            quality.setName("ULTRA HD");
            quality.setExtraPrice(2.00);
            create(quality);
            quality = new Quality();
            quality.setName("4K");
            quality.setExtraPrice(3.00);
            create(quality);
        }
    }

    public List<Quality> findAll(){
        return qualityRepository.findAll();
    }

    public Quality create(Quality quality){
        return qualityRepository.save(quality);
    }
}
