package console;

import java.util.InputMismatchException;
import java.util.Scanner;
import game.*;
import statistic.*;
import botplayer.BotPlayer;

public class ConsolePlayer extends Player {


    private static Game game;
    private static Player player1;
    private static Player player2;
    private static final PlayerStat statistic = new Statistic();
    private static final Scanner console = new Scanner(System.in);

    public static void main(String[] args) {
        String menu = "Крестики-нолики (console_version)\n"
                + "1 - Новая игра\n"
                + "2 - Создать новых игроков\n"
                + "3 - Посмотреть результаты игр\n"
                + "0 - Выход\n>";
        boolean exit = false;
        while (!exit) {
            System.out.print(menu);
            switch (console.nextInt()) {
                case 1: {
                    newgame();
                    break;
                }
                case 2: {
                    newplayers();
                    break;
                }
                case 3: {
                    System.out.print("\n" + statistic.getStatistic() + "\n");
                    break;
                }
                case 0: {
                    exit = true;
                    statistic.safeFile();
                    System.out.print("Хорошего дня!\n");
                    break;
                }
                default: {
                    System.out.print("Неверный ввод. Попробуйте снова\n\n");
                    break;
                }
            }
        }
    }

    private static void newgame() {
        if (player1 == null || player2 == null) {
            System.out.print("Для начала необходимо создать игроков!\n\n");
            return;
        }
        game = new Game(player1, player2, (Statistic) statistic);
        game.start();
        game=null;
    }

    private static void showfield(Game game) {
        char[][] keys = new char[][]{
            {'7', '8', '9'},
            {'4', '5', '6'},
            {'1', '2', '3'},};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(game.GetField()[i][j] + " ");
            }
            System.out.print("   ");
            for (int j = 0; j < 3; j++) {
                System.out.print(keys[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void newplayers() {
        System.out.print("Введите имя первого игрока (или bot)> ");
        console.nextLine();
        String name = console.nextLine();
        if (name.toLowerCase().equals("bot")) {
            player1 = new BotPlayer("bot1", 'x');
        } else {
            player1 = new ConsolePlayer(name, 'x');
        }
        System.out.print("Введите имя первого игрока (или bot)> ");
        name = console.nextLine();
        if (name.toLowerCase().equals("bot")) {
            player2 = new BotPlayer("bot2", 'o');
        } else {
            player2 = new ConsolePlayer(name, 'o');
        }
    }

    @Override
    public char GetSymbol() {
        return symbol;
    }

    @Override
    public String GetName() {
        return name;
    }

    @Override
    public int GetScore() {
        return score;
    }

    @Override
    public void winner() {
        score++;
    }

    @Override
    public void endgame() {
       
        System.out.print(statistic.getLastGame());
        showfield(game);
    }

    protected int keyswitcher(Turn turn) {
        if (turn.X == 0 && turn.Y == 0) {
            return 7;
        } else if (turn.X == 0 && turn.Y == 1) {
            return 8;
        } else if (turn.X == 0 && turn.Y == 2) {
            return 9;
        } else if (turn.X == 1 && turn.Y == 0) {
            return 4;
        } else if (turn.X == 1 && turn.Y == 1) {
            return 5;
        } else if (turn.X == 1 && turn.Y == 2) {
            return 6;
        } else if (turn.X == 2 && turn.Y == 0) {
            return 1;
        } else if (turn.X == 2 && turn.Y == 1) {
            return 2;
        } else if (turn.X == 2 && turn.Y == 2) {
            return 3;
        } else {
            return 0;
        }
    }

    protected Turn keyswitcher(int numkey) {
        Turn turn;
        switch (numkey) {
            case 1: {
                turn = new Turn(2, 0);
                break;
            }
            case 2: {
                turn = new Turn(2, 1);
                break;
            }
            case 3: {
                turn = new Turn(2, 2);
                break;
            }
            case 4: {
                turn = new Turn(1, 0);
                break;
            }
            case 5: {
                turn = new Turn(1, 1);
                break;
            }
            case 6: {
                turn = new Turn(1, 2);
                break;
            }
            case 7: {
                turn = new Turn(0, 0);
                break;
            }
            case 8: {
                turn = new Turn(0, 1);
                break;
            }
            case 9: {
                turn = new Turn(0, 2);
                break;
            }
            default: {
                turn = null;
                break;
            }
        }
        return turn;
    }

    @Override
    public Turn turn(Game game) {
        int key;
        boolean correction = false;
        while (!correction) {
            showfield(game);
            System.out.print("Ход " + name + "> ");
            try {
                key = console.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Неверный ввод!\n");
                console.nextLine();
                continue;
            }
            if (key > 0 && key < 10) {
                if (game.GetField()[keyswitcher(key).X][keyswitcher(key).Y] == '_') {
                    return keyswitcher(key);
                }

            } else {
                System.out.print("Неверный ход!\n");
            }
        }
        return null;
    }

    public ConsolePlayer(String _name, char _symbol) {
        super(_name, _symbol);

    }

}
