package kz.mathncode.domain.unit;

import kz.mathncode.domain.Coordinates;
import kz.mathncode.domain.enums.Color;

/**
 * @author Aleksandr Nedokushev
 * @created 14.12.2022
 */
public class DameUnit extends AbstractUnit {

    public DameUnit(Color color, Coordinates coordinates) {

        super(color, coordinates);
    }

    @Override
    public boolean isCorrectMove(Coordinates startCoordinates, Coordinates finishCoordinates) {

        // todo потом
        return false;
    }
}
