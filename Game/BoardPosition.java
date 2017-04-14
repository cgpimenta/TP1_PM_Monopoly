package Game;

/**
 * Created by cgpimenta on 09/04/17.
 */
public abstract class BoardPosition {

    private BoardPositionType positionType;

    private enum BoardPositionType {
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

    public int getPositionType() {
        switch (this.positionType) {
            case START:
                return 1;
            case SKIP:
                return 2;
            case PROPERTY:
                return 3;
            default:
                return 0;

        }
    }

}
