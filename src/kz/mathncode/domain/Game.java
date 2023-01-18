package kz.mathncode.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kz.mathncode.domain.enums.ActionType;
import kz.mathncode.domain.enums.Color;
import kz.mathncode.domain.enums.ResultAction;
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

    private Coordinates checkPoint;

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

    public ResultAction performAction(Coordinates startCoordinate, Coordinates finishCoordinate)
            throws GameException {

        validateCheckPoint(startCoordinate);

        // достаём юнита активного игрока на стартовой позиции
        Unit activeUnit = getActiveUnit(startCoordinate);
        // проверка на пустоту конечное поля
        checkEmptyFinishField(finishCoordinate);

        Action action = activeUnit.determineAction(startCoordinate, finishCoordinate, board);

        if (action.getActionType() == ActionType.MOVE) {
            return move(activeUnit, finishCoordinate);
        } else {
            return chop(activeUnit, action.getVictim(), finishCoordinate);
        }
    }

    public ResultAction move(Unit activeUnit, Coordinates finishCoordinate) throws GameException {

        // проверяем, что у активного игрока нет доступных ходов-рубок для ВСЕХ его юнитов
        checkCanActivePlayerOnlyMove();

        // непосредственно перемещение - изменение координаты юнита на finishCoordinate
        board.relocateUnit(activeUnit, finishCoordinate);

        if (isBlockedAllOpponentUnits()) {
            return ResultAction.WIN;
        } else {
            // выполняем переход хода - изменяется активный игрок (активным становится соперник)
            changeActivePlayer();
            return ResultAction.CONTINUE;
        }
    }

    public ResultAction chop(Unit activeUnit, Unit victim, Coordinates finishCoordinate)
            throws GameException {

        // перемещение на конечную позицию (поле для приземления после рубки)
        activeUnit = board.relocateUnit(activeUnit, finishCoordinate);

        // удаляем жертву из списка юнитов на доске
        board.getUnits().remove(victim);
        if (isDestroyedAllOpponentUnits() || isBlockedAllOpponentUnits()) {
            return ResultAction.WIN;
        }

        // проверка и переход хода сопернику
        if (activeUnit.hasPossibleVictim(board)) {
            // переход хода не выполняем
            checkPoint = finishCoordinate;
        } else {
            // выполняем переход хода другому игроку
            checkPoint = null;
            changeActivePlayer();
        }

        return ResultAction.CONTINUE;
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

    private void changeActivePlayer() {

        Color opponentColor = activePlayer.getColor() == Color.WHITE ? Color.BLACK : Color.WHITE;
        activePlayer = players.get(opponentColor);
    }

    private void checkCanActivePlayerOnlyMove() throws GameException {

        List<Unit> activePlayerUnits = new ArrayList<>();
        for (Unit unit : board.getUnits()) {
            if (unit.getColor() == activePlayer.getColor()) {
                activePlayerUnits.add(unit);
            }
        }
        for (Unit activePlayerUnit : activePlayerUnits) {
            if (activePlayerUnit.hasPossibleVictim(board)) {
                throw new GameException(
                        "У активного игрока есть возможный ход-рубка, простое перемещение запрещено!");

            }
        }
    }

    private void validateCheckPoint(Coordinates startCoordinate) throws GameException {

        if (checkPoint != null && !checkPoint.equals(startCoordinate)) {
            throw new GameException(
                    "Игрок должен продолжать ход-рубку тем же самым юнитом, которым он уже выполнил ход-рубку!");
        }
    }

    private boolean isDestroyedAllOpponentUnits() {

        for (Unit unit : board.getUnits()) {
            if (unit.getColor() != activePlayer.getColor()) {
                return false;
            }
        }
        return true;
    }

    private boolean isBlockedAllOpponentUnits() {

        for (Unit unit : board.getUnits()) {
            if (unit.getColor() != activePlayer.getColor()) {
                // может ли юнит соперника выполнить перемещение или срубить
                if (unit.hasPossibleMove(board) || unit.hasPossibleVictim(board)) {
                    return false;
                }
            }
        }
        return true;
    }
}
