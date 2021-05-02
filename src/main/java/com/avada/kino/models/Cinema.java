package com.avada.kino.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.avada.kino.util.UtilConstant.MAX_SIZE;
import static javax.persistence.CascadeType.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity

public class Cinema extends BasicEntity {
    @Size(max = 4000, message = MAX_SIZE + 4000)
    private String conditions;

    @Embedded
    @AttributeOverrides(
            {
                    @AttributeOverride(name = "name", column = @Column(name = "banner_name")),
                    @AttributeOverride(name = "url", column = @Column(name = "banner_url"))
            }
    )
    private Image banner;

    @ManyToOne(cascade = {MERGE, DETACH, REFRESH})
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "cinema", cascade = ALL)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Hall> hallsList;

    @OneToMany(mappedBy = "cinema", cascade = ALL)
    private List<MovieSession> sessions;








    public void addHall(Hall hall) {
        if (this.hallsList == null) {
            this.hallsList = new ArrayList<>();
        }
        hall.setCinema(this);
        this.hallsList.add(hall);
    }

    public void addMovieSession(MovieSession session) {
        session.setCinema(this);
        this.sessions.add(session);
    }

}
