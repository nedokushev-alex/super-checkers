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

    @Override
    public boolean hasPossibleVictim(Board board) {

        int unitLine = coordinates.getDigital().getLineNumber(); // 8
        int unitColumn = coordinates.getLetter().getColumnNumber(); // 5

        int[] appendersLine = { -1, 1 };
        int[] appendersColumn = { -1, 1 };

        for (int appenderLine : appendersLine) {
            for (int appenderColumn : appendersColumn) {
                // appenderLine = -1, appenderColumn = -1 - первое направление
                // appenderLine = -1, appenderColumn = 1 - второе направление
                // appenderLine = 1, appenderColumn = -1 - третье направление
                // appenderLine = 1, appenderColumn = 1 - четвёртое направление

                int line = unitLine; // 8
                int column = unitColumn; // 5

                Unit foundUnit = null;
                boolean stop = false;

                do {
                    try {
                        line = line + appenderLine; // 8+(-1)=7 // 7+(-1)=6 // 6-1=5 //4 //3
                        column = column + appenderColumn; // 5+(-1)=4 // 4+(-1)=3 // 3-1=2 //1 //0
                        Coordinates coord = new Coordinates(column, line); // D7 // C6 // B5
                        Unit unit = board.getUnitByCoordinates(coord);
                        if (unit != null) {
                            foundUnit = unit; // черная пешка на B5
                            stop = true;
                        }
                    } catch (GameException ex) {
                        stop = true;
                    }
                } while (!stop);

                // если мы натолкнулись на юнит И он цвета соперника
                if (foundUnit != null && foundUnit.getColor() != color) {
                    // надо проверить поле ЗА этим юнитом: если поле пустое - то рубка возможна,\
                    // а если поле непустое - то рубка НЕвозможна
                    try {
                        int landingLine = line + appenderLine; //5-1=4
                        int landingColumn = column + appenderColumn; //2-1=1
                        Coordinates coord = new Coordinates(landingColumn, landingLine); //A4
                        if (board.isEmptyField(coord)) {
                            return true;
                        }
                    } catch (GameException ex) {

                    }
                }
            }
        }

        return false;
    }

    @Override
    public boolean hasPossibleMove(Board board) {

        int unitLine = coordinates.getDigital().getLineNumber();
        int unitColumn = coordinates.getLetter().getColumnNumber();

        int[] lines = { unitLine - 1, unitLine + 1 };
        int[] columns = {unitColumn - 1, unitColumn + 1};

        for (int line : lines) {
            for (int column : columns) {
                try {
                    Coordinates coord = new Coordinates(column, line);
                    if (board.isEmptyField(coord)) {
                        return true;
                    }
                } catch (GameException e) {
                    // поля не существует, сходить туда не можем
                }
            }
        }

        return false;
    }

    private static List<Unit> getUnitsOnTrack(Coordinates startCoordinates,
            Coordinates finishCoordinates, Board board) throws GameException {

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

        while (line + appenderLine != finishLine) { // finishLine

            line = line + appenderLine; // 7
            column = column + appenderColumn; // 4
            Coordinates coord = new Coordinates(column, line);
            Unit unit = board.getUnitByCoordinates(coord);
            if (unit != null) {
                unitsOnTrack.add(unit);
            }
        }

        return unitsOnTrack;
    }
}
