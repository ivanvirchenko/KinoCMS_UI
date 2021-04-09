package com.avada.kino.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Column(name = "imageName")
    private String name;
    @Column(name = "imageUrl")
    private String url;
}
