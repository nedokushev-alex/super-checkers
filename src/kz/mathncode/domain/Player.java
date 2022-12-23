package kz.mathncode.domain;

import java.util.ArrayList;
import java.util.List;

import kz.mathncode.domain.enums.Color;
import kz.mathncode.domain.enums.DigitalCoordinate;
import kz.mathncode.domain.enums.LetterCoordinate;
import kz.mathncode.domain.unit.SimpleUnit;
import kz.mathncode.domain.unit.Unit;
import kz.mathncode.exceptions.GameException;

/**
 * @author Aleksandr Nedokushev
 * @created 14.12.2022
 */
public class Player {

    private Color color;

    public Player(Color color) {

        this.color = color;
    }

    public Color getColor() {

        return color;
    }

    public List<Unit> initUnits() throws GameException {

        if (color == Color.WHITE) {
            return initUnits(1,3);
        } else {
            return initUnits(6,8);
        }
    }

    private List<Unit> initUnits(int startLine, int finishLine) throws GameException {

        List<Unit> units = new ArrayList<>();

        for (int line = startLine; line <= finishLine; line++) {
            for (int column = 1; column <= 8; column++) {
                if (line % 2 == column % 2) {
                    LetterCoordinate letter = LetterCoordinate.getByColumnNumber(column);
                    DigitalCoordinate digital = DigitalCoordinate.getByLineNumber(line);
                    Coordinates coordinates = new Coordinates(letter, digital);

                    SimpleUnit simpleUnit = new SimpleUnit(color, coordinates);
                    units.add(simpleUnit);
                }
            }
        }

        return units;
    }
}
