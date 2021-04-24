package com.avada.kino.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Place {
    private boolean isTaken;
    private int rowNumber;
    private int placeNumber;
}
