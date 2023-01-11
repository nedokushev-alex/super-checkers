package kz.mathncode.domain.unit;

import java.util.ArrayList;
import java.util.List;

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
public class DameUnit extends AbstractUnit {

    public DameUnit(Color color, Coordinates coordinates) {

        super(color, coordinates);
    }

    @Override
    public String getView() {

        return color == Color.WHITE ? "W" : "B";
    }

    @Override
    public Action determineAction(Coordinates startCoordinates, Coordinates finishCoordinates,
            Board board) throws GameException {

        int diffLine = getDiffLine(startCoordinates, finishCoordinates);
        int diffColumn = getDiffColumn(startCoordinates, finishCoordinates);

        if (Math.abs(diffLine) == Math.abs(diffColumn)) {

            List<Unit> unitsOnTrack = getUnitsOnTrack(startCoordinates, finishCoordinates, board);

            if (unitsOnTrack.isEmpty()) {
                return new Action(ActionType.MOVE, null);

            } else if (unitsOnTrack.size() == 1 && unitsOnTrack.get(0).getColor() != color) {
                Unit victim = unitsOnTrack.get(0);
                return new Action(ActionType.CHOP, victim);

            } else {
                throw new GameException("Некорректный ход для дамки!");
            }
        } else {
            throw new GameException("Некорректный ход для дамки!");
        }
    }

    private static List<Unit> getUnitsOnTrack(Coordinates startCoordinates, Coordinates finishCoordinates,
            Board board) throws GameException {

        int finishLine = finishCoordinates.getDigital().getLineNumber();
        int startLine = startCoordinates.getDigital().getLineNumber();
        int appenderLine = finishLine > startLine ? 1 : -1;
        // 4 > 8 -> false, то есть appenderLine = -1

        int finishColumn = finishCoordinates.getLetter().getColumnNumber();
        int startColumn = startCoordinates.getLetter().getColumnNumber();
        int appenderColumn = finishColumn > startColumn ? 1 : -1;
        // 1 > 5 -> false, то есть appenderColumn = -1

        List<Unit> unitsOnTrack = new ArrayList<>();
        int line = startLine; // 8
        int column = startColumn; // 5
        do {
            line = line + appenderLine; // 7 // 6 // 5
            column = column + appenderColumn; // 4 // 3 // 2
            Coordinates coord = new Coordinates(column, line);
            Unit unit = board.getUnitByCoordinates(coord);
            if (unit != null) {
                unitsOnTrack.add(unit);
            }
        } while (line + appenderLine != finishLine); // line + appenderLine = 4; finishLine = 4

        return unitsOnTrack;
    }

    @Override
    public boolean hasPossibleVictim(Board board) {

        return false;
    }
}
