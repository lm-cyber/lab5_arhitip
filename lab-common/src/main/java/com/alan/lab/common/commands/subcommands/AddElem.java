package com.alan.lab.common.commands.subcommands;

import com.alan.lab.common.data.Coordinates;
import com.alan.lab.common.data.Location;
import com.alan.lab.common.data.Person;
import com.alan.lab.common.utility.OutputManager;
import com.alan.lab.common.utility.UserInputManager;

import java.time.LocalDateTime;

public final class AddElem {
    private static final Float MINX = -601F;
    private static final Float MINY = -904F;
    private static final int MAXLENPASSPORTID = 49;
    private static final int MINLENPASSPORTID = 6;

    private AddElem() {
    }

    public static Person add( UserInputManager userInputManager, OutputManager outputManager) {
        Coordinates.CoordinatesBuilder coordinatesBuilder = Coordinates.builder();
        outputManager.println("add location(\"\")");
        Location location;
        if (!userInputManager.nextLine().equals("")) {
            Location.LocationBuilder locationBuilder = Location.builder();
            locationBuilder.z(userInputManager.readLongValue("locationZ(Long)", outputManager));
            locationBuilder.x(userInputManager.readDoubleValue("locationX(Double)", outputManager));
            locationBuilder.y(userInputManager.readIntegerValue("locationY(Integer)", outputManager));
            location = locationBuilder.build();
        } else {
            location = null;
        }
        coordinatesBuilder.x(userInputManager.readFloatValue("coordinatesX(Float)", outputManager, x -> x < MINX));
        coordinatesBuilder.y(userInputManager.readFloatValue("coordinatesY(Float)", outputManager, x -> x < MINY));
        Person.PersonBuilder personBuilder = Person.builder();
        personBuilder.location(location);
        personBuilder.coordinates(coordinatesBuilder.build());
        personBuilder.height(userInputManager.readFloatValueH("height(Float)", outputManager, x -> x <= 0));
        personBuilder.name(userInputManager.readStringWithPredicatValue("name", outputManager, x -> x.equals("")));
        personBuilder.hairColor(userInputManager.readHairColorValue(" RED or GREEN or ORANGE", outputManager));
        personBuilder.id(0L);
        personBuilder.creationDate(LocalDateTime.now());
        personBuilder.birthday(userInputManager.readBirthdayValue("birthday patern\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}", outputManager));
        personBuilder.passportID(userInputManager.readStringWithPredicatValue("passportId", outputManager, x -> {
            return x.length() < MINLENPASSPORTID || x.length() > MAXLENPASSPORTID ;
        }));
        return personBuilder.build();

    }

}
