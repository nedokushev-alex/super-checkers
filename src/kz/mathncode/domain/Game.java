package kz.mathncode.domain;

import java.util.List;

import kz.mathncode.domain.enums.Color;
import kz.mathncode.domain.unit.Unit;
import kz.mathncode.exceptions.GameException;

/**
 * @author Aleksandr Nedokushev
 * @created 14.12.2022
 */
public class Game {

    private Board board;

    private Player activePlayer;

    public Game() throws GameException {

        Player whitePlayer = new Player(Color.WHITE);
        activePlayer = whitePlayer;
        List<Unit> whiteUnits = whitePlayer.initUnits();

        Player blackPlayer = new Player(Color.BLACK);
        List<Unit> blackUnits = blackPlayer.initUnits();

        this.board = new Board();
        board.getUnits().addAll(whiteUnits);
        board.getUnits().addAll(blackUnits);
    }

    public Board getBoard() {

        return board;
    }

    public void move(Coordinates startCoordinate, Coordinates finishCoordinate)
            throws GameException {

        Unit unit = board.getUnitByCoordinates(startCoordinate);
        if (unit == null || unit.getColor() != activePlayer.getColor()) {
            throw new GameException("На стартовой позиции нет юнита игрока, выполняющего ход");
        }

        // todo а вообще корректен ли ход с точки зрения механики юнита
        unit.isCorrectMove(startCoordinate, finishCoordinate);

        if (board.getUnitByCoordinates(finishCoordinate) != null) {
            throw new GameException("На конечной позиции уже есть юнит");
        }

        unit.setCoordinate(finishCoordinate);
    }
}
