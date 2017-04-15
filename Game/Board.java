package Game;

import java.util.ArrayList;

public class Board {

    private ArrayList<BoardPosition> board;
    private int boardSize;

    /**
     * Class constructor.
     * Initializes an empty board.
     */
    public Board() {
        this.board = new ArrayList<BoardPosition>();
        this.boardSize = 0;
    }

    public void addPosition(BoardPosition p, int index) {
        // Add position to board:
        this.board.add(index, p);
        this.boardSize++;
    }

    public ArrayList<BoardPosition> getBoard() {
        return this.board;
    }

    public int getBoardSize() {
        return this.boardSize;
    }
}
