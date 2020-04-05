package chess.runner;

import chess.movement.BoardDisplay;
import chess.movement.UserInputHandler;
import java.util.Scanner;
import chess.core.ChessCordinator;
import chess.core.Tuple;
import chess.utils.ChessErrorMessages;
import chess.utils.ChessInformativeMessages;


public final class ChessRunner
{

    public static void main(String args[])
        throws Exception
    {

        Scanner scanner = new Scanner(System.in);

        ChessCordinator game = new ChessCordinator();
        BoardDisplay.clearConsole();
        BoardDisplay.printBoard(game.getBoard());
        while (!game.isFinished())
        {
            System.out.println(ChessInformativeMessages.ENTER_MOVE);
            String input = scanner.nextLine();

            if (!UserInputHandler.isPositionValid(input))
            {
                System.out.println(ChessErrorMessages.INVALID_USER_INPUT);

            }
            else
            {
                Tuple from = UserInputHandler.getFrom(input);
                Tuple to = UserInputHandler.getTo(input);

                boolean movePlayed = game.playMove(from, to);
                if (!movePlayed)
                    System.out.println(ChessErrorMessages.ILLEGAL_MOVE);
                else
                {
                    BoardDisplay.clearConsole();
                    BoardDisplay.printBoard(game.getBoard());
                }
            }
        }
        scanner.close();
        System.out.println(ChessInformativeMessages.THANKS_FOR_PLAYING);
    }
}
