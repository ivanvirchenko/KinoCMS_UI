package com.avada.kino.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Hall extends BasicEntity{

    private String banner_url;

    @ManyToOne(cascade = {MERGE, REFRESH, DETACH})
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "places")
    private List<HallPlace> places;

}
