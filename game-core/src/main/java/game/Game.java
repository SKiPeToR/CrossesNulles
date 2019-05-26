package game;
public class Game {

    final private Player player1;
    final private Player player2;
    final private char[][] field = new char[3][3];
    final private GameStat statistic;

    public char[][] GetField() {
        return field;
    }

    public Game(Player P1, Player P2, GameStat stat) {
        player1 = P1;
        player2 = P2;
        statistic = stat;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = '_';
            }
        }
    }

    private boolean check() {
        for (int i = 0; i < 3; i++) {
            if (field[i][0] != '_' && field[i][0] == field[i][1] && field[i][0] == field[i][2]
                    || field[0][i] != '_' && field[0][i] == field[1][i] && field[0][i] == field[2][i]) {
                return true;
            }
        }
        return field[1][1] != '_' && field[0][0] == field[1][1] && field[0][0] == field[2][2]
                || field[1][1] != '_' && field[0][2] == field[1][1] && field[0][2] == field[2][0];
    }

    public void start() {
        int turns = 0;
        Player currentPlayer = player2;
        while (!check()) {
            if (currentPlayer != player1) {
                currentPlayer = player1;
            } else {
                currentPlayer = player2;
            }
            Turn turn = currentPlayer.turn(this);
            field[turn.X][turn.Y] = currentPlayer.GetSymbol();
            if (++turns == 9) {
                break;
            }
        }
        if (turns == 9 && !check()) {
            statistic.endgame(player1, player2, 0);
        } else if (currentPlayer == player1) {
            player1.winner();
            statistic.endgame(player1, player2, 1);
        } else if (currentPlayer == player2) {
            player2.winner();
            statistic.endgame(player1, player2, 2);
        }
        player1.endgame();
        player2.endgame();
    }
}
