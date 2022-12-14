package kz.mathncode.domain;

import kz.mathncode.domain.enums.Color;

/**
 * @author Aleksandr Nedokushev
 * @created 14.12.2022
 */
public class Game {

    private Board board;

    public Game() {

        Player whitePlayer = new Player(Color.WHITE);
        Player blackPlayer = new Player(Color.BLACK);

        this.board = new Board();
    }
}
