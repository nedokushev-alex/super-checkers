package kz.mathncode.domain.enums;

import kz.mathncode.exceptions.GameException;

/**
 * @author Aleksandr Nedokushev
 * @created 14.12.2022
 */
public enum LetterCoordinate {

    A(1),
    B(2),
    C(3),
    D(4),
    E(5),
    F(6),
    G(7),
    H(8);

    private int columnNumber;

    LetterCoordinate(int number) {

        this.columnNumber = number;
    }

    public int getColumnNumber() {

        return columnNumber;
    }

    public static LetterCoordinate getByColumnNumber(int column) throws GameException {

        for (LetterCoordinate value : values()) {
            if (value.columnNumber == column) {
                return value;
            }
        }
        throw new GameException("Для данного номера колонки нет соответствующего значения");
    }
}
