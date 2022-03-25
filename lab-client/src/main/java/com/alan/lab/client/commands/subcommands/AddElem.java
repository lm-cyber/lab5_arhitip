package com.alan.lab.client.commands.subcommands;

import com.alan.lab.client.data.Coordinates;
import com.alan.lab.client.data.Location;
import com.alan.lab.client.data.Person;
import com.alan.lab.client.exceptions.InvalidEmptyLineException;
import com.alan.lab.client.exceptions.InvalidPassportIDSizeException;
import com.alan.lab.client.exceptions.InvalidValuesException;
import com.alan.lab.client.utility.CollectionManager;
import com.alan.lab.client.utility.OutputManager;
import com.alan.lab.client.utility.UserInputManager;

import java.time.LocalDateTime;

public final class AddElem {
    private static final Float MINX = -601F;
    private static final Float MINY = -904F;
    private static final int MAXLENPASSPORTID = 49;
    private static final int MINLENPASSPORTID = 6;

    private AddElem() {
    }

    public static Person add(boolean newID, UserInputManager userInputManager, OutputManager outputManager, CollectionManager collectionManager) throws InvalidValuesException, InvalidEmptyLineException, InvalidPassportIDSizeException {
        Coordinates.CoordinatesBuilder coordinatesBuilder = Coordinates.builder();
        Location.LocationBuilder locationBuilder = Location.builder();
        locationBuilder.z(userInputManager.readLongValue("locationZ(Long)", outputManager));
        locationBuilder.x(userInputManager.readDoubleValue("locationX(Double)", outputManager));
        locationBuilder.y(userInputManager.readIntegerValue("locationY(Integer)", outputManager));
        coordinatesBuilder.x(userInputManager.readFloatValue("coordinatesX(Float)", outputManager, x -> x < MINX));
        coordinatesBuilder.y(userInputManager.readFloatValue("coordinatesY(Float)", outputManager, x -> x < MINY));
        Person.PersonBuilder personBuilder = Person.builder();
        personBuilder.location(locationBuilder.build());
        personBuilder.coordinates(coordinatesBuilder.build());
        personBuilder.height(userInputManager.readFloatValue("height(Float)", outputManager, x -> x <= 0));
        personBuilder.name(userInputManager.readStringWithPredicatValue("name", outputManager, x -> x.equals("")));
        personBuilder.hairColor(userInputManager.readHairColorValue(" RED or GREEN or ORANGE", outputManager));
        if (newID) {
            personBuilder.id(collectionManager.getNewID());
        } else {
            personBuilder.id(0L);
        }
        personBuilder.creationDate(LocalDateTime.now());
        personBuilder.birthday(userInputManager.readBirthdayValue("birthday patern\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}", outputManager));
        personBuilder.passportID(userInputManager.readStringWithPredicatValue("passportId", outputManager, x -> {
            return x.length() < MINLENPASSPORTID || x.length() > MAXLENPASSPORTID || collectionManager.isContains(x);
        }));
        return personBuilder.build();

    }

}
