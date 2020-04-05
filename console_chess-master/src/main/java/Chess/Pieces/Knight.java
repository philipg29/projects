package chess.pieces;


import chess.ChessPiece;
import chess.Move;


public final class Knight
    extends
    ChessPiece
{

    public Knight(final ChessPiece.PieceColor color)
    {
        super(PieceType.Knight, color, validMoves(), false);
    }


    private static Move[] validMoves()
    {
        return new Move[]{new Move(2, 1, false, false), new Move(1, 2, false, false),
                          new Move(2, -1, false, false), new Move(-1, 2, false, false),
                          new Move(2, -1, false, false), new Move(-1, 2, false, false),
                          new Move(-2, 1, false, false), new Move(1, -2, false, false),
                          new Move(-2, -1, false, false), new Move(-1, -2, false, false),
                          new Move(-2, -1, false, false), new Move(-1, -2, false, false)};
    }
}
