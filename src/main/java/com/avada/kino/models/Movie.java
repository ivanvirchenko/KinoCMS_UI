package com.avada.kino.models;

import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Movie extends BasicEntity{
    private String videoLink;
    @ElementCollection
    private List<MovieType> types;
    private boolean inShow;

    public Movie(String name, String description, String imgUrl, List<Image> gallery, Seo seo, String videoLink, List<MovieType> types, boolean inShow) {
        super(name, description, imgUrl, gallery, seo);
        this.videoLink = videoLink;
        this.types = types;
        this.inShow = inShow;
    }
}
