package chess.core;


public final class Tile
{

    // represents the chess piece
    private ChessPiece piece;
    // represents the colour of the tile
    private final TileColor color;

    public enum TileColor
    {
        White, Black
    }


    /**
     * @param color - set tile colour
     */
    public Tile(final TileColor color)
    {
        this.color = color;
    }


    /**
     * @param color - colour of the chess piece
     * @param piece - the piece that corresponds to a tile
     */
    public Tile(final TileColor color,final  ChessPiece piece)
    {
        this.color = color;
        this.piece = piece;
    }


    /**
     * @param piece - sets piece on tile
     */
    public void setPiece(final ChessPiece piece)
    {
        this.piece = piece;
    }


    /**
     * Returns the chess piece
     *
     * @return piece
     */
    public ChessPiece getPiece()
    {
        return piece;
    }


    /**
     * @return pieceInformation - information about the piece
     */
    public String getPieceValue()
    {
        String pieceInformation = null;
        if (piece != null)
        {
            pieceInformation = "[" + piece.getCharValue() + "]";
        }
        else
        {
            pieceInformation = "[ ]";
        }
        return pieceInformation;
    }


    /**
     * @return is the tile empty
     */
    public boolean isEmpty()
    {
        return piece == null;
    }


    /**
     * Clear the tile
     */
    public void setEmpty()
    {
        piece = null;
    }
}
