package chess.pieces;


import chess.core.ChessPiece;
import chess.core.Move;



public final class Queen
    extends
    ChessPiece
{

    public Queen(final ChessPiece.PieceColor color)
    {
        super(PieceType.Queen, color, validMoves(), true);
    }


    private static Move[] validMoves()
    {
        return new Move[]{new Move(1, 0, false, false), new Move(0, 1, false, false),
                          new Move(-1, 0, false, false), new Move(0, -1, false, false),
                          new Move(1, 1, false, false), new Move(1, -1, false, false),
                          new Move(-1, 1, false, false), new Move(-1, -1, false, false)};
    }
}
