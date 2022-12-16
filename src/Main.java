import kz.mathncode.domain.Game;

/**
 * @author Aleksandr Nedokushev
 * @created ${DATE}
 */
public class Main {

    public static void main(String[] args) {

        Game game = new Game();
        String boardView = game.getBoard().show();
    }
}
