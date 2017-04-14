package Game;

import java.util.ArrayList;
import java.util.Scanner;
import IO.*;

/**
 * Created by cgpimenta on 09/04/17.
 */
public class Game {

    private Board board;
    private ArrayList<Player> players;

    /* Decidir como o jogo vai ser inicializado (depende da entrada) */
    public Game() {
        this.board = new Board();
        this.players = new ArrayList<Player>();
    }

//    public void inputHandler(String fileName) {
//        String line = null;
//        double purchaseValue = 0.0;
//        double rentTax = 0.0;
//        int propertyType = 0;
//        try {
//            FileReader fr = new FileReader(fileName);
//
//            BufferedReader br = new BufferedReader(fr);
//
//            line = br.readLine(); // number of positions on the board
//
//            while ((line = br.readLine()) != null){
//                System.out.println(line); // retarded debugging
//                String [] splitLine = line.split(";");
//
//                // Start Position
//                if (splitLine[2].equals("1")) {
//                    // To do
//                }
//                // Skip Turn
//                else if (splitLine[2].equals("2")) {
//                    // To do
//                }
//                // Property
//                else if (splitLine[2].equals("3")) {
//                    purchaseValue = Double.parseDouble(splitLine[4]);
//                    rentTax = Double.parseDouble(splitLine[5]);
//                    propertyType = Integer.parseInt(splitLine[3]);
//                    // Nao tenho certeza se pode fazer isso
//                    Property property = new Property(purchaseValue, rentTax, propertyType);
//                    this.board.addPosition(property);
//                }
//
//                this.board.addPosition();
//                br.close();
//            }
//        }
//        catch(Exception e) {
//            e.printStackTrace();
//        }
//    }
    public void makeBoard(String fileName) {
        InputHandler inputFile = new InputHandler(fileName);
        String line;
        double purchaseValue;
        double rentTax;
        int propertyType;
        int index;

        // Get first line of "tabuleiro.txt"
        line = inputFile.getLine();
        // System.out.println(line); // retarded debugging - works

        int numPositions = Integer.parseInt(line);
        for (int i = 0; i < numPositions; i++) {
            line = inputFile.getLine();
            // System.out.println(line); // retarded debugging - works

            String[] splitLine = line.split(";");
            // Start Position
            if (splitLine[2].equals("1")) {
                index = Integer.parseInt(splitLine[1]) - 1;
                Start start = new Start();

                this.board.addPosition(start, index);
            }
            // Skip Turn
            else if (splitLine[2].equals("2")) {
                index = Integer.parseInt(splitLine[1]) - 1;
                Skip skip = new Skip();

                this.board.addPosition(skip, index);
            }
            // Property
            else if (splitLine[2].equals("3")) {
                index = Integer.parseInt(splitLine[1]) - 1;
                purchaseValue = Double.parseDouble(splitLine[4]);
                rentTax = Double.parseDouble(splitLine[5]);
                propertyType = Integer.parseInt(splitLine[3]);
                Property property = new Property(purchaseValue, rentTax, propertyType);

                this.board.addPosition(property, index);
            }
        }

        /**/
        int posIndex = 1;
        for (BoardPosition pos : this.board.getBoard()) {
            int type = pos.getPositionType();
            System.out.print("Position " + (posIndex++) + " - Type: " + type);
            if (type == 3) {
               double value = ((Property) pos).getPurchaseValue();
               double rent = ((Property) pos).getRentValue();
                System.out.println(" | Value = " + value + " | Rent = " + rent);
            } else System.out.println();
        }
        /**/

    }

    private void payBonus(Player player) {
        player.receiveBonus(500.0);
    }

