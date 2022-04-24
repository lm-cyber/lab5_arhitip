package com.alan.lab.common.data;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coordinates {
    private static final Float MINX = -601F;
    private static final Float MINY = -904F;
    private float x; //Значение поля должно быть больше -601
    @NonNull
    private Float y; //Значение поля должно быть больше -904, Поле не может быть null

    @Override
    public String toString() {
        return "\n\tx=" + x
                + "\n\ty=" + y + "\n";
    }
}
