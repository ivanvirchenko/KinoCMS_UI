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
    private Image logo;
}
