package chess.movement;


import chess.utils.ChessConstants;


class BoardMapper
{

    private BoardMapper()
    {

    }


    /**
     * Maps position to the reflective one
     *
     * @param position
     * @return
     */
    public static int mapToPosition(int position)
    {

        if (isPositionInRange(position))
        {
            return ChessConstants.ERROR_POS;
        }
        else
        {
            return ChessConstants.MAX_BOARD_SIZE - position;
        }

    }


    public static int mapCharToPosition(final char position)
    {
        switch (Character.toLowerCase(position))
        {
            case 'a':
                return 0;
            case 'b':
                return 1;
            case 'c':
                return 2;
            case 'd':
                return 3;
            case 'e':
                return 4;
            case 'f':
                return 5;
            case 'g':
                return 6;
            case 'h':
                return 7;
        }
        return ChessConstants.ERROR_POS;
    }


    private static boolean isPositionInRange(int position)
    {

        return position >= 0 && position <= ChessConstants.MAX_BOARD_SIZE ? true : false;

    }
}
