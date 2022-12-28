package kz.mathncode.domain.unit;

import kz.mathncode.domain.Board;
import kz.mathncode.domain.Coordinates;
import kz.mathncode.domain.enums.Color;
import kz.mathncode.exceptions.GameException;

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

        int diffLine = getDiffLine(startCoordinates, finishCoordinates);
        int diffColumn = getDiffColumn(startCoordinates, finishCoordinates);

        if (color == Color.WHITE) {
            if (diffLine == 1 && Math.abs(diffColumn) == 1) {
                return true;
            }
        } else {
            if (diffLine == -1 && Math.abs(diffColumn) == 1) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isCorrectChop(Coordinates startCoordinates, Coordinates finishCoordinates,
            Board board) throws GameException {

        int diffLine = getDiffLine(startCoordinates, finishCoordinates);
        int diffColumn = getDiffColumn(startCoordinates, finishCoordinates);

        if (Math.abs(diffLine) == 2 && Math.abs(diffColumn) == 2) {

            int victimLine = (startCoordinates.getDigital().getLineNumber() +
                    finishCoordinates.getDigital().getLineNumber()) / 2;
            int victimColumn = (startCoordinates.getLetter().getColumnNumber() +
                    finishCoordinates.getLetter().getColumnNumber()) / 2;

            Coordinates victimCoord = new Coordinates(victimColumn, victimLine);

            Unit victim = board.getUnitByCoordinates(victimCoord);
            if (victim != null && victim.getColor() != color) {
                return true;
            }
        }

        return false;
    }

    private static int getDiffLine(Coordinates startCoordinates, Coordinates finishCoordinates) {

        int startLine = startCoordinates.getDigital().getLineNumber();
        int finishLine = finishCoordinates.getDigital().getLineNumber();
        return finishLine - startLine;
    }

    private static int getDiffColumn(Coordinates startCoordinates, Coordinates finishCoordinates) {

        int startColumn = startCoordinates.getLetter().getColumnNumber();
        int finishColumn = finishCoordinates.getLetter().getColumnNumber();
        return finishColumn - startColumn;
    }
}
