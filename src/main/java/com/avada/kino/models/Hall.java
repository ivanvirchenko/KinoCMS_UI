package com.avada.kino.models;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "hall", cascade = ALL)
    private List<MovieSession> movieSessions;

    @ElementCollection
    private List<Place> places;

    public void fillTheHall(int rows, int placesNum) {
        if (places == null) {
            places = new ArrayList<>();
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < placesNum; j++) {
                this.places.add(new Place(i + 1, j + 1, false));
            }
        }
    }

    @Data
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    static class Place {
        private int rowsNum;
        private int placesNum;
        private boolean isTaken;
    }

    public int getRowsCount(){
        if (places != null && !places.isEmpty()) {
            return places.get(places.size() - 1).rowsNum;
        }
        return 0;
    }
    public int getPlacesInRow() {
        if (places != null && !places.isEmpty()) {
            return places.size() / getRowsCount();
        }
        return 0;
    }
}
