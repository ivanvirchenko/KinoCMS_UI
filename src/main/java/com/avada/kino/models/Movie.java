package com.avada.kino.models;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Movie extends BasicEntity{
    @Column(unique = true)
    private String videoLink;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {REFRESH, PERSIST, DETACH, MERGE})
    @JoinTable(
            name = "movies_types",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id")
    )
    private List<MovieType> types;
    @NonNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate startDate;
    @NonNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate endDate;

    public Movie(String name, String description, String imgUrl, Seo seo, String videoLink, List<MovieType> types, @NonNull LocalDate startDate, @NonNull LocalDate endDate) {
        super(name, description, imgUrl, seo);
        this.videoLink = videoLink;
        this.types = types;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
