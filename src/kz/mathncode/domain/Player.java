package kz.mathncode.domain;

import java.util.List;

import kz.mathncode.domain.enums.Color;
import kz.mathncode.domain.enums.DigitalCoordinate;
import kz.mathncode.domain.enums.LetterCoordinate;
import kz.mathncode.domain.unit.SimpleUnit;
import kz.mathncode.domain.unit.Unit;

/**
 * @author Aleksandr Nedokushev
 * @created 14.12.2022
 */
public class Player {

    private Color color;

    public Player(Color color) {

        this.color = color;
    }

    public List<Unit> initUnits() {

        if (color == Color.WHITE) {
            // todo расставить белые пешки с 1 по 3 линии
            for (int line = 1; line <= 3; line++) {
                for (int column = 1; column <= 8; column++) {
                    if (line % 2 == column % 2) {
                        // line = 1, column = 1
                        // column -> LetterCoordinate
                        // line -> DigitalCoordinate
                        new SimpleUnit(color, new Coordinates());
                    }
                }
            }
        } else {
            // todo расставить черные пешки с 6 по 8 линии
        }

        new SimpleUnit(color, new Coordinates(LetterCoordinate.A, DigitalCoordinate.ONE));
    }
}
