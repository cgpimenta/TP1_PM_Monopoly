import Game.*;
import IO.*;

/**
 * Created by cgpimenta on 09/04/17.
 */
public class Monopoly {

    public static void main(String[] args) {

        Game game = new Game();
        game.makeBoard(System.getProperty("user.dir") + "/src/tabuleiro.txt");
        game.play(System.getProperty("user.dir") + "/src/jogadas.txt");

        OutputHandler output = new OutputHandler();
    }

}
