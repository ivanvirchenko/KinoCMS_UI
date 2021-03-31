package com.avada.kino.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class News extends BasicEntity {
    private boolean enabled;
    private LocalDate date;
    private String videoLink;

    public News(String name, String description, String imgUrl, Seo seo, boolean enabled, LocalDate date, String videoLink) {
        super(name, description, imgUrl, seo);
        this.enabled = enabled;
        this.date = date;
        this.videoLink = videoLink;
    }
}
