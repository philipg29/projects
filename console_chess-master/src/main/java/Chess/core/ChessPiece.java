package chess.core;


public abstract class ChessPiece
{
    private final PieceType type;
    private final PieceColor color;
    private final Move[] moves;
    private final String name;
    private final char charValue;
    private final boolean repeatableMoves;

    public enum PieceType
    {
        Pawn, Rook, Knight, Bishop, Queen, King
    }

    public enum PieceColor
    {
        White, Black
    }


    public ChessPiece(final PieceType type,final  PieceColor color,final  Move[] moves, boolean repeatableMoves)
    {
        this.type = type;
        this.color = color;
        this.moves = moves;
        this.repeatableMoves = repeatableMoves;
        name = type.name();
        charValue = type.name().trim().charAt(0);
    }


    public Move[] getMoves()
    {
        return moves;
    }


    public String getName()
    {
        return name;
    }


    public PieceColor getColor()
    {
        return color;
    }


    public char getCharValue()
    {
        return charValue;
    }


    public boolean isRepeatableMove()
    {
        return repeatableMoves;
    }


    public PieceType getPieceType()
    {
        return type;
    }


    public static PieceColor getColour(final PieceColor color)
    {
        return (color == PieceColor.Black) ? PieceColor.White : PieceColor.Black;
    }

}
