import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Hordur on 19.2.2016.
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

        if (role == Role.Black) {
            return 50 - (distMostAdvancedWhite + distMostAdvancedBlack);
        }
        else {
            return 50 - (distMostAdvancedWhite + distMostAdvancedBlack);
        }

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

        if (role == Role.Black) {
            return 50 + (distMostAdvancedWhite - distMostAdvancedBlack)
                      + (-nrOfWhites + nrOfBlacks)
                      + (whiteThreatened - blackThreatened)
                      + (blackDefended - whiteDefended)
                      + (distToHomeBlack - distToHomeWhite);
        }
        else {
            return 50 - ((distMostAdvancedWhite - distMostAdvancedBlack)
                      + (-nrOfWhites + nrOfBlacks) + (whiteThreatened - blackThreatened)
                      + (blackDefended - whiteDefended) + (distToHomeBlack - distToHomeWhite));
        }
    }

    public static int breakThroughHeuristic(State state, Role role) {

        int maxClearColumnsWhite = 0;
        int maxClearColumnsBlack = 0;

        if (role == Role.Black) {
            return 50 + (maxClearColumnsWhite - maxClearColumnsBlack);
        }
        else {
            return 50 - (maxClearColumnsWhite - maxClearColumnsBlack);
        }
    }
    
    /* Heuristic method created by Ari */
    public static int heuristicAri(State state, Role role)
    {
    	BoardSquare[][] board = state.board;
    	int width = board.length;
    	int height = board[0].length;
    	
    	/* Number of pawns for each player */
    	int nrOfWhite = 0;
    	int nrOfBlack = 0;
    	
    	/* Number of pawns that are protecting other pawns
    	 * for each player
    	 *  */
    	int nrOfWhiteProtectors = 0;
    	int nrOfBlackProtectors = 0;
    	
    	/* Number of pawns that threaten other pawns
    	 * for each player */
    	int nrOfWhiteThreateners = 0;
    	int nrOfBlackThreateners = 0;
    	
    	/* Distance of the most advanced pawn to the opposite side
    	 * for each player */
    	int distMostAdvWhite = height - 1;
    	int distMostAdvBlack = height - 1;
    	
    	/* Number of pawns that are blocked for each player.
    	 * That is pawns that can not move. */
    	int nrOfBlockedWhite = 0;
    	int nrOfBlockedBlack = 0;
    	
    	/* Number of columns on the board that
    	 * have only white or only black pawns */
    	int nrOfOnlyWhiteColumns = 0;
    	int nrOfOnlyBlackColumns = 0;
    	
    	for (int i = 0; i < width; i++)
    	{
    		boolean foundWhiteInColumn = false;
    		boolean foundBlackInColumn = false;
    		for (int j = 0; j < height; j++)
    		{
    			BoardSquare square = board[i][j];
    			
    			if (square == BoardSquare.White)
    			{
    				foundWhiteInColumn = true;
    				nrOfWhite++;
    				
    				int distanceToOtherSide = height - 1 - j; 
    				if (distMostAdvWhite > distanceToOtherSide)
    				{
    					distMostAdvWhite = distanceToOtherSide;
    				}
    				
    				boolean isProtector = false;
    				boolean isThreatener = false;
    				
    				if (i-1 >= 0)
    				{
    					BoardSquare upperLeft = board[i-1][j+1];
    					if (upperLeft == BoardSquare.White)
    					{
    						isProtector = true;
    					}
    					else if (upperLeft == BoardSquare.Black)
    					{
    						isThreatener = true;
    					}
    				}
    				
					if (i+1 <= width - 1)
					{
						BoardSquare upperRight = board[i+1][j+1];
						if (upperRight == BoardSquare.White)
						{
							isProtector = true;
						}
						else if (upperRight == BoardSquare.Black)
						{
							isThreatener = true;
						}
					}
					
    				if (isProtector)
    				{
    					nrOfWhiteProtectors++;
    				}
    				if (isThreatener)
    				{
    					nrOfWhiteThreateners++;
    				}
    				else
    				{
    					if (board[i][j+1] != BoardSquare.Empty)
    					{
    						nrOfBlockedWhite++;
    					}
    				}
    			}
    			else if (square == BoardSquare.Black)
    			{
    				foundBlackInColumn = true;
    				nrOfBlack++;
    				
    				if (distMostAdvBlack > j)
    				{
    					distMostAdvBlack = j;
    				}
    				
    				boolean isProtector = false;
    				boolean isThreatener = false;
    				
    				if (i-1 >= 0)
    				{
    					BoardSquare lowerLeft = board[i-1][j-1];
    					if (lowerLeft == BoardSquare.Black)
    					{
    						isProtector = true;
    					}
    					else if (lowerLeft == BoardSquare.White)
    					{
    						isThreatener = true;
    					}
    				}
					if (i+1 <= width - 1)
					{
						BoardSquare lowerRight = board[i+1][j-1];
						if (lowerRight == BoardSquare.Black)
						{
							isProtector = true;
						}
						else if (lowerRight == BoardSquare.White)
						{
							isThreatener = true;
						}
					}
    				if (isProtector)
    				{
    					nrOfBlackProtectors++;
    				}
    				if (isThreatener)
    				{
    					nrOfBlackThreateners++;
    				}
    				else
    				{
    					if (board[i][j-1] != BoardSquare.Empty)
    					{
    						nrOfBlockedBlack++;
    					}
    				}
    			}
    		}
    		
    		if (foundWhiteInColumn && !foundBlackInColumn)
    		{
    			nrOfOnlyWhiteColumns++;
    		}
    		if (foundBlackInColumn && !foundWhiteInColumn)
    		{
    			nrOfOnlyBlackColumns++;
    		}
    	}
    	
    	int val = nrOfBlack - nrOfWhite
    			+ nrOfBlackProtectors - nrOfWhiteProtectors
    			+ nrOfBlackThreateners - nrOfWhiteThreateners
    			- distMostAdvBlack + distMostAdvWhite
    			- nrOfBlockedBlack + nrOfBlockedWhite
    			+ nrOfOnlyBlackColumns - nrOfOnlyWhiteColumns;
    	int tieVal = 50;
    	return role == Role.Black ? tieVal + val : tieVal - val;
    }

    public static int heuristicHoddi(State state, Role role) {

        int boardHeight = state.board[0].length;
        int boardWidth = state.board.length;

        int whiteThreatened = 0;
        int blackThreatened = 0;

        int nrOfBlacks = 0;
        int nrOfWhites = 0;

        int blackDefended = 0;
        int whiteDefended = 0;

        int whiteProtected = 0;
        int blackProtected = 0;

        int heightOfMostAdvancedBlack = 0;
        int heightOfMostAdvancedWhite = 0;

        int whiteForward = 0;
        int blackForward = 0;

        for (int j = 0; j < boardHeight; j++) {
            for (int i = 0; i < boardWidth; i++) {

                // Count black pawns who are threatened
                if (state.board[i][j] == BoardSquare.White){
                    // Capture left.
                    if (i-1 >= 0 && j+1 <= boardHeight-1) {
                        if (state.board[i - 1][j + 1] == BoardSquare.Black) {
                            whiteThreatened += 1;
                        }
                    }
                    // Capture right.
                    if (i+1 <= boardWidth-1 && j+1 <= boardHeight-1) {
                        if (state.board[i + 1][j + 1] == BoardSquare.Black) {
                            whiteThreatened += 1;
                        }
                    }
                }
                // Count black pawns who are threatened
                if (state.board[i][j] == BoardSquare.Black) {
                    // Capture left.
                    if (i-1 >= 0 && j-1 >= 0) {
                        if (state.board[i - 1][j - 1] == BoardSquare.White) {
                            blackThreatened += 1;
                        }
                    }
                    // Capture right.
                    if (i+1 <= boardWidth-1 && j-1 >= 0) {
                        if (state.board[i + 1][j - 1] == BoardSquare.White) {
                            blackThreatened += 1;
                        }
                    }
                }
                // Give value to number of pawns.
                // Black pawns.
                if (state.board[i][j] == BoardSquare.Black) {
                    nrOfBlacks += 1;
                }
                // White pawns
                if (state.board[i][j] == BoardSquare.White) {
                    nrOfWhites += 1;
                }
                // Give value to number of pawns defended.
                // White pawns defended.
                if (state.board[i][j] == BoardSquare.White) {
                    // defended back left.
                    if (i-1 >= 0 && j-1 >= 0) {
                        if (state.board[i - 1][j - 1] == BoardSquare.White) {
                            whiteDefended += 1;
                        }
                    }
                    // defended back right.
                    if (i+1 <= boardWidth-1 && j-1 >= 0) {
                        if (state.board[i + 1][j - 1] == BoardSquare.White) {
                            whiteDefended += 1;
                        }
                    }
                }
                // Black pawns defended.
                if (state.board[i][j] == BoardSquare.Black) {
                    // defended back left.
                    if (i-1 >= 0 && j+1 <= boardHeight-1) {
                        if (state.board[i - 1][j + 1] == BoardSquare.Black) {
                            blackDefended += 1;
                        }
                    }
                    // defended back right.
                    if (i+1 <= boardWidth-1 && j+1 <= boardHeight-1) {
                        if (state.board[i + 1][j + 1] == BoardSquare.Black) {
                            blackDefended += 1;
                        }
                    }
                }
                // Gives value to pawns who protect others.
                // White pawns.
                if (state.board[i][j] == BoardSquare.White) {
                    // Check left.
                    if (i-1 >= 0 && j+1 <= boardHeight-1) {
                        if (state.board[i - 1][j + 1] == BoardSquare.White) {
                            whiteProtected += 1;
                        }
                    }
                    if (i+1 <= boardWidth-1 && j+1 <= boardHeight-1) {
                        // Check right.
                        if (state.board[i + 1][j + 1] == BoardSquare.White) {
                            whiteProtected += 1;
                        }
                    }
                }
                // Black pawns.
                if (state.board[i][j] == BoardSquare.Black) {
                    // Check left.
                    if (i-1 >= 0 && j-1 >= 0) {
                        if (state.board[i - 1][j - 1] == BoardSquare.Black) {
                            blackProtected += 1;
                        }
                    }
                    if (i+1 <= boardWidth-1 && j-1 >= 0) {
                        if (state.board[i + 1][j - 1] == BoardSquare.Black) {
                            blackProtected += 1;
                        }
                    }
                }
                // Looking forward for path clear.
                // White.
                if (state.board[i][j] == BoardSquare.White) {
                    Collection<BoardSquare> list = new ArrayList<>();
                    for (int k = j; k < boardHeight; k++) {
                        if (state.board[i][k] == BoardSquare.Empty || state.board[i][k] == BoardSquare.Black) {
                            list.add(state.board[i][k]);
                        }
                    }
                    if (!list.contains(BoardSquare.Black)){
                        whiteForward += 1;
                    }
                }
                // Black.
                if (state.board[i][j] == BoardSquare.Black) {
                    Collection<BoardSquare> list = new ArrayList<>();
                    for (int k = j; k > 0; k--) {
                        if (state.board[i][k] == BoardSquare.Empty || state.board[i][k] == BoardSquare.White){
                            list.add(state.board[i][k]);
                        }
                    }
                    if (!list.contains(BoardSquare.White)) {
                        blackForward += 1;
                    }
                }


            }
        }
        // Gives value to the most advanced pawn.
        // Black pawn.
        blackLoop:
        for (int j = 0; j < boardHeight; j++) {
            for (int i = 0; i < boardWidth; i++) {
                if (state.board[i][j] == BoardSquare.Black) {
                    heightOfMostAdvancedBlack = j;
                    break blackLoop;
                }
            }
        }
        // White pawn.
        whiteLoop:
        for (int j = boardHeight - 1; j >= 0; j--) {
            for (int i = 0; i < boardWidth; i++) {
                if (state.board[i][j] == BoardSquare.White) {
                    heightOfMostAdvancedWhite = j;
                    break whiteLoop;
                }
            }
        }
        int distMostAdvancedBlack = heightOfMostAdvancedBlack;
        int distMostAdvancedWhite = boardHeight - 1 - heightOfMostAdvancedWhite;

        int valMostAdv = 1;
        int valAmount = 1;
        int valThreat = 1;
        int valDefend = 1;
        int valProtect = 1;
        int valForward = 1;

        if (role == Role.Black) {
            return 50 + valMostAdv*(distMostAdvancedWhite - distMostAdvancedBlack)
                      + valAmount*(-nrOfWhites + nrOfBlacks)
                      + valThreat*(whiteThreatened - blackThreatened)
                      + valDefend*(blackDefended - whiteDefended)
                      + valProtect*(blackProtected - whiteProtected)
                      + valForward*(blackForward - whiteForward);

        }
        else {
            return 50 - ((distMostAdvancedWhite - distMostAdvancedBlack)
                      + (-nrOfWhites + nrOfBlacks)
                      + (whiteThreatened - blackThreatened)
                      + (blackDefended - whiteDefended)
                      + (blackProtected - whiteProtected)
                      + (blackForward - whiteForward));
        }
    }
}
