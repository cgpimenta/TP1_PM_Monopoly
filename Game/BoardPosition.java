package Game;

public abstract class BoardPosition {

    private BoardPositionType positionType;

    public enum BoardPositionType {
        START, SKIP, PROPERTY
    }

    public BoardPosition(int positionType) {
        switch (positionType) {
            case 1:
                this.positionType = BoardPositionType.START;
                break;
            case 2:
                this.positionType = BoardPositionType.SKIP;
                break;
            case 3:
                this.positionType = BoardPositionType.PROPERTY;
                break;
            default:
                this.positionType = null;
        }
    }

    public BoardPosition.BoardPositionType getPositionType() {
        return this.positionType;
    }
    
}
