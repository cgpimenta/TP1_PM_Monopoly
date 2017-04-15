import Game.*;
import IO.*;

public class Monopoly {

    public static void main(String[] args) {

        Game game = new Game();

        // Create board according to the input file:
        game.makeBoard(System.getProperty("user.dir") + "/src/tabuleiro.txt");

        // Play game according to input file:
        game.play(System.getProperty("user.dir") + "/src/jogadas.txt");

        // Save game statistics to file:
        game.printStats(System.getProperty("user.dir") + "/src/estatisticas.txt");
    }

}
