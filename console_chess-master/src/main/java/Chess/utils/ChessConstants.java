/*
 * ChessConstants.java
 *
 * created at 2020-04-05 by f.grozdanov <YOURMAILADDRESS>
 *
 * Copyright (c) SEEBURGER AG, Germany. All Rights Reserved.
 */
package chess.utils;

import java.util.regex.Pattern;


public final class ChessConstants
{
    // represents invalid piece position
    public static final int ERROR_POS = -1;
    // max rectangle length
    public static final int MAX_BOARD_SIZE = 8;
    // min rectangle length
    public static final int MIN_BOARD_SIZE = 0;
    // pattern to validate moving from position to position
    public final static Pattern VALID_MOVE_PATTERN = Pattern.compile("([a-hA-H][1-8])([-])([a-hA-H][1-8])",
                                                                     Pattern.CASE_INSENSITIVE);
    // top frame of displaying the chess in console mode
    public final static String BOARD_TOP_FRAME = "      [A][B][C][D][E][F][G][H] \n";

    // windows escape code
    public final static String WINDOWS_ESCAPE_CODE = "\033[H\033[2J";

    // clear message
    public final static String CLEAR_MESSAGE = "Board was cleared";

    // windows system message
    public final static String WINDOWS_OS = "windows";
}
