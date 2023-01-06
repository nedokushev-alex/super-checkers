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
public class SimpleUnit extends AbstractUnit {

    public SimpleUnit(Color color, Coordinates coordinates) {

        super(color, coordinates);
    }

    @Override
    public Action determineAction(Coordinates startCoordinates, Coordinates finishCoordinates,
            Board board) throws GameException {

        int diffLine = getDiffLine(startCoordinates, finishCoordinates);
        int diffColumn = getDiffColumn(startCoordinates, finishCoordinates);

        // проверка "перемещение ли это?"
        if (Math.abs(diffLine) == 1 && Math.abs(diffColumn) == 1) {
            if ((color == Color.WHITE && diffLine == 1)
                    || (color == Color.BLACK && diffLine == -1)) {
                return new Action(ActionType.MOVE, null);
            }

            // проверка "рубка ли это?"
        } else if (Math.abs(diffLine) == 2 && Math.abs(diffColumn) == 2) {

            int victimLine = (startCoordinates.getDigital().getLineNumber() +
                    finishCoordinates.getDigital().getLineNumber()) / 2;
            int victimColumn = (startCoordinates.getLetter().getColumnNumber() +
                    finishCoordinates.getLetter().getColumnNumber()) / 2;

            Coordinates victimCoord = new Coordinates(victimColumn, victimLine);

            Unit victim = board.getUnitByCoordinates(victimCoord);
            if (victim != null && victim.getColor() != color) {
                return new Action(ActionType.CHOP, victim);
            }
        }

        throw new GameException("Некорректный ход для пешки!");
    }

    @Override
    public boolean hasPossibleVictim(Board board) {

        int unitLine = coordinates.getDigital().getLineNumber();
        int unitColumn = coordinates.getLetter().getColumnNumber();

        int[] lines = {unitLine - 1, unitLine + 1};
        int[] columns = {unitColumn - 1, unitColumn + 1};

        for (int line : lines) {
            for (int column : columns) {
                try {
                    Coordinates coordPossibleVictim = new Coordinates(column, line);
                    Unit possibleVictim = board.getUnitByCoordinates(coordPossibleVictim);
                    if (possibleVictim != null && possibleVictim.getColor() != color) {
                        int landingLine = unitLine + (line - unitLine) * 2;
                        int landingColumn = unitColumn + (column - unitColumn) * 2;
                        // column(жертвы) = 4, unitColumn = 5
                        // landingColumn = 5 + (4-5) * 2
                        Coordinates landingCoord = new Coordinates(landingColumn, landingLine);
                        if (board.isEmptyField(landingCoord)) {
                            return true;
                        }
                    }
                } catch (Exception ex) {

                }
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
