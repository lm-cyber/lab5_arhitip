package com.alan.lab.client.data;



import lombok.*;


import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location implements Serializable {
    private double x;
    private int y;
    @NonNull
    private Long z; //Поле не может быть null

    @Override
    public String toString() {
        return "\n\tx=" + x +
                "\n\ty=" + y +
                "\n\tz=" + z + "\n";
    }
}

