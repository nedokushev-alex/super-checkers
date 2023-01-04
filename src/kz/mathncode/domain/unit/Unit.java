package kz.mathncode.domain.unit;

import kz.mathncode.domain.Action;
import kz.mathncode.domain.Board;
import kz.mathncode.domain.Coordinates;
import kz.mathncode.domain.enums.ActionType;
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

    Action determineAction(Coordinates startCoordinates, Coordinates finishCoordinates,
            Board board) throws GameException;

    boolean hasPossibleVictim(Board board);
}
