package chess.movement;


import chess.core.ChessBoard;
import chess.core.Tile;
import chess.utils.ChessConstants;
import chess.utils.ChessErrorMessages;


public final class BoardDisplay
{

    /**
     * Prints the board
     *
     * @param board
     */
    public static void printBoard(ChessBoard board)
    {
        clearConsole();
        Tile[][] tileBoard = board.getBoardArray();

        System.out.println();
        System.out.println(ChessConstants.BOARD_TOP_FRAME);
        for (int i = 0; i < ChessConstants.MAX_BOARD_SIZE; i++)
        {
            System.out.print("[" + (ChessConstants.MAX_BOARD_SIZE - i) + "]   ");

            for (int j = 0; j < ChessConstants.MAX_BOARD_SIZE; j++)
            {
                System.out.print(tileBoard[i][j].getPieceValue());
            }

            System.out.println("   [" + (ChessConstants.MAX_BOARD_SIZE - i) + "]");
        }

        System.out.println(ChessConstants.BOARD_TOP_FRAME);
    }


    /**
     * Universal console clear for both Windows and Unix machines.
     */
    public static void clearConsole()
    {
        try
        {
            final String os = System.getProperty("os.name");

            if (os == null || os.trim().isEmpty())
            {
                System.out.println(ChessErrorMessages.UNKNOWN_OS_MSG);

            }
            else if (os.toLowerCase().contains(ChessConstants.WINDOWS_OS))
            {
                // ASCII escape code
                System.out.print(ChessConstants.WINDOWS_ESCAPE_CODE);
            }
            else
            {
                Runtime.getRuntime().exec(ChessConstants.CLEAR_MESSAGE);
            }
        }
        catch (Exception e)
        {
            System.out.println(ChessErrorMessages.CLEAR_BOARD_MSG);
            // log the exception
        }
    }
}
