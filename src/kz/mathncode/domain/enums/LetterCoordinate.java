package kz.mathncode.domain.enums;

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

    public static LetterCoordinate getByColumnNumber(int column) {

        for (LetterCoordinate value : values()) {
            if (value.columnNumber == column) {
                return value;
            }
        }
        // todo доделать нормально!
        return null;
    }
}
