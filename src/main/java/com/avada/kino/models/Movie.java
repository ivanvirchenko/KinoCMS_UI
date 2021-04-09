package com.avada.kino.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.*;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Movie extends BasicEntity{
    private String videoLink;
    @ManyToMany(cascade = {MERGE, REFRESH, DETACH})
    @JoinTable(
            name = "movies_types",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id")
    )
    private List<MovieType> types;

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
