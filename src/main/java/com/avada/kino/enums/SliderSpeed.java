package com.avada.kino.enums;

public enum SliderSpeed {
    TWO(1, 2),
    THREE(2, 3),
    FOR(3, 4),
    FIVE(4, 5);

    private int speed;

    SliderSpeed(int id, int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }
}
