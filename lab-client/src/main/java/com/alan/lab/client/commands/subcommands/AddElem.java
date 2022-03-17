package com.alan.lab.client.commands.subcommands;

import com.alan.lab.client.data.Color;
import com.alan.lab.client.data.Coordinates;
import com.alan.lab.client.data.Location;
import com.alan.lab.client.data.Person;

import java.time.LocalDateTime;

public final class AddElem {
    private static final int K3 = 3;
    private static final int K4 = 4;
    private static final int K5 = 5;
    private static final int K6 = 6;
    private static final int K7 = 7;
    private static final int K8 = 8;
    private static final int K9 = 9;

    private AddElem() {
    }

    public static Person add(String arg) {
        String[] lines = arg.split(" ");
        try {


            String name = lines[0];
            Float coordinatesX = Float.parseFloat(lines[1]);
            Float coordinatesY = Float.parseFloat(lines[2]);
            Coordinates coordinates = new Coordinates(coordinatesX, coordinatesY);
            Float height = Float.parseFloat(lines[K3]);
            LocalDateTime birthday = LocalDateTime.parse(lines[K4]);
            String passportID = lines[K5];
            Color hairColor = Color.valueOf(lines[K6]);
            Double locationX = Double.parseDouble(lines[K7]);
            Integer locationY = Integer.parseInt(lines[K8]);
            Long locationZ = Long.parseLong(lines[K9]);
            Location location = new Location(locationX, locationY, locationZ);
            return new Person(name, coordinates, height, birthday, passportID, hairColor, location);
        } catch (Exception e) {
            return null;
        }
    }
}
