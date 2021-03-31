package com.avada.kino.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(length = 5000)
    private String description;
    private String imgUrl;
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Image> gallery;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seo_id")
    private Seo seo;

    public BasicEntity(String name, String description, String imgUrl, Seo seo) {
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.seo = seo;
    }

    public Seo getSeo() {
        if (seo == null) {
            seo = new Seo();
        }
        return seo;
    }
}
