package kz.mathncode.domain.unit;

import kz.mathncode.domain.Coordinates;
import kz.mathncode.domain.enums.Color;

/**
 * @author Aleksandr Nedokushev
 * @created 14.12.2022
 */
public class SimpleUnit extends AbstractUnit {

    public SimpleUnit(Color color, Coordinates coordinates) {

        super(color, coordinates);
    }

    @Override
    public boolean isCorrectMove(Coordinates startCoordinates, Coordinates finishCoordinates) {

        // БЕЛЫЕ : digital +1 , letter +/-1
        // ЧЕРНЫЕ : digital -1 , letter +/-1
        // return true
        // иначе

        // B6 -> A5
        // B6 -> C5

        int startLine = startCoordinates.getDigital().getLineNumber(); //6
        int finishLine = finishCoordinates.getDigital().getLineNumber(); //5
        int diffLine = finishLine - startLine; // 5-6 = -1

        int startColumn = startCoordinates.getLetter().getColumnNumber(); // 2 //2
        int finishColumn = finishCoordinates.getLetter().getColumnNumber(); // 1 //3
        int diffColumn = finishColumn - startColumn; // 1-2 = -1 // 3-2 = 1

        if (color == Color.WHITE) {
            if (diffLine == 1 && Math.abs(diffColumn) == 1) {
                return true;
            }
        } else {
            // todo логика для ЧЕРНЫХ
        }

        return false;
    }

}
