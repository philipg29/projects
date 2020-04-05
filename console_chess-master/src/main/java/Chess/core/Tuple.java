package chess.core;


/**
 * Used to store an int/int pair to map to tiles on the chessboard.
 */
public final class Tuple
{
    // x cordinate position
    private final int xPosition;
    // y cordinate position
    private final int yPosition;


    /**
     * Creates chess tuple
     *
     * @param xPosition - x cordinate position
     * @param yPosition - y cordinate position
     */
    public Tuple(final int xPosition, final int yPosition)
    {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }


    /**
     * Retrieves the x cordinate position
     *
     * @return xPosition
     */
    public int getXposition()
    {
        return xPosition;
    }


    /**
     * Retrieves the y cordinate position
     *
     * @return yPosition
     */
    public int getYposition()
    {
        return yPosition;
    }

}
