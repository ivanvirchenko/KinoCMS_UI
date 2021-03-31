package com.avada.kino.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Embeddable
@NoArgsConstructor
public class HallPlace {
    private int rawNum;
    private int placeNum;
    private boolean isFree;

    public HallPlace(int rawNum, int placeNum, boolean isFree) {
        this.rawNum = rawNum;
        this.placeNum = placeNum;
        this.isFree = isFree;
    }
}
