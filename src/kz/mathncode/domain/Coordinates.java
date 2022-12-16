package kz.mathncode.domain;

import java.util.Objects;

import kz.mathncode.domain.enums.DigitalCoordinate;
import kz.mathncode.domain.enums.LetterCoordinate;

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

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Coordinates that = (Coordinates) o;
        return letter == that.letter && digital == that.digital;
    }

    @Override
    public int hashCode() {

        // todo объяснить
        return Objects.hash(letter, digital);
    }
}
