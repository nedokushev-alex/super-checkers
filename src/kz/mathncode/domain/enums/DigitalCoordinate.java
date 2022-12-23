package kz.mathncode.domain.enums;

import kz.mathncode.exceptions.GameException;

/**
 * @author Aleksandr Nedokushev
 * @created 14.12.2022
 */
public enum DigitalCoordinate {

    ONE(1), TWO(2), THREE(3), FOUR(4),
    FIVE(5), SIX(6), SEVEN(7), EIGHT(8);

    private int lineNumber;

    DigitalCoordinate(int number) {

        this.lineNumber = number;
    }

    public int getLineNumber() {

        return lineNumber;
    }

    public static DigitalCoordinate getByLineNumber(int line) throws GameException {

        for (DigitalCoordinate value : values()) {
            if (value.lineNumber == line) {
                return value;
            }
        }
        throw new GameException("Для данного номера линии нет соответствующего значения");
    }
}
