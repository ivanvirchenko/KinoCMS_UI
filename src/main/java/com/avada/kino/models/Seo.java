package com.avada.kino.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class Seo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String url;
    private String title;
    private String keyWords;
    private String description;

    public Seo(String url, String title, String keyWords, String description) {
        this.url = url;
        this.title = title;
        this.keyWords = keyWords;
        this.description = description;
    }
}
