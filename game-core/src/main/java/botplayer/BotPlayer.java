package botplayer;

import game.*;
import java.util.Random;

public class BotPlayer extends Player {

    Random generator = new Random(123);

    public BotPlayer(String _name, char _symbol) {
        super(_name, _symbol);
    }

    @Override
    public Turn turn(Game game) {
        int x,y;
        boolean correction = false;
        while (!correction) {
            x = Math.abs(generator.nextInt() % 3);
            y = Math.abs(generator.nextInt() % 3);
            
            if (game.GetField()[x][y]=='_') {
               return new Turn(x,y);
            }
         
        }
        return null;
    } 

   

}
