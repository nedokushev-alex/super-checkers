package kz.mathncode.domain.unit;

import kz.mathncode.domain.Coordinates;
import kz.mathncode.domain.enums.Color;

/**
 * @author Aleksandr Nedokushev
 * @created 14.12.2022
 */
public interface Unit {

    Coordinates getCoordinates();

    void setCoordinate(Coordinates coordinates);

    Color getColor();

    boolean isCorrectMove(Coordinates startCoordinates, Coordinates finishCoordinates);
}
