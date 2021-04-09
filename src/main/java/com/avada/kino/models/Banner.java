package com.avada.kino.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String pageName;
    private boolean enabled;
    private int speed;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "banner_images")
    private List<Image> images;
    public Banner(List<Image> images) {
        this.images = images;
    }

    public void addImage(Image image) {
        images.add(image);
    }
}
