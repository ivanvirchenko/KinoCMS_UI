package com.avada.kino.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.avada.kino.util.StringsConstant.*;

@Data
@Entity
@NoArgsConstructor
public class Seo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = REQUIRED)
    @Size(max = 255, message = MAX_SIZE + 255)
    private String url;

    @NotEmpty(message = REQUIRED)
    @Size(max = 255, message = MAX_SIZE + 255)
    private String title;

    @NotEmpty(message = REQUIRED)
    @Size(max = 255, message = MAX_SIZE + 255)
    private String keyWords;

    @Column(length = 3048)
    @NotEmpty(message = REQUIRED)
    @Size(max = 3048, message = MAX_SIZE + 3048)
    private String description;

    public Seo(String url, String title, String keyWords, String description) {
        this.url = url;
        this.title = title;
        this.keyWords = keyWords;
        this.description = description;
    }
}
