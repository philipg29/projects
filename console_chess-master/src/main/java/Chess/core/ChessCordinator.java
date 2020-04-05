package chess.core;


import java.util.ArrayList;
import java.util.List;
import chess.core.ChessPiece.PieceColor;
import chess.core.ChessPiece.PieceType;
import chess.utils.ChessConstants;


public final class ChessCordinator
{

    private final ChessBoard gameBoard;
    private boolean isFinished;
    private PieceColor currentPlayer;


    /**
     * Constructor
     */
    public ChessCordinator()
    {
        gameBoard = new ChessBoard();
        currentPlayer = PieceColor.White;
        isFinished = false;
    }


    /**
     * @param from
     * @param to
     * @return returns true if move was played, false if move was illegal
     */
    public boolean playMove(final Tuple from, final Tuple to)
    {
        if (isValidMove(from, to, false))
        {
            Tile fromTile = gameBoard.getBoardArray()[from.getYposition()][from.getXposition()];
            ChessPiece pieceToMove = fromTile.getPiece();

            Tile toTile = gameBoard.getBoardArray()[to.getYposition()][to.getXposition()];
            toTile.setPiece(pieceToMove);

            fromTile.setEmpty();
            setEndTurn();
            return true;
        }
        else
        {
            return false;
        }
    }


    /**
     * Determine whether the Pawn at 'from' on 'board' has moved yet.
     *
     * @param from
     * @param board
     * @return
     */
    public boolean isFirstMoveForPawn(final Tuple from, final ChessBoard board)
    {
        Tile tile = board.getTileFromTuple(from);
        if (tile.isEmpty() || tile.getPiece().getPieceType() != PieceType.Pawn)
        {
            return false;
        }
        else
        {
            PieceColor color = tile.getPiece().getColor();
            return (color == PieceColor.White) ? from.getYposition() == ChessConstants.MAX_BOARD_SIZE - 2
                            : from.getYposition() == ChessConstants.MIN_BOARD_SIZE + 1;
        }
    }


    /**
     * @return returns the current ChessBoard associated with the game.
     */
    public ChessBoard getBoard()
    {
        return gameBoard;
    }


    /**
     * @return returns whether the given ChessGame is finished.
     */
    public boolean isFinished()
    {
        return isFinished;
    }


    private void setEndTurn()
    {
        currentPlayer = (currentPlayer == PieceColor.White) ? PieceColor.Black : PieceColor.White;
    }


    /**
     * Function that checks if any piece can prevent check for the given color This includes whether the King
     * can move out of check himself.
     *
     * @param color
     * @return
     */
    private boolean isCheckPreventable(final PieceColor color)
    {
        boolean canPreventCheck = false;
        Tuple[] locations = gameBoard.getAllPiecesLocationForColor(color);

        for (Tuple location : locations)
        {
            Tile fromTile = gameBoard.getTileFromTuple(location);
            ChessPiece piece = fromTile.getPiece();
            Tuple[] possibleMoves = getValidMovesForPiece(piece, location);

            for (Tuple newLocation : possibleMoves)
            {
                Tile toTile = gameBoard.getTileFromTuple(newLocation);
                ChessPiece toPiece = toTile.getPiece();

                // temporarily play the move to see if it makes us check
                toTile.setPiece(piece);
                fromTile.setEmpty();

                // if we're no longer check
                if (!isKingCheck(color))
                {
                    canPreventCheck = true;
                }

                // revert temporary move
                toTile.setPiece(toPiece);
                fromTile.setPiece(piece);
                if (canPreventCheck)
                { // early out
                    System.out.printf("Prevented with from:" + fromTile + ", to: " + toTile);
                    return canPreventCheck;
                }
            }
        }
        return canPreventCheck;
    }


    /**
     * @param color
     * @return
     */
    private boolean isColorCheckMate(final PieceColor color)
    {
        if (!isKingCheck(color))
            return false;// if not check, then we're not mate
        return !isCheckPreventable(color);
    }


