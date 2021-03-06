package chess.pieces;


import chess.core.ChessPiece;
import chess.core.Move;


public final class Bishop
    extends
    ChessPiece
{

    public Bishop(final PieceColor color)
    {
        super(PieceType.Bishop, color, validMoves(), true);
    }


    private static Move[] validMoves()
    {
        return new Move[]{new Move(1, 1, false, false), new Move(1, -1, false, false),
                          new Move(-1, 1, false, false), new Move(-1, -1, false, false)};
    }
}
