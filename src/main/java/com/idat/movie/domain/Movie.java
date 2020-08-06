package com.idat.movie.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Movie {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String image;
    private Double price;
    private Integer stock;
    private Boolean state;
    @ManyToOne
    private Category category;
    @ManyToOne
    private Quality quality;

    @PrePersist
    public void prePersist(){
        state = true;
    }
}
