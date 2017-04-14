package Game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

/**
 * Created by cgpimenta on 09/04/17.
 */
public class Board {

    private ArrayList<BoardPosition> board;
    private int boardSize;
//    private Collection<Property> bankProperties;

    public Board() {
        this.board = new ArrayList<BoardPosition>();
//        this.bankProperties = new ArrayList<Property>();
        this.boardSize = 0;
    }

    public void addPosition(BoardPosition p, int index) {
        // Add position to board:
        this.board.add(index, p);
        this.boardSize++;


//        this.board.add
//        // If position is a property, add it to the bank's properties list:
//        if (p.getPositionType() == 3) {
//            bankProperties.add((Property) p);
//        }
    }

    public ArrayList<BoardPosition> getBoard() {
        return this.board;
    }

    public int getBoardSize() {
        return this.boardSize;
    }
}
