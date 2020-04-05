package chess;


public final class Move
{

    private final int xPosition;
    private final int yPosition;
    private final boolean firstMoveOnly;
    private final boolean onTakeOnly;


    /**
     * Initializes player move
     *
     * @param xPosition - the x position
     * @param yPosition - the y position
     * @param firstMoveOnly - is it first move
     * @param onTakeOnly - is it on take
     */
    public Move(int xPosition, int yPosition, boolean firstMoveOnly, boolean onTakeOnly)
    {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.firstMoveOnly = firstMoveOnly;
        this.onTakeOnly = onTakeOnly;
    }


    public int getX()
    {
        return xPosition;
    }


    public int getY()
    {
        return yPosition;
    }


    public boolean isFirstMoveOnly()
    {
        return firstMoveOnly;
    }


    public boolean isOnTakeOnly()
    {
        return onTakeOnly;
    }

}
