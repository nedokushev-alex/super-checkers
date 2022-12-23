package kz.mathncode.domain;

import java.util.Objects;

import kz.mathncode.domain.enums.DigitalCoordinate;
import kz.mathncode.domain.enums.LetterCoordinate;
import kz.mathncode.exceptions.GameException;

/**
 * @author Aleksandr Nedokushev
 * @created 14.12.2022
 */
public class Coordinates {

    private LetterCoordinate letter;

    private DigitalCoordinate digital;

    public Coordinates(LetterCoordinate letter, DigitalCoordinate digital) {

        this.letter = letter;
        this.digital = digital;
    }

    public LetterCoordinate getLetter() {

        return letter;
    }

    public DigitalCoordinate getDigital() {

        return digital;
    }

    public static Coordinates convertFromHumanInput(String humanInput) throws GameException { // A3

        try {
            // char -> String -> LetterCoordinate(по названию)
            char letterChar = humanInput.charAt(0); //A как char
            String letterString = String.valueOf(letterChar); //A как String
            LetterCoordinate letter = LetterCoordinate.valueOf(letterString); //A как элемент перечисления LetterCoordinate.A

            char digitalChar = humanInput.charAt(1);
            // Первый вариант: char -> ascii-код -> -48
            DigitalCoordinate digital = DigitalCoordinate.getByLineNumber(digitalChar - 48);

            // Второй вариант: char -> String -> int -> DigitalCoordinate(по номеру линии)
            //         String digitalString = String.valueOf(digitalChar);
            //         int digitalInt = Integer.parseInt(digitalString);
            //         DigitalCoordinate digital = DigitalCoordinate.getByLineNumber(digitalInt);

            Coordinates coordinates = new Coordinates(letter, digital);
            return coordinates;
        } catch (Exception ex) {
            throw new GameException("Некорректные данные координат");
        }
    }

    @Override public boolean equals(Object o) {

        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Coordinates that = (Coordinates) o;
        return letter == that.letter && digital == that.digital;
    }

    @Override public int hashCode() {

        // todo объяснить
        return Objects.hash(letter, digital);
    }
}
