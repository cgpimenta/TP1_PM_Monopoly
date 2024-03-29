import Game.*;

public class Monopoly {

    public static void main(String[] args) {

        Game game = new Game();

        // Create board according to the input file:
        game.makeBoard(System.getProperty("user.dir") + "/src/tabuleiro-3.txt");

        // Play game according to input file:
        game.play(System.getProperty("user.dir") + "/src/jogadas-3.txt");

        // Save game statistics to file:
        game.printStats(System.getProperty("user.dir") + "/src/estatisticas-3.txt");
    }

}
