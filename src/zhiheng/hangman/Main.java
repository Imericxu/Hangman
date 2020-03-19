package zhiheng.hangman;

public class Main {
    
    public static void main(String[] args) {
        BaseGame game = new ConsoleGame(8);
        game.play();
    }
}
