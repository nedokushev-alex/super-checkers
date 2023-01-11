package kz.mathncode.domain.unit;

import kz.mathncode.domain.Coordinates;
import kz.mathncode.domain.enums.Color;
import kz.mathncode.domain.unit.Unit;

/**
 * @author Aleksandr Nedokushev
 * @created 14.12.2022
 */
public abstract class AbstractUnit implements Unit {

    protected Color color;

    protected Coordinates coordinates;

    protected AbstractUnit(Color color, Coordinates coordinates) {

        this.color = color;
        this.coordinates = coordinates;
    }

    @Override
    public Coordinates getCoordinates() {

        return coordinates;
    }

    @Override
    public void setCoordinate(Coordinates coordinates) {

        this.coordinates = coordinates;
    }

    @Override
    public Color getColor() {

        return color;
    }

    protected static int getDiffLine(Coordinates startCoordinates, Coordinates finishCoordinates) {

        int startLine = startCoordinates.getDigital().getLineNumber();
        int finishLine = finishCoordinates.getDigital().getLineNumber();
        return finishLine - startLine;
    }

    protected static int getDiffColumn(Coordinates startCoordinates, Coordinates finishCoordinates) {

        int startColumn = startCoordinates.getLetter().getColumnNumber();
        int finishColumn = finishCoordinates.getLetter().getColumnNumber();
        return finishColumn - startColumn;
    }
}
