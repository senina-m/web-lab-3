package ru.senina.itmo.web.web_lab_3.validators;

import ru.senina.itmo.web.web_lab_3.entities.Coordinates;
import ru.senina.itmo.web.web_lab_3.exceptions.CoordinatesOutOfBoundsException;
import ru.senina.itmo.web.web_lab_3.exceptions.NoCoordinatesParametersInRequest;

import javax.enterprise.context.Dependent;
import java.io.Serializable;
import java.util.Optional;

@Dependent
public class CoordinatesValidator implements Serializable {
    public void validate(Coordinates coordinates){
        double x = Optional.of(coordinates.getX()).orElseThrow(NoCoordinatesParametersInRequest::new);
        double y = Optional.of(coordinates.getY()).orElseThrow(NoCoordinatesParametersInRequest::new);
        double r = Optional.of(coordinates.getR()).orElseThrow(NoCoordinatesParametersInRequest::new);

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