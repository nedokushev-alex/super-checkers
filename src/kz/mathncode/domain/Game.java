package kz.mathncode.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private Map<Color, Player> players = new HashMap<>();

    public Game() throws GameException {

        Player whitePlayer = new Player(Color.WHITE);
        activePlayer = whitePlayer;
        List<Unit> whiteUnits = whitePlayer.initUnits();

        Player blackPlayer = new Player(Color.BLACK);
        List<Unit> blackUnits = blackPlayer.initUnits();

        this.board = new Board();
        board.getUnits().addAll(whiteUnits);
        board.getUnits().addAll(blackUnits);

        players.put(Color.WHITE, whitePlayer);
        players.put(Color.BLACK, blackPlayer);
    }

    public Board getBoard() {

        return board;
    }

    public Player getActivePlayer() {

        return activePlayer;
    }

    public void move(Coordinates startCoordinate, Coordinates finishCoordinate)
            throws GameException {

        Unit activeUnit = getActiveUnit(startCoordinate);

        if (!activeUnit.isCorrectMove(startCoordinate, finishCoordinate)) {
            throw new GameException("Некорректный ход для данного юнита");
        }

        checkEmptyFinishField(finishCoordinate);

        activeUnit.setCoordinate(finishCoordinate);

        Color opponentColor = activePlayer.getColor() == Color.WHITE ? Color.BLACK : Color.WHITE;
        activePlayer = players.get(opponentColor);
    }

    public void chop(Coordinates startCoordinate, Coordinates finishCoordinate)
            throws GameException {

        Unit activeUnit = getActiveUnit(startCoordinate);

        // todo соответствует ли поедание механике юнита
        activeUnit.isCorrectChop(startCoordinate, finishCoordinate, board);

        checkEmptyFinishField(finishCoordinate);

        activeUnit.setCoordinate(finishCoordinate);

        // todo удалить с доски юнита, которого съели

        // todo проверить есть ли ещё возможность рубить у активного юнита (если есть такая возможность,
        //  то не передаём ход другому игроку, иначе - передаём ход другому игроку)
    }

    private Unit getActiveUnit(Coordinates startCoordinate) throws GameException {

        Unit unit = board.getUnitByCoordinates(startCoordinate);
        if (unit == null || unit.getColor() != activePlayer.getColor()) {
            throw new GameException("На стартовой позиции нет юнита игрока, выполняющего ход");
        }
        return unit;
    }

    private void checkEmptyFinishField(Coordinates finishCoordinate) throws GameException {

        if (board.getUnitByCoordinates(finishCoordinate) != null) {
            throw new GameException("На конечной позиции уже есть юнит");
        }
    }
}
