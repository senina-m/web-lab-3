package ru.senina.itmo.web.web_lab_3.validators;

import ru.senina.itmo.web.web_lab_3.entities.Coordinates;
import ru.senina.itmo.web.web_lab_3.exceptions.CoordinatesOutOfBoundsException;

import javax.enterprise.context.Dependent;

@Dependent
public class CoordinatesValidator {
    public void validate(Coordinates coordinates){
        double x = coordinates.getX();
        double y = coordinates.getY();
        double r = coordinates.getR();

        /* if value was integer but stored like double rounding and casting
        it do long wouldn't change it but we would be able to check if it was integer */
        if (!(x >= -4 && x <= 4) && !(Math.round(x) == (long) x)) {
            throw new CoordinatesOutOfBoundsException(x, "x");
        }
        if (!(y >= -5 && y <= 5)) {
            throw new CoordinatesOutOfBoundsException(y, "y");
        }
        if (!(r >= 2 && r <= 5)) {
            throw new CoordinatesOutOfBoundsException(r, "r");
        }
    }

}