package com.avada.kino.models;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;

import static com.avada.kino.util.UtilConstant.MAX_SIZE;
import static javax.persistence.CascadeType.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cinema extends BasicEntity {
    @Column(length = 3048)
    @Size(max = 3048, message = MAX_SIZE + 3048)
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

    @OneToMany(mappedBy = "cinema", fetch = FetchType.LAZY, cascade = ALL)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Hall> hallsList;

    @OneToMany(mappedBy = "cinema", cascade = ALL)
    private List<MovieSession> sessions;

    public void addHall(Hall hall) {
        if (hallsList == null) {
            hallsList = new ArrayList<>();
        }
        hall.setCinema(this);
        hallsList.add(hall);
    }

    public void addMovieSession(MovieSession session) {
        session.setCinema(this);
        this.sessions.add(session);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cinema)) return false;
        if (!super.equals(o)) return false;
        Cinema cinema = (Cinema) o;
        return Objects.equals(conditions, cinema.conditions) &&
                Objects.equals(banner, cinema.banner) &&
                Objects.equals(city, cinema.city) &&
                Objects.equals(hallsList, cinema.hallsList) &&
                Objects.equals(sessions, cinema.sessions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), conditions, banner, city, hallsList, sessions);
    }
}
