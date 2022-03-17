package com.alan.lab.client.data;

import org.jetbrains.annotations.NotNull;

public class Coordinates {
    private static final Float MINX = -601F;
    private static final Float MINY = -904F;
    private float x; //Значение поля должно быть больше -601
    private Float y; //Значение поля должно быть больше -904, Поле не может быть null

    public Coordinates(float x, @NotNull Float y) throws Exception {
        if (x <= MINX) {
            throw new Exception("x<=-601");
        }
        if (y <= MINY) {
            throw new Exception("y<=-904");
        }
        this.x = x;
        this.y = y;


    }

    @Override
    public String toString() {
        return "Coordinates{"
                + "\n\t\t x=" + x
                + "\n\t\t y=" + y
                + "\n\t}";
    }
}
