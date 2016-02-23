/**
 * Created by HörðurMár on 19.2.2016.
 */
public class Evaluate {

    /*
     * A state evaluation.
     * Here we can have multiple heuristics.
     * We have two "different" heuristics.
     * heuristicSimple is: 50 + distMostAdvancedWhite - distMostAdvancedBlack;
     * heuristicAdvanced is: return 50 + (distMostAdvancedWhite - distMostAdvancedBlack) + 5*(-nrOfWhites + nrOfBlacks) + (whiteThreatened - blackThreatened) + (blackDefended - whiteDefended);
     */
    public static int heuristicSimple(State state, Role role)
    {
        int boardWidth = state.board.length;
        int boardHeight = state.board[0].length;

        int heightOfMostAdvancedWhite = 0;
        int heightOfMostAdvancedBlack = 0;

        blackLoop:
        for (int j = 0; j < boardHeight; j++)
        {
            for (int i = 0; i < boardWidth; i++)
            {
                if (state.board[i][j] == BoardSquare.Black)
                {
                    heightOfMostAdvancedBlack = j;
                    break blackLoop;
                }
            }
        }

        whiteLoop:
        for (int j = boardHeight - 1; j >= 0; j--)
        {
            for (int i = 0; i < boardWidth; i++)
            {
                if (state.board[i][j] == BoardSquare.White)
                {
                    heightOfMostAdvancedWhite = j;
                    break whiteLoop;
                }
            }
        }

        int distMostAdvancedBlack = heightOfMostAdvancedBlack;
        int distMostAdvancedWhite = boardHeight - 1 - heightOfMostAdvancedWhite;

        return 50 + distMostAdvancedWhite - distMostAdvancedBlack;
    }

