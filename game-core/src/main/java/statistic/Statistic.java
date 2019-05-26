package statistic;

import game.GameStat;
import java.io.*;
import java.util.LinkedList;
import game.Player;

class GameResult {

    public final String player1;
    public final String player2;
    public final int winner;

    GameResult(Player player1, Player player2, int winner) {
        this.player1 = player1.GetName();
        this.player2 = player2.GetName();
        this.winner = winner;
    }

}

public class Statistic implements GameStat, PlayerStat {

    LinkedList<GameResult> results = new LinkedList<>();
    int gamescount = 0;


    @Override
    public void endgame(Player player1, Player player2, int winner) {
        results.add(new GameResult(player1, player2, winner));
        gamescount++;
        if (gamescount >= 30) {
            safeFile();
        }
    }

    @Override
    public String getStatistic() {
        String statistic = "";
        for (GameResult gr : results) {
            if (gr.winner == 0) {
                statistic += gr.player1 + "   tie   " + gr.player2 + "\n";
            } else if (gr.winner == 1) {
                statistic += gr.player1 + " <<win   " + gr.player2 + "\n";
            } else if (gr.winner == 2) {
                statistic += gr.player1 + "   win>> " + gr.player2 + "\n";
            }
        }
        return statistic;
    }

    @Override
    public void safeFile() {
        if (gamescount == 0) {
            return;
        }
        try {
            try (FileWriter writer = new FileWriter(new File("stat.txt"),true)) {
                writer.write(getStatistic().replace("\n", "\r\n"));
                writer.write("================================================\r\n");
                writer.close();
                results = new LinkedList<>();
                gamescount=0;
            }
        } catch (IOException e) {

        }
    }

    @Override
    public String getLastGame() {
        String lastgame = results.getLast().player1;
        switch (results.getLast().winner) {
            case 0: {
                lastgame += "   tie   ";
                break;
            }
            case 1: {
                lastgame += " <<win   ";
                break;
            }
            case 2: {
                lastgame += "   win>> ";
                break;
            }
        }
        lastgame += results.getLast().player2 + "\n";
//  
        return lastgame;
    }
}
