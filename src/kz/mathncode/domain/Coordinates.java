package kz.mathncode.domain;

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
}
