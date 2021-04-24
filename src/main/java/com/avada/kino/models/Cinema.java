package com.avada.kino.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

import static com.avada.kino.util.StringsConstant.MAX_SIZE;
import static javax.persistence.CascadeType.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
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

    @OneToMany(mappedBy = "cinema", fetch = FetchType.LAZY, cascade = ALL)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Schedule> schedules;


    public void addHall(Hall hall) {
        if (hallsList == null) {
            hallsList = new ArrayList<>();
        }
        hall.setCinema(this);
        hallsList.add(hall);
    }
}
