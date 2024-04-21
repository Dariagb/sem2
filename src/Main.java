import java.net.SocketOption;
import java.util.*;

public class Main {
    private static final char dot_human = 'x';
    private static final char dot_ai = '0';
    private static final char dot_empty = '*';
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();
    private static char[][] field;
    private static int fieldSizeX;
    private static int fieldSizeY;

    public static void main(String[] args) {
        while (true) {
            initialize();
            printFielt();
            while (true) {
                humanTurn();
                printFielt();
                if (checkGameState(dot_human, "Вы победили!"))
                    break;
                aiTurn();
                printFielt();
                if (checkGameState(dot_ai, "Победил компьютер!"))
                    break;
            }
            System.out.println("Желаете сыграть еще раз? y-да");
            if (!scanner.next().equalsIgnoreCase("Y"))
                break;
        }
    }

    /*инициализация игрового поля*/
    static void initialize() {
        fieldSizeY = 3;
        fieldSizeX = 3;
        field = new char[fieldSizeY][fieldSizeX];
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                field[y][x] = dot_empty;
            }
        }
    }

    /*Печать текущего игрового поля*/
    private static void printFielt() {
        System.out.print("+");
        for (int i = 0; i < fieldSizeX; i++) {
            System.out.print("-" + (i + 1));
        }
        System.out.println("-");
        for (int y = 0; y < fieldSizeY; y++) {
            System.out.print(y + 1 + "|");
            for (int x = 0; x < fieldSizeX; x++) {
                System.out.print(field[y][x] + "|");
            }
            System.out.println();
        }
        for (int i = 0; i < fieldSizeX * 2 + 2; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    /*Ход игрока человека*/
    static void humanTurn() {
        int x;
        int y;
        do {
            System.out.print("Введите координаты кода X и Y\n через пробел от 1 до 3");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        }
        while (!isCellValid(x, y) || !isCellEmpty(x, y));
        field[x][y] = dot_human;

    }

    /*ход компьютера*/
    static void aiTurn() {
        int x;
        int y;
        do {
            x = random.nextInt(fieldSizeX);
            y = random.nextInt(fieldSizeY);
        }
        while (!isCellEmpty(x, y));
        field[x][y] = dot_ai;
    }

    /*Проверка является ячейка пустой*/
    static boolean isCellEmpty(int x, int y) {
        return field[y][x] == dot_empty;

    }

    /*Проверка доступности ячейки игрового поля*/
    static boolean isCellValid(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    /**
     * проверка состояния игры
     */
    static boolean checkGameState(char dot, String s) {
        if (checkWin(dot)) {
            System.out.println(s);
            return true;
        }
        if (checkDraw()) {
            System.out.println("Ничья!");
            return true;
        }
        return false;//игра продолжается
    }

    /*проверка на ничью*/
    static boolean checkDraw() {
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (isCellEmpty(x, y))
                    return false;
            }
        }
        return true;
    }

    /**
     * проверка игрока
     *
     * @param dot фишка игрока
     * @return признак победы
     */
    static boolean checkWin(char dot) {
        //Проверка по трем вертикалям
        if (field[0][0] == dot && field[0][1] == dot && field[0][2] == dot) return true;
        if (field[1][0] == dot && field[1][1] == dot && field[1][2] == dot) return true;
        if (field[2][0] == dot && field[2][1] == dot && field[2][2] == dot) return true;
//проверка по трем горизонталям
        if (field[0][0] == dot && field[1][0] == dot && field[2][0] == dot) return true;
        if (field[0][1] == dot && field[1][1] == dot && field[2][1] == dot) return true;
        if (field[0][2] == dot && field[1][2] == dot && field[2][2] == dot) return true;
//проверка по диагонали
        if (field[0][0] == dot && field[1][1] == dot && field[2][2] == dot) return true;
        if (field[0][2] == dot && field[1][1] == dot && field[2][0] == dot) return true;
        return false;
    }
}