    /**
     * @param kingColor
     * @return
     */
    private boolean isKingCheck(final PieceColor kingColor)
    {
        Tuple kingLocation = gameBoard.getKingLocation(kingColor);
        return canOpponentTakeLocation(kingLocation, kingColor);
    }


    /**
     * @param location
     * @param color
     * @return
     */
    private boolean canOpponentTakeLocation(final Tuple location, final PieceColor color)
    {
        PieceColor opponentColor = ChessPiece.getColour(color);
        Tuple[] piecesLocation = gameBoard.getAllPiecesLocationForColor(opponentColor);

        for (Tuple fromTuple : piecesLocation)
        {
            if (isValidMove(fromTuple, location, true))
                return true;
        }
        return false;
    }


    /**
     * @param from the position from which the player tries to move from
     * @param to the position the player tries to move to
     * @param hypothetical if the move is hypothetical, we disregard if it sets the from player check
     * @return a boolean indicating whether the move is valid or not
     */
    private boolean isValidMove(final Tuple from, final Tuple to, boolean hypothetical)
    {
        Tile fromTile = gameBoard.getTileFromTuple(from);
        Tile toTile = gameBoard.getTileFromTuple(to);
        ChessPiece fromPiece = fromTile.getPiece();
        ChessPiece toPiece = toTile.getPiece();

        if (fromPiece == null)
        {
            return false;
        }
        else if (fromPiece.getColor() != currentPlayer)
        {
            return false;
        }
        else if (toPiece != null && toPiece.getColor() == currentPlayer)
        {
            return false;
        }
        else if (isValidMoveForPiece(from, to))
        {
            // if hypothetical and valid, return true
            if (hypothetical)
                return true;

            // temporarily play the move to see if it makes us check
            toTile.setPiece(fromPiece);
            fromTile.setEmpty();
            if (isKingCheck(currentPlayer))
            {// check that move doesn't put oneself in check
                toTile.setPiece(toPiece);
                fromTile.setPiece(fromPiece);
                return false;
            }

            // if mate, finish game
            if (isColorCheckMate(ChessPiece.getColour(currentPlayer)))
                isFinished = true;

            // revert temporary move
            toTile.setPiece(toPiece);
            fromTile.setPiece(fromPiece);

            return true;
        }
        return false;
    }


    // A utility function that gets all the possible moves for a piece, with illegal ones removed.
    // NOTICE: Does not check for counter-check when evaluating legality.
    // This means it mostly checks if it is a legal move for the piece in terms
    // of ensuring its not taking one of its own, and within its 'possibleMoves'.
    // Returns the Tuples representing the Tiles to which the given piece
    // can legally move.
    /**
     * @param piece
     * @param currentLocation
     * @return
     */
    private Tuple[] getValidMovesForPiece(final ChessPiece piece, final Tuple currentLocation)
    {
        return piece.isRepeatableMove() ? validMovesRepeatable(piece, currentLocation)
                        : validMovesNonRepeatable(piece, currentLocation);
    }


    // Returns the Tuples representing the Tiles to which the given piece
    // can legally move.
    /**
     * @param piece
     * @param currentLocation
     * @return
     */
    private Tuple[] validMovesRepeatable(final ChessPiece piece, final Tuple currentLocation)
    {
        Move[] moves = piece.getMoves();
        List<Tuple> possibleMoves = new ArrayList<>();

        for (Move move : moves)
        {
            for (int i = 1; i < ChessConstants.MAX_BOARD_SIZE - 1; i++)
            {
                int newX = currentLocation.getXposition() + move.getX() * i;
                int newY = currentLocation.getYposition() + move.getY() * i;
                if (newX < 0 || newX > ChessConstants.MAX_BOARD_SIZE - 1 || newY < 0
                    || newY > ChessConstants.MAX_BOARD_SIZE - 1)
                    break;

                Tuple toLocation = new Tuple(newX, newY);
                Tile tile = gameBoard.getTileFromTuple(toLocation);
                if (tile.isEmpty())
                {
                    possibleMoves.add(toLocation);
                }
                else
                {
                    if (tile.getPiece().getColor() != piece.getColor())
                        possibleMoves.add(toLocation);
                    break;
                }
            }
        }
        return possibleMoves.toArray(new Tuple[0]);
    }