    public static int heuristicAdvanced(State state, Role role) {

        int boardWidth = state.board.length;
        int boardHeight = state.board[0].length;

        int heightOfMostAdvancedWhite = 0;
        int heightOfMostAdvancedBlack = 0;

        int nrOfWhites = 0;
        int nrOfBlacks = 0;

        int whiteThreatened = 0;
        int blackThreatened = 0;

        int whiteDefended = 0;
        int blackDefended = 0;

        int distToHomeWhite = 0;
        int distToHomeBlack = 0;

        int whiteMobility = 0;
        int blackMobility = 0;

        // Count white pawns who are currently threatened.
        for (int j = 0; j < boardHeight; j++)
        {
            for (int i = 0; i < boardWidth; i++)
            {
                if (state.board[i][j] == BoardSquare.White){

                    // Capture left.
                    if (i-1 >= 0 && j+1 <= boardHeight-1)
                    {
                        if (state.board[i - 1][j + 1] == BoardSquare.Black)
                        {
                            whiteThreatened += 1;
                        }
                    }
                    // Capture right.
                    if (i+1 <= boardWidth-1 && j+1 <= boardHeight-1)
                    {
                        if (state.board[i + 1][j + 1] == BoardSquare.Black)
                        {
                            whiteThreatened += 1;
                        }
                    }
                }
            }
        }

        // Count black pawns who are currently threatened.
        for (int j = 0; j < boardHeight; j++)
        {
            for (int i = 0; i < boardWidth; i++)
            {
                if (state.board[i][j] == BoardSquare.Black)
                {
                    // Capture left.
                    if (i-1 >= 0 && j-1 >= 0)
                    {
                        if (state.board[i - 1][j - 1] == BoardSquare.White)
                        {
                            blackThreatened += 1;
                        }
                    }
                    // Capture right.
                    if (i+1 <= boardWidth-1 && j-1 >= 0)
                    {
                        if (state.board[i + 1][j - 1] == BoardSquare.White)
                        {
                            blackThreatened += 1;
                        }
                    }
                }
            }
        }



        // Count black pawns.
        for (int j = 0; j < boardHeight; j++)
        {
            for (int i = 0; i < boardWidth; i++)
            {
                if (state.board[i][j] == BoardSquare.Black)
                {
                    nrOfBlacks += 1;
                }
            }
        }
        // Count white pawns.
        for (int j = 0; j < boardHeight; j++)
        {
            for (int i = 0; i < boardWidth; i++)
            {
                if (state.board[i][j] == BoardSquare.White)
                {
                    nrOfWhites += 1;
                }
            }
        }

        blackLoop:
        for (int j = 0; j < boardHeight; j++)
        {
            for (int i = 0; i < boardWidth; i++)
            {
                if (state.board[i][j] == BoardSquare.Black)
                {
                    heightOfMostAdvancedBlack = j;
                    break blackLoop;
                }
            }
        }

        whiteLoop:
        for (int j = boardHeight - 1; j >= 0; j--)
        {
            for (int i = 0; i < boardWidth; i++)
            {
                if (state.board[i][j] == BoardSquare.White)
                {
                    heightOfMostAdvancedWhite = j;
                    break whiteLoop;
                }
            }
        }

        // Number of white pawns defended.
        for (int j = 0; j < boardHeight; j++)
        {
            for (int i = 0; i < boardWidth; i++)
            {
                if (state.board[i][j] == BoardSquare.White)
                {
                    // defended back left.
                    if (i-1 >= 0 && j-1 >= 0)
                    {
                        if (state.board[i - 1][j - 1] == BoardSquare.White)
                        {
                            whiteDefended += 1;
                        }
                    }
                    // defended back right.
                    if (i+1 <= boardWidth-1 && j-1 >= 0)
                    {
                        if (state.board[i + 1][j - 1] == BoardSquare.White)
                        {
                            whiteDefended += 1;
                        }
                    }
                }
            }
        }
        // Number of black pawns defended.
        for (int j = 0; j < boardHeight; j++)
        {
            for (int i = 0; i < boardWidth; i++)
            {
                if (state.board[i][j] == BoardSquare.Black)
                {
                    // defended back left.
                    if (i-1 >= 0 && j+1 <= boardHeight-1)
                    {
                        if (state.board[i - 1][j + 1] == BoardSquare.Black)
                        {
                            whiteDefended += 1;
                        }
                    }
                    // defended back right.
                    if (i+1 <= boardWidth-1 && j+1 <= boardHeight-1)
                    {
                        if (state.board[i + 1][j + 1] == BoardSquare.Black)
                        {
                            whiteDefended += 1;
                        }
                    }
                }
            }
        }
        // Gives value to distance from home.
        for (int j = 0; j < boardHeight; j++) {
            for (int i = 0; i < boardWidth; i++) {
                if (state.board[i][j] == BoardSquare.White) {
                    if (j-1 >= 0) {
                        if (state.board[i][j - 1] == BoardSquare.Empty) {
                            distToHomeWhite += 1;
                        }
                    }
                }
                if (state.board[i][j] == BoardSquare.Black) {
                    if (j+1 <= boardHeight-1) {
                        if (state.board[i][j + 1] == BoardSquare.Empty) {
                            distToHomeBlack += 1;
                        }
                    }
                }
            }
        }
        // Gives value to pawns mobility.
        for (int j = 0; j < boardHeight; j++) {
            for (int i = 0; i < boardWidth; i++) {
                // Value to whites.
                if (state.board[i][j] == BoardSquare.White) {
                    for (int k = j; k < boardHeight; k++) {
                        if (state.board[i][k] == BoardSquare.Empty) {
                            whiteMobility += 1;
                        }
                    }
                }
                // Value to blacks.
                if (state.board[i][j] == BoardSquare.Black) {
                    for (int k = j; k > 0; k--) {
                        if (state.board[i][k] == BoardSquare.Empty) {
                            blackMobility += 1;
                        }
                    }
                }
            }
        }

        int distMostAdvancedBlack = heightOfMostAdvancedBlack;
        int distMostAdvancedWhite = boardHeight - 1 - heightOfMostAdvancedWhite;

        return 50 + (distMostAdvancedWhite - distMostAdvancedBlack) + (-nrOfWhites + nrOfBlacks) + (whiteThreatened - blackThreatened) + (blackDefended - whiteDefended) + (distToHomeBlack - distToHomeWhite);

    }
}
