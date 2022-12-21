import java.util.Scanner;

import kz.mathncode.domain.Coordinates;
import kz.mathncode.domain.Game;

/**
 * @author Aleksandr Nedokushev
 * @created ${DATE}
 */
public class Main {

    public static void main(String[] args) {

        Game game = new Game();
        String boardView = game.getBoard().show();
        System.out.println(boardView);

        Scanner in = new Scanner(System.in);
        String inputStep = in.nextLine(); // A3 - B4
        String inputStepWithoutSpaces = inputStep.replace(" ", ""); // A3-B4
        String[] humanCoordinates = inputStepWithoutSpaces.split("-"); // { A3, B4 }

        // A3 -> new Coordinates(LetterCoordinate.A, DigitalCoordinate.THREE)
        // A3: A->LetterCoordinate.A , 3 -> DigitalCoordinate.THREE
        Coordinates startCoordinates = Coordinates.convertFromHumanInput(humanCoordinates[0]);
        Coordinates finishCoordinates = Coordinates.convertFromHumanInput(humanCoordinates[1]);
        game.move(startCoordinates, finishCoordinates);

    }
}
