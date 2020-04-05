package chess.movement;

import chess.utils.ChessConstants;
import java.util.regex.Matcher;

import chess.core.Tuple;


public final class UserInputHandler
{

    private UserInputHandler()
    {

    }


    public static Tuple parsePosition(String tuplePosition)
    {
        int x = BoardMapper.mapCharToPosition(tuplePosition.charAt(0));
        int y = BoardMapper.mapToPosition(Integer.parseInt(String.valueOf(tuplePosition.charAt(1))));

        return new Tuple(x, y);
    }


    public static Tuple getFrom(String tuplePosition)
    {
        Matcher matcher = ChessConstants.VALID_MOVE_PATTERN.matcher(tuplePosition);
        matcher.matches();
        String coords = matcher.group(1);

        return parsePosition(coords);
    }


    public static Tuple getTo(String tuplePosition)
    {
        Matcher matcher = ChessConstants.VALID_MOVE_PATTERN.matcher(tuplePosition);
        matcher.matches();
        String coords = matcher.group(3);

        return parsePosition(coords);
    }


    public static boolean isPositionValid(String tuplePosition)
    {
        Matcher matcher = ChessConstants.VALID_MOVE_PATTERN.matcher(tuplePosition);

        return matcher.matches();
    }
}
