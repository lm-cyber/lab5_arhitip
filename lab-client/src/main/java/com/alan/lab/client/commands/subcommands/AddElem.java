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

public final class AddElem {
    private AddElem() {
    }

    public static Person add(boolean newID, UserInputManager userInputManager, OutputManager outputManager, CollectionManager collectionManager) throws InvalidValuesException, InvalidEmptyLineException, InvalidPassportIDSizeException {
        Coordinates.CoordinatesBuilder coordinatesBuilder = Coordinates.builder();
        Person.PersonBuilder personBuilder = Person.builder();
        Location.LocationBuilder locationBuilder = Location.builder();
        locationBuilder.z(userInputManager.readLongValue("locationZ(Long)", outputManager));
        locationBuilder.x(userInputManager.readDoubleValue("locationX(Double)", outputManager));
        locationBuilder.y(userInputManager.readIntegerValue("locationY(Integer)", outputManager));
        coordinatesBuilder.x(userInputManager.readFloatValue("coordinatesX(Float)", outputManager));
        coordinatesBuilder.y(userInputManager.readFloatValue("coordinatesY(Float)", outputManager));
        personBuilder.location(locationBuilder.build());
        personBuilder.coordinates(coordinatesBuilder.build());
        personBuilder.height(userInputManager.readFloatValue("height(Float)", outputManager));
        outputManager.println("enter name: ");
        personBuilder.name(userInputManager.nextLine());
        personBuilder.hairColor(userInputManager.readHairColorValue(" RED or GREEN or ORANGE", outputManager));
        if (newID) {
            personBuilder.id(collectionManager.getNewID());
        } else {
            personBuilder.id(0L);
        }
        personBuilder.creationDate();
        personBuilder.birthday(userInputManager.readBirthdayValue("birthday patern\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}", outputManager));
        outputManager.println("enter PassportID: ");
        personBuilder.passportID(userInputManager.nextLine());
        return personBuilder.build();

    }

}
