package com.avada.kino.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@MappedSuperclass
@AllArgsConstructor
public class BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(length = 5000)
    private String description;
    private Image image;

    @ElementCollection
    private List<Image> gallery;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seo_id")
    private Seo seo;

    public BasicEntity() {
        if (this.gallery == null) {
            this.gallery = new ArrayList<>();
        }
        if (this.seo == null) {
            this.seo = new Seo();
        }
    }

    public BasicEntity(String name, String description, Image image, List<Image> gallery, Seo seo) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.gallery = gallery;
        this.seo = seo;
    }

    public void addToGallery(Image image) {
        gallery.add(image);
    }
}
