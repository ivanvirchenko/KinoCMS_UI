package com.avada.kino.models;

import lombok.*;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.CascadeType.*;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Movie extends BasicEntity{
    private String videoLink;
    @ManyToMany(cascade = {ALL})
    @JoinTable(
            name = "movies_to_types",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id")
    )
    private Set<MovieType> types = new HashSet<>();

    @NonNull
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @NonNull
    @Temporal(TemporalType.DATE)
    private Date endDate;

    public void addType(MovieType type) {
        types.add(type);
    }

}
