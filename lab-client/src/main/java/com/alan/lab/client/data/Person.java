package com.alan.lab.client.data;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;


public class Person implements Serializable, Comparable<Person> {
    private static final int MAX = 49;
    private static final int MIN = 6;
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Float height; //Поле может быть null, Значение поля должно быть больше 0
    private java.time.LocalDateTime birthday; //Поле не может быть null
    private String passportID; //Значение этого поля должно быть уникальным, Длина строки не должна быть больше 49, Длина строки должна быть не меньше 6, Поле не может быть null
    private Color hairColor; //Поле может быть null
    private Location location; //Поле может быть null

    public Person(@NotNull String name,
                  @NotNull Coordinates coordinates,
                  Float height,
                  @NotNull java.time.LocalDateTime birthday,
                  @NotNull String passportID,
                  Color hairColor,
                  Location location) throws Exception {
        if (name.length() == 0) {
            throw new Exception("name.length()==0");
        }
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = java.time.LocalDateTime.now();
        if (height != null) {
            if (height <= 0) {
                throw new Exception("height<=0");
            }
        }
        this.height = height;
        this.birthday = birthday;
        this.passportID = passportID;
        this.hairColor = hairColor;
        this.location = location;

    }

    public void update() {
        creationDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Float getHeight() {
        return height;
    }

    public String getPassportID() {
        return passportID;
    }


    @Override
    public int compareTo(@NotNull Person person) {

        return person.getHeight().compareTo(this.getHeight());
    }

    @Override
    public String toString() {
        return "\nPerson{" + "\n id=" + id + "\n name='" + name + '\'' + "\n coordinates=\n\t" + coordinates + "\n creationDate=" + creationDate + "\n height=" + height + "\n birthday=" + birthday + "\n passportID='" + passportID + '\'' + "\n hairColor=" + hairColor + "\n location=\n\t" + location + "\n}";
    }
}
