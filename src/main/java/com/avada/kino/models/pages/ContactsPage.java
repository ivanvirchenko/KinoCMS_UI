package com.avada.kino.models.pages;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class ContactsPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String cinemaName;
    private String address;
    private String coordinates;
    private String logoUrl;

    public ContactsPage(String cinemaName, String address, String coordinates, String logoUrl) {
        this.cinemaName = cinemaName;
        this.address = address;
        this.coordinates = coordinates;
        this.logoUrl = logoUrl;
    }
}
