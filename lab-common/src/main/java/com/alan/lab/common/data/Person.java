package com.alan.lab.common.data;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Person implements Comparable<Person> {
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
    private Float height; //Поле может быть null, Значение поля должно быть больше 0
    @NonNull
    private LocalDateTime birthday; //Поле не может быть null
    @NonNull
    private String passportID; //Значение этого поля должно быть уникальным, Длина строки не должна быть больше 49, Длина строки должна быть не меньше 6, Поле не может быть null
    private Color hairColor; //Поле может быть null
    private Location location; //Поле может быть null

    @Override
    public String toString() {
        return "Person"
                + "\nid=" + id
                + "\nname=" + name
                + "\ncoordinates" + coordinates
                + "\ncreationDate=" + creationDate
                + "\nheight=" + height
                + "\nbirthday=" + birthday
                + "\npassportID=" + passportID
                + "\nhairColor=" + hairColor
                + "\nlocation" + location;
    }

    public Float getHeight() {
        return height != null ? height : 0F;
    }

    @Override
    public int compareTo(@NonNull Person person) {
        return person.getHeight().compareTo(this.getHeight());
    }


}
