package kz.mathncode.domain;

import java.util.ArrayList;
import java.util.List;

import kz.mathncode.domain.enums.Color;
import kz.mathncode.domain.enums.DigitalCoordinate;
import kz.mathncode.domain.enums.LetterCoordinate;
import kz.mathncode.domain.unit.DameUnit;
import kz.mathncode.domain.unit.SimpleUnit;
import kz.mathncode.domain.unit.Unit;
import kz.mathncode.exceptions.GameException;

/**
 * @author Aleksandr Nedokushev
 * @created 14.12.2022
 */
public class Board {

    private List<Unit> units = new ArrayList<>();

    public List<Unit> getUnits() {

        return units;
    }

    public String show() throws GameException {

        StringBuilder builder = new StringBuilder();
        for (int line = 8; line >= 1; line--) {
            builder.append(line).append("|");

            for (int column = 1; column <= 8; column++) {
                if (column % 2 == line % 2) {
                    LetterCoordinate letter = LetterCoordinate.getByColumnNumber(column);
                    DigitalCoordinate digital = DigitalCoordinate.getByLineNumber(line);
                    Coordinates fieldCoordinates = new Coordinates(letter, digital);

                    Unit unit = getUnitByCoordinates(fieldCoordinates); // есть ли юнит с координатой (A, EIGHT)

                    if (unit == null) {
                        builder.append("_");
                    } else {
                        String unitView = unit.getView();
                        builder.append(unitView);
                    }
                } else {
                    builder.append("_");
                }

                builder.append("|");
            }
            builder.append("\n");
        }

        builder.append("  A B C D E F G H");

        return builder.toString();
    }

    public Unit getUnitByCoordinates(Coordinates fieldCoordinates) { // (A, EIGHT)

        for (Unit unit : units) {
            Coordinates unitCoordinates = unit.getCoordinates(); // (A, ONE) , (C, ONE)

            if (unitCoordinates.equals(fieldCoordinates)) {
                return unit;
            }
        }

        return null;
    }

    public boolean isEmptyField(Coordinates coordinates) {

        Unit unitOnField = getUnitByCoordinates(coordinates);
        if (unitOnField == null) {
            return true;
        } else {
            return false;
        }
    }

    public Unit relocateUnit(Unit unit, Coordinates coordinates) {

        if (unit instanceof SimpleUnit simpleUnit) {
            // это была пешка
            if ((simpleUnit.getColor() == Color.WHITE
                            && coordinates.getDigital() == DigitalCoordinate.EIGHT)
                || (simpleUnit.getColor() == Color.BLACK
                            && coordinates.getDigital() == DigitalCoordinate.ONE)) {

                // превращение в дамку
                units.remove(simpleUnit);

                DameUnit dameUnit = new DameUnit(simpleUnit.getColor(), coordinates);
                units.add(dameUnit);

                return dameUnit;

            } else {
                simpleUnit.setCoordinate(coordinates);
                return unit;
            }

        } else {
            // это была дамка
            unit.setCoordinate(coordinates);
            return unit;
        }
    }
}
