package com.alan.lab.client.data;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class Location implements Serializable {
    private double x;
    private int y;
    private Long z; //Поле не может быть null

    public Location(double x, int y, @NotNull Long z) {
        this.x = x;
        this.y = y;
        this.z = z;

    }

    @Override
    public String toString() {
        return "Location{"
                + "\n\t\t x=" + x
                + "\n\t\t y=" + y
                + "\n\t\t z=" + z
                + "\n\t}";
    }
}
