
package game;

public abstract class Player {
    protected int score;
    protected final char symbol;
    protected final String name;
    
    public Player(String name, char symbol){
        this.name=name;
        this.symbol=symbol;
        score = 0;
    }
    
    public char GetSymbol() {
        return symbol;
    }

    
    public String GetName() {
        return name;
    }

    
    public int GetScore() {
        return score;
    }

    
    public void winner() {
        score++;
    }

    
    public void endgame(){}

    
    public abstract Turn turn(Game game);
   
    
}
