package ru.senina.itmo.web.web_lab_3.validators.areaCheckers;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import ru.senina.itmo.web.web_lab_3.entities.Coordinates;

import java.util.ArrayList;

public class AreaChecker {
    @Getter
    private final ArrayList<AreaCheckCondition> listOfConditions;
    public AreaChecker(ArrayList<AreaCheckCondition> listOfConditions) {
        this.listOfConditions = listOfConditions;
    }

    public boolean check(@NotNull Coordinates coordinates) {
        double x = coordinates.getX();
        double y = coordinates.getY();
        double r = coordinates.getR();
        return listOfConditions.stream().allMatch(condition -> condition.validate(x, y, r));
    }
}
