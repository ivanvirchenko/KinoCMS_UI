package com.avada.kino.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "cinema", fetch = FetchType.LAZY, cascade = ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Hall> hallsList;

    @OneToMany(mappedBy = "cinema", fetch = FetchType.LAZY, cascade = ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Schedule> schedules;

    public Cinema(String name, String description, String imgUrl, Seo seo, String conditions, String banner_url, City city) {
        super(name, description, imgUrl, seo);
        this.conditions = conditions;
        this.banner_url = banner_url;
        this.city = city;
    }

    public void add(Hall hall) {
        if (hallsList == null) {
            hallsList = new ArrayList<>();
        }
        hall.setCinema(this);
        hallsList.add(hall);
    }

    public void add(Schedule schedule) {
        if (schedules == null) {
            schedules = new ArrayList<>();
        }
        schedule.setCinema(this);
        schedules.add(schedule);
    }
}
