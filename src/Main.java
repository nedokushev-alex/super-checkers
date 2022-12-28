import java.util.Scanner;

import kz.mathncode.domain.Coordinates;
import kz.mathncode.domain.Game;
import kz.mathncode.domain.enums.Color;
import kz.mathncode.exceptions.GameException;

/**
 * @author Aleksandr Nedokushev
 * @created ${DATE}
 */
public class Main {

    public static void main(String[] args) throws GameException {

        Game game = new Game();
        Scanner in = new Scanner(System.in);

        do {
            System.out.println(game.getBoard().show());
            System.out.println("Ходят " + game.getActivePlayer().getName());

            try {
                String[] humanCoordinates = receiveHumanCoordinates(in); // { A3, B4 }

                Coordinates startCoordinates = Coordinates.convertFromHumanInput(humanCoordinates[0]);
                Coordinates finishCoordinates = Coordinates.convertFromHumanInput(humanCoordinates[1]);

                game.move(startCoordinates, finishCoordinates);

            } catch (GameException ex) {
                System.out.println("ОШИБКА: " + ex.getMessage());
            }
        } while (true);
    }

    private static String[] receiveHumanCoordinates(Scanner in) throws GameException {

        String inputStep = in.nextLine(); // A3 - B4
        String inputStepWithoutSpaces = inputStep.replace(" ", ""); // A3-B4
        if (inputStepWithoutSpaces.length() != 5) {
            throw new GameException("Введены некорректные данные");
        }

        String[] humanCoordinates = inputStepWithoutSpaces.split("-"); // { A3, B4 }
        if (humanCoordinates.length != 2 || humanCoordinates[0].length() !=2
                || humanCoordinates[1].length() != 2) {
            throw new GameException("Введены некорректные данные");
        }
        return humanCoordinates;
    }
}
