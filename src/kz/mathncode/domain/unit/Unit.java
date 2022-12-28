package kz.mathncode.domain.unit;

import kz.mathncode.domain.Board;
import kz.mathncode.domain.Coordinates;
import kz.mathncode.domain.enums.Color;
import kz.mathncode.exceptions.GameException;

/**
 * @author Aleksandr Nedokushev
 * @created 14.12.2022
 */
public interface Unit {

    Coordinates getCoordinates();

    void setCoordinate(Coordinates coordinates);

    Color getColor();

    boolean isCorrectMove(Coordinates startCoordinates, Coordinates finishCoordinates);

    boolean isCorrectChop(Coordinates startCoordinates, Coordinates finishCoordinatesBoard,
            Board board) throws GameException;
}
