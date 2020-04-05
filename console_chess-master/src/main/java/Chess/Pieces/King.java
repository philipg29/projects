package chess.pieces;


import chess.ChessPiece;
import chess.Move;


public final class King
    extends
    ChessPiece
{

    public King(final ChessPiece.PieceColor color)
    {
        super(PieceType.King, color, validMoves(), false);
    }


    private static Move[] validMoves()
    {
        return new Move[]{new Move(1, 0, false, false), new Move(0, 1, false, false),
                          new Move(-1, 0, false, false), new Move(0, -1, false, false),
                          new Move(1, 1, false, false), new Move(1, -1, false, false),
                          new Move(-1, 1, false, false), new Move(-1, -1, false, false)};
    }
}
