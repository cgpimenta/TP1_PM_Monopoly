package Game;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import IO.*;

public class Game {

    private Board board;
    private ArrayList<Player> players;

    /**
     * Class constructor
     * Instantiates empty board and empty list of players.
     */
    public Game() {
        this.board = new Board();
        this.players = new ArrayList<Player>();
    }

    /**
     * Creates the board using the input file.
     * @param fileName Name of the input file
     */
    public void makeBoard(String fileName) {
        // Read input file:
        InputHandler inputFile = new InputHandler(fileName);
        
        String line;
        double purchaseValue;
        double rentTax;
        int propertyType;
        int index;

        // Get first line of "tabuleiro.txt":
        line = inputFile.getLine();

        // First line defines the size of the board:
        int numPositions = Integer.parseInt(line);

        for (int i = 0; i < numPositions; i++) {
            line = inputFile.getLine();

            String[] splitLine = line.split(";");

            // Start Position:
            if (splitLine[2].equals("1")) {
                index = Integer.parseInt(splitLine[1]) - 1;
                Start start = new Start();

                this.board.addPosition(start, index);
            }
            // Skip Turn:
            else if (splitLine[2].equals("2")) {
                index = Integer.parseInt(splitLine[1]) - 1;
                Skip skip = new Skip();

                this.board.addPosition(skip, index);
            }
            // Property:
            else if (splitLine[2].equals("3")) {
                index = Integer.parseInt(splitLine[1]) - 1;
                purchaseValue = Double.parseDouble(splitLine[4]);
                rentTax = Double.parseDouble(splitLine[5]);
                propertyType = Integer.parseInt(splitLine[3]);
                Property property = new Property(purchaseValue, rentTax, propertyType);

                this.board.addPosition(property, index);
            }
        }

    }

    /**
     * Pays $500.00 bonus to player.
     * @param player Player that will receive the bonus.
     */
    private void payBonus(Player player) {
        player.receiveBonus(500.0);
    }

    /**
     * Plays a game of Monopoly according to input file.
     * @param fileName Name of the input file.
     */
    public void play(String fileName) {
        double numRounds = 0;

        // Read input file:
        InputHandler inputFile = new InputHandler(fileName);

        String line = inputFile.getLine();
        String[] splitLine = line.split("%");

        // First line defines the # of moves, # of players and their initial balance:
        int numMoves = Integer.parseInt(splitLine[0]);
        int numPlayers = Integer.parseInt(splitLine[1]);
        double initialBalance = Double.parseDouble(splitLine[2]);


        // Initialize players:
        for (int i = 1; i <= numPlayers; i++) {
            this.players.add(new Player(i, initialBalance));
        }

        // Play each move in the file:
        for (int i = 0; i < numMoves; i++) {
            // Read next line
            line = inputFile.getLine();

            if (line.equals("DUMP")) break;

            splitLine = line.split(";");

            int lineId = Integer.parseInt(splitLine[0]);
            int playerId = Integer.parseInt(splitLine[1]);
            int diceValue = Integer.parseInt(splitLine[2]);

            Player player = this.players.get(playerId-1);

            // Checks if current player is still in the game:
            if (!player.isActive()) continue;

            // Increment number of rounds:
            player.getStats().addRound();

            int boardSize = this.board.getBoardSize();

            // Move player:
            int currentPosition = player.getCurrentPosition();
            int newPosition = (currentPosition + diceValue + 1 > boardSize) ? (currentPosition + diceValue ) % boardSize : (currentPosition + diceValue);
            player.setCurrentPosition(newPosition);

            // Identify that player has completed one (or more) laps:
            for (int j = 0; j < (currentPosition + diceValue) / boardSize; j++) {
                // Receive bonus:
                this.payBonus(player);
                // Count the number of laps
                player.getStats().addLap();
            }

            // Check the type of the position and act accordingly:
            BoardPosition position = this.board.getBoard().get(newPosition);
            switch (position.getPositionType()) {
                case START:
                    // Nothing to be done
                    break;
                case SKIP:
                    // Skip player's turn:
                    player.getStats().addSkip();
                    break;
                case PROPERTY:
                    int ownerId = ((Property) position).getOwnerId();
                    double playerBalance = player.getBalance();

                    // Property belongs to another player:
                    if (ownerId != playerId && ownerId != 0) {
                        double rent = ((Property) position).getRentValue();

                        // If player has enough balance, pay rent; otherwise, leave game:
                        if (playerBalance >= rent) {
                            Player owner = this.players.get(ownerId - 1);
                            player.payRent(owner, rent);
                        } else {
                            player.goBankrupt(rent);
                        }
                    } 
                    // Property belongs to the bank:
                    else if (ownerId == 0) {
                        double propertyValue = ((Property) position).getPurchaseValue();

                        if (playerBalance >= propertyValue) {
                            player.buyProperty((Property) position);
                        }
                    } 
                    // Player owns the property:
                    else { /* Nothing to be done */ }
            } /* END switch position type */

        } /* END for moves */

    }

    public void printStats() {
        DecimalFormat df = new DecimalFormat("##.##");
        int numPlayers = this.players.size();

        OutputHandler output = new OutputHandler(System.getProperty("user.dir") + "/src/estatisticas.txt");
        String line;

        // Number of rounds:
        int[] playersRounds = new int[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            playersRounds[i] = this.players.get(i).getStats().getNumRounds();
        }
        int numRounds = Arrays.stream(playersRounds).max().getAsInt();
        output.putLine("1:" + numRounds + "\n");

        // Number of laps:
        line = "2:";
        for (int i = 0; i < numPlayers; i++) {
            int numLaps = this.players.get(i).getStats().getNumLaps();
            line += (i+1) + "-" + numLaps;
            if (i != numPlayers - 1) line += ";";
        } output.putLine(line + "\n");

        // Final balance:
        line = "3:";
        for (int i = 0; i < numPlayers; i++) {
            double finalBalance = this.players.get(i).getBalance();
            line += (i+1) + "-" + df.format(finalBalance);
            if (i != numPlayers - 1) line += ";";
        } output.putLine(line + "\n");

        // Rent received:
        line = "4:";
        for (int i = 0; i < numPlayers; i++) {
            double rentIncome = this.players.get(i).getStats().getRentIncome();
            line += (i+1) + "-" + df.format(rentIncome);
            if (i != numPlayers - 1) line += ";";
        } output.putLine(line + "\n");

        // Rent payed:
        line = "5:";
        for (int i = 0; i < numPlayers; i++) {
            double rentPayed = this.players.get(i).getStats().getRentExpenses();
            line += (i+1) + "-" + df.format(rentPayed);
            if (i != numPlayers - 1) line += ";";
        } output.putLine(line + "\n");

        // Properties purchased:
        line = "6:";
        for (int i = 0; i < numPlayers; i++) {
            double purchaseExpense = this.players.get(i).getStats().getPropertyExpenses();
            line += (i+1) + "-" + df.format(purchaseExpense);
            if (i != numPlayers - 1) line += ";";
        } output.putLine(line + "\n");

        // Number of skips:
        line = "7:";
        for (int i = 0; i < numPlayers; i++) {
            Player currPlayer = this.players.get(i);
            line += (i+1) + "-" + currPlayer.getStats().getNumSkips();
            if (i != numPlayers - 1) line += ";";
        } output.putLine(line);

        output.closeFile();
    }
    
}
