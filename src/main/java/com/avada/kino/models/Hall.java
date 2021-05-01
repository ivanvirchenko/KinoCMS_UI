package com.avada.kino.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static javax.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true)
public class Hall extends BasicEntity {
    @Embedded
    @AttributeOverrides(
            {
                    @AttributeOverride(name = "name", column = @Column(name = "banner_name")),
                    @AttributeOverride(name = "url", column = @Column(name = "banner_url"))
            }
    )
    private Image banner;
    @ManyToOne(cascade = {MERGE, REFRESH, DETACH})
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Place> places;
    @OneToMany(mappedBy = "hall", cascade = ALL)
    private List<MovieSession> movieSessions;

    public void addPlace(Place place) {
        if (this.places == null) {
            this.places = new ArrayList<>();
        }
        this.places.add(place);
    }

    public void fillTheHall(int rowCount, int placesInTheRow) {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < placesInTheRow; j++) {
                addPlace(new Place(false, i + 1, j + 1));
            }
        }
    }
}
