package com.avada.kino.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    private String cinemaName;
    private String address;
    private String coordinates;
    @Embedded
    @AttributeOverrides(
            {
                    @AttributeOverride(name = "name", column = @Column(name = "logo_name")),
                    @AttributeOverride(name = "url", column = @Column(name = "logo_url"))
            }
    )
    private Image logo;
}