    public void play(String fileName) {
        /* É aqui que a mágica acontece */
        /**/double numRounds = 0;

        InputHandler inputFile = new InputHandler(fileName);

        String line = inputFile.getLine();
        String[] splitLine = line.split("%");

        int numMoves = Integer.parseInt(splitLine[0]);
        int numPlayers = Integer.parseInt(splitLine[1]);
        int initialBalance = Integer.parseInt(splitLine[2]);

//        System.out.println("numMoves = " + numMoves);
//        System.out.println("numPlayers = " + numPlayers);
//        System.out.println("initialBalance = " + initialBalance);


//        Scanner sc = new Scanner(System.in);
//        System.out.print("Número de jogadas: ");
//        int numMoves = sc.nextInt();
//        System.out.print("Número de jogadores: ");
//        int numPlayers = sc.nextInt();
//        System.out.print("Saldo inicial: ");
//        double initialBalance = sc.nextDouble();
//        System.out.println(numMoves + " " + numPlayers + " " + initialBalance);

        // Inicializa jogadores:
        for (int i = 1; i <= numPlayers; i++) {
            this.players.add(new Player(i, initialBalance));
        }


        for (int i = 0; i < numMoves; i++) {
            numRounds += 1.0/numPlayers;

            line = inputFile.getLine();
//            System.out.println(line);

            if (line.equals("DUMP")) break;

            splitLine = line.split(";");

            int lineId = Integer.parseInt(splitLine[0]);
            int playerId = Integer.parseInt(splitLine[1]) - 1;
            int diceValue = Integer.parseInt(splitLine[2]);

            Player player = this.players.get(playerId);

            // Verifica se o jogador atual ainda está no jogo:
            if (!player.isActive()) continue;

            // Mover jogador:
            int boardSize = this.board.getBoardSize();
            int currentPosition = player.getCurrentPosition();
//            System.out.println("Posição atual do jogador: " + Integer.toString(currentPosition+1));
            int newPosition = (currentPosition + diceValue +1 > boardSize) ? (currentPosition + diceValue ) % boardSize : (currentPosition + diceValue);
//            System.out.println("Nova posição: " + Integer.toString(newPosition+1));
            player.setCurrentPosition(newPosition);

            // Receber bônus quando passar pela posição inicial:
            /* COMO IDENTIFICAR QUE JOGADOR PASSOU PELO INÍCIO? */
            /* APROVEITAR E CHAMAR ADDLAPS() */
            for (int j = 0; j < (currentPosition + diceValue) / boardSize; j++) {
                this.payBonus(player);
                player.getStats().addLap();
            }

            // Realiza ação de acordo com o tipo da posição:
            BoardPosition position = this.board.getBoard().get(newPosition);
            switch (position.getPositionType()) {
                case 1:
                    // Nothing to be done
                    break;
                case 2:
                    // Skip player's turn
                    player.getStats().addSkip();
                    break;
                case 3:
                    int ownerId = ((Property) position).getOwnerId();
                    double playerBalance = player.getBalance();

                    if (ownerId != playerId && ownerId != 0) { // Property belongs to another player
                        // If player has enough balance, pay rent; otherwise, leave game:
                        double rent = ((Property) position).getRentValue();

                        if (playerBalance >= rent) {
                            Player owner = this.players.get(ownerId - 1);
                            player.payRent(owner, rent);
                        } else {
                            player.goBankrupt();
                        }
                    } else if (ownerId == 0) {
                        double propertyValue = ((Property) position).getPurchaseValue();

                        if (playerBalance >= propertyValue) {
                            player.buyProperty((Property) position);
                        }
                    } else {}
            }


        }

        System.out.println();
        System.out.println("---------------");
        System.out.println();
        System.out.println("numRounds: " + (int) numRounds);
        for (int i = 0; i < numPlayers; i++) {
            Player currPlayer = this.players.get(i);
            int laps = currPlayer.getStats().getNumLaps();
            double balance = currPlayer.getBalance();
            double rentReceived = currPlayer.getStats().getRentIncome();
            double rentPaid = currPlayer.getStats().getRentExpenses();
            double buy = currPlayer.getStats().getPropertyExpenses();
            double skips = currPlayer.getStats().getNumSkips();

            int index = i+1;
            System.out.println("Player " + index + ": " + laps + " laps | Balance = " + balance + " | Rent received = " + rentReceived + " | Rent paid = " + rentPaid + " | Properties = " + buy + " | Skips = " + skips);
        }


    }
}
