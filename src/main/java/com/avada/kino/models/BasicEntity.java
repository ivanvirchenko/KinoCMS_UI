package com.avada.kino.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Data
@MappedSuperclass
@NoArgsConstructor
public class BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column(length = 3048)
    private String description;
    private String imgUrl;
    @ElementCollection
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Image> gallery;
    @ManyToOne
    @JoinColumn(name = "seo_id")
    private Seo seo;

    public BasicEntity(String name, String description, String imgUrl, List<Image> gallery, Seo seo) {
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.gallery = gallery;
        this.seo = seo;
    }
}
