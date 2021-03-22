package com.avada.kino.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Hall extends BasicEntity{
    private String banner_url;
    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    public Hall(String name, String description, String imgUrl, List<Image> gallery, Seo seo, String banner_url, Cinema cinema) {
        super(name, description, imgUrl, gallery, seo);
        this.banner_url = banner_url;
        this.cinema = cinema;
    }
}
