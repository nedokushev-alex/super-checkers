package kz.mathncode.domain;

import java.util.List;

import kz.mathncode.domain.enums.Color;
import kz.mathncode.domain.unit.Unit;

/**
 * @author Aleksandr Nedokushev
 * @created 14.12.2022
 */
public class Game {

    private Board board;

    public Game() {

        Player whitePlayer = new Player(Color.WHITE);
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

    public void move(Coordinates startCoordinate, Coordinates finishCoordinate) {

        // todo получить юнита, который находится на startCoordinate
    }
}
