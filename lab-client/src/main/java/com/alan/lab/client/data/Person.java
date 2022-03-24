package com.alan.lab.client.data;

import com.alan.lab.client.exceptions.InvalidEmptyLineException;
import com.alan.lab.client.exceptions.InvalidPassportIDSizeException;
import com.alan.lab.client.exceptions.InvalidValuesException;
import lombok.NonNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Serializable, Comparable<Person> {
    private static final int MAXLENPASSPORTID = 49;
    private static final int MINLENPASSPORTID = 6;
    @NonNull
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @NonNull
    private String name; //Поле не может быть null, Строка не может быть пустой
    @NonNull
    private Coordinates coordinates; //Поле не может быть null
    @NonNull
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @NonNull
    private Float height; //Поле может быть null, Значение поля должно быть больше 0
    @NonNull
    private LocalDateTime birthday; //Поле не может быть null
    @NonNull
    private String passportID; //Значение этого поля должно быть уникальным, Длина строки не должна быть больше 49, Длина строки должна быть не меньше 6, Поле не может быть null
    @NonNull
    private Color hairColor; //Поле может быть null
    @NonNull
    private Location location; //Поле может быть null

    public class PersonBuilder {
        public PersonBuilder id(Long id) {
            Person.this.id = id;
            return this;
        }

        public PersonBuilder name(String name) throws InvalidEmptyLineException {
            if (name.equals("")) {
                throw new InvalidEmptyLineException("name is empty");
            }
            Person.this.name = name;
            return this;
        }

        public PersonBuilder coordinates(Coordinates coordinates) {
            Person.this.coordinates = coordinates;
            return this;
        }

        public PersonBuilder creationDate() {
            Person.this.creationDate = LocalDateTime.now();
            return this;
        }

        public PersonBuilder height(Float height) throws InvalidValuesException {
            if (height <= 0) {
                throw new InvalidValuesException("height<=0");
            }
            Person.this.height = height;
            return this;
        }

        public PersonBuilder birthday(LocalDateTime birthady) {
            Person.this.birthday = birthady;
            return this;
        }

        public PersonBuilder passportID(String passportID) throws InvalidPassportIDSizeException {
            if (passportID.length() > MAXLENPASSPORTID || passportID.length() < MINLENPASSPORTID) {
                throw new InvalidPassportIDSizeException("passportID" + (passportID.length() > MAXLENPASSPORTID ? ">49" : "<6"));
            }
            Person.this.passportID = passportID;
            return this;
        }

        public PersonBuilder hairColor(Color hairColor) {
            Person.this.hairColor = hairColor;
            return this;
        }

        public PersonBuilder location(Location location) {
            Person.this.location = location;
            return this;
        }

        public Person build() {
            return Person.this;
        }
    }

    public static PersonBuilder builder() {
        return new Person().new PersonBuilder();
    }

    @Override
    public String toString() {
        return "Person" +
                "\nid=" + id +
                "\nname=" + name +
                "coordinates" + coordinates +
                "\ncreationDate=" + creationDate +
                "\nheight=" + height +
                "\nbirthday=" + birthday +
                "\npassportID=" + passportID +
                "\nhairColor=" + hairColor +
                "\nlocation" + location;
    }

    @Override
    public int compareTo(@NonNull Person person) {

        return person.getHeight().compareTo(this.getHeight());
    }

}
