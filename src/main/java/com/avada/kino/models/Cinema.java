package com.avada.kino.models;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Cinema extends BasicEntity {
    private String conditions;
    private String banner_url;
    @ManyToOne(cascade = {MERGE, DETACH, PERSIST, REFRESH})
    @JoinColumn(name = "city_id")
    private City city;
    @Fetch(value = FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "cinema", fetch = FetchType.LAZY, cascade = ALL)
    private List<Hall> hallsList;
    @Fetch(value = FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "cinema", cascade = ALL, fetch = FetchType.LAZY)
    private List<Schedule> schedule;

    public Cinema(String name, String description, String imgUrl, List<Image> gallery, Seo seo, String conditions, String banner_url, City city, List<Hall> hallsList) {
        super(name, description, imgUrl, gallery, seo);
        this.conditions = conditions;
        this.banner_url = banner_url;
        this.city = city;
        this.hallsList = hallsList;
    }
}