    /**
     * @param piece
     * @param currentLocation
     * @return
     */
    private Tuple[] validMovesNonRepeatable(final ChessPiece piece, final Tuple currentLocation)
    {
        Move[] moves = piece.getMoves();
        List<Tuple> possibleMoves = new ArrayList<>();

        for (Move move : moves)
        {
            int currentX = currentLocation.getXposition();
            int currentY = currentLocation.getYposition();
            int newX = currentX + move.getX();
            int newY = currentY + move.getY();
            if (newX < 0 || newX > ChessConstants.MAX_BOARD_SIZE - 1 || newY < 0
                || newY > ChessConstants.MAX_BOARD_SIZE - 1)
                continue;
            Tuple newLocation = new Tuple(newX, newY);
            if (isValidMoveForPiece(currentLocation, newLocation))
                possibleMoves.add(newLocation);
        }
        return possibleMoves.toArray(new Tuple[0]);
    }


    // Checks whether a given move from from one tuple to another is valid.
    /**
     * @param from
     * @param to
     * @return
     */
    private boolean isValidMoveForPiece(final Tuple from, final Tuple to)
    {
        ChessPiece fromPiece = gameBoard.getTileFromTuple(from).getPiece();
        boolean repeatableMoves = fromPiece.isRepeatableMove();

        return repeatableMoves ? isValidMoveForPieceRepeatable(from, to)
                        : isValidMoveForPieceNonRepeatable(from, to);
    }


    // Check whether a given move is valid for a piece without repeatable moves.
    /**
     * @param from
     * @param to
     * @return
     */
    private boolean isValidMoveForPieceRepeatable(final Tuple from, final Tuple to)
    {
        ChessPiece fromPiece = gameBoard.getTileFromTuple(from).getPiece();
        Move[] validMoves = fromPiece.getMoves();

        int xMove = to.getXposition() - from.getXposition();
        int yMove = to.getYposition() - from.getYposition();

        for (int i = 1; i < ChessConstants.MAX_BOARD_SIZE; i++)
        {
            for (Move move : validMoves)
            {

                // generally check for possible move
                if (move.getX() * i == xMove && move.getY() * i == yMove)
                {

                    // if move is generally valid - check if path is valid up till i
                    for (int j = 1; j <= i; j++)
                    {
                        Tile tile = gameBoard.getTileFromTuple(new Tuple(from.getXposition()
                                                                         + move.getX() * j,
                                                                         from.getYposition() + move.getY()
                                                                                               * j));
                        // if passing through non empty tile return false
                        if (j != i && !tile.isEmpty())
                            return false;

                        // if last move and toTile is empty or holds opponents piece, return true
                        if (j == i && (tile.isEmpty() || tile.getPiece().getColor() != currentPlayer))
                            return true;
                    }
                }
            }
        }
        return false;
    }


    // Check whether a given move is valid for a piece with repeatable moves.
    private boolean isValidMoveForPieceNonRepeatable(final Tuple from, final Tuple to)
    {
        ChessPiece fromPiece = gameBoard.getTileFromTuple(from).getPiece();
        Move[] validMoves = fromPiece.getMoves();
        Tile toTile = gameBoard.getTileFromTuple(to);

        int xMove = to.getXposition() - from.getXposition();
        int yMove = to.getYposition() - from.getYposition();

        for (Move move : validMoves)
        {
            if (move.getX() == xMove && move.getY() == yMove)
            {
                if (move.isOnTakeOnly())
                {// if move is only legal on take (pawns)
                    if (toTile.isEmpty())
                        return false;

                    ChessPiece toPiece = toTile.getPiece();
                    return fromPiece.getColor() != toPiece.getColor();// if different color, valid move

                    // handling first move only for pawns - they should not have moved yet
                }
                else if (move.isFirstMoveOnly())
                {
                    return toTile.isEmpty() && isFirstMoveForPawn(from, gameBoard);
                }
                else
                {
                    return toTile.isEmpty();
                }
            }
        }
        return false;
    }

}
