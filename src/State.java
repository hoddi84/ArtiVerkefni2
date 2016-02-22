import java.util.ArrayList;

public class State implements Cloneable {
	
	public BoardSquare[][] board;
	
	// Remark 0 in the array is 1 in the game board
	public State(int width, int height)
	{
		board = new BoardSquare[width][height];
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				if (j == 0 || j == 1)
				{
					board[i][j] = BoardSquare.White;
				}
				else if (j == height-2 || j == height-1)
				{
					board[i][j] = BoardSquare.Black;
				}
				else
				{
					board[i][j] = BoardSquare.Empty;
				}
			}
		}
	}
	
	public State(BoardSquare[][] board)
	{
		this.board = board;
	}
	
	/*
	 * Get the state that results from a certain move
	 * */

	public State getNextState(Move move)
	{
		State newState = MakeCopy();
		BoardSquare movingPawn = this.board[move.fromx-1][move.fromy-1];
		newState.board[move.fromx-1][move.fromy-1] = BoardSquare.Empty;
		newState.board[move.tox-1][move.toy-1] = movingPawn;
		return newState;
	}

	
	//TODO maybee it could increase performance
	//if we only check for a goal when we atempt to 
	//move a pawn so we dont have to go through all
	//the row

	/*
	 * Get the state of the game
	 * Returns 100 if black has won
	 * Return 0 if white has won
	 * Return 50 if there is a draw
	 * Returns -1 if no one has won yet
	 * */
	public int getUtility(boolean isMyTurn)
	{
		int boardWidth = this.board.length;
		int boardHeight = this.board[0].length;
		
		for (int i = 0; i < boardWidth; i++)
		{
			if (board[i][boardHeight-1] == BoardSquare.White)
			{
				return 0;
			}
		}
		for (int i = 0; i < boardWidth; i++)
		{
			if (board[i][0] == BoardSquare.Black)
			{
				return 100;
			}
		}
		
		//TODO maybee this is not necessary
		ArrayList<Move> legalMoves = getLegalMoves (isMyTurn);
		if (legalMoves.isEmpty())
		{
			return 50;
		}
			
		//TODO if draw return 50
		return -1;
	}
	/*
	 * MakeCopy() is not being used atm,
	 * instead our getNextState() function
	 * calls clone().
	 */
	public State MakeCopy()
	{
		int width = this.board.length;
		int height = this.board[0].length;
		BoardSquare[][] newBoard = new BoardSquare[width][height];
		
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				newBoard[i][j] = this.board[i][j];
			}
		}
		
		State newState = new State(newBoard);
		return newState;
	}

	/*
	 * Not using.
	 * */
	public State clone() {
		State newState = new State(board);
		return newState;
	}
	
	/* A state evaluation */
	public int evaluate()
	{
		int boardWidth = this.board.length;
		int boardHeight = this.board[0].length;
		
		int heightOfMostAdvancedWhite = 0;
		int heightOfMostAdvancedBlack = 0;

		int nrOfWhites = 0;
		int nrOfBlacks = 0;

		int whiteThreatened = 0;
		int blackThreatened = 0;

		int whiteDefended = 0;
		int blackDefended = 0;

		
		// Count white pawns who are currently threatened.
		for (int j = 0; j < boardHeight; j++)
		{
			for (int i = 0; i < boardWidth; i++)
			{
				if (board[i][j] == BoardSquare.White){

					// Capture left.
					if (i-1 >= 0 && j+1 <= boardHeight-1)
					{
						if (board[i - 1][j + 1] == BoardSquare.Black)
						{
							whiteThreatened += 1;
						}
					}
					// Capture right.
					if (i+1 <= boardWidth-1 && j+1 <= boardHeight-1)
					{
						if (board[i + 1][j + 1] == BoardSquare.Black)
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
				if (board[i][j] == BoardSquare.Black)
				{
					// Capture left.
					if (i-1 >= 0 && j-1 >= 0)
					{
						if (board[i - 1][j - 1] == BoardSquare.White)
						{
							blackThreatened += 1;
						}
					}
					// Capture right.
					if (i+1 <= boardWidth-1 && j-1 >= 0)
					{
						if (board[i + 1][j - 1] == BoardSquare.White)
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
				if (board[i][j] == BoardSquare.Black)
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
				if (board[i][j] == BoardSquare.White)
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
				if (board[i][j] == BoardSquare.Black)
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
				if (board[i][j] == BoardSquare.White)
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
				if (board[i][j] == BoardSquare.White)
				{
					// defended back left.
					if (i-1 >= 0 && j-1 >= 0)
					{
						if (board[i - 1][j - 1] == BoardSquare.White)
						{
							whiteDefended += 1;
						}
					}
					// defended back right.
					if (i+1 <= boardWidth-1 && j-1 >= 0)
					{
						if (board[i + 1][j - 1] == BoardSquare.White)
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
				if (board[i][j] == BoardSquare.Black)
				{
					// defended back left.
					if (i-1 >= 0 && j+1 <= boardHeight-1)
					{
						if (board[i - 1][j + 1] == BoardSquare.Black)
						{
							whiteDefended += 1;
						}
					}
					// defended back right.
					if (i+1 <= boardWidth-1 && j+1 <= boardHeight-1)
					{
						if (board[i + 1][j + 1] == BoardSquare.Black)
						{
							whiteDefended += 1;
						}
					}
				}
			}
		}


		int distMostAdvancedBlack = heightOfMostAdvancedBlack;
		int distMostAdvancedWhite = boardHeight - 1 - heightOfMostAdvancedWhite;

		return 50 + (distMostAdvancedWhite - distMostAdvancedBlack) + 5*(-nrOfWhites + nrOfBlacks) + (whiteThreatened - blackThreatened) + (blackDefended - whiteDefended);
	}


	public ArrayList<Move> getLegalMoves (boolean isMyTurn) {
		ArrayList<Move> moves = new ArrayList<Move>();
		
		//Need to remove this because this method should never return noop
		/*
		if (!isMyTurn) {
			moves.add(new Move());
			return moves;
		}
		*/
		
		int width = board.length;
		int height = board[0].length;
		
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				// White's turn
				if (!isMyTurn) {
					if (board[i][j] == BoardSquare.White) {
						// One forward
						if (board[i][j+1] == BoardSquare.Empty) {
							moves.add(new Move(i, j, i, j+1));
						}
						
						/* This is necessary so we don't get an array index out of bound exception */
						if (i-1 >= 0)
						{
							// Capture left
							if (board[i-1][j+1] == BoardSquare.Black) {
								moves.add(new Move(i, j, i-1, j+1));
							}
						}
						
						if (i+1 <= width-1)
						{
							// Capture right
							if (board[i+1][j+1] == BoardSquare.Black) {
								moves.add(new Move(i, j, i+1, j+1));
							}
						}
					}
				}
				
				// Black's turn
				if (isMyTurn) {
					if (board[i][j] == BoardSquare.Black) {
						// One forward
						if (board[i][j-1] == BoardSquare.Empty) {
							moves.add(new Move(i, j, i, j-1));
						}
						// Capture left
						
						if (i-1 >= 0)
						{
							if (board[i-1][j-1] == BoardSquare.White) {
								moves.add(new Move(i, j, i-1, j-1));
							}
						}
						// Capture right
						if (i+1 <= width - 1)
						{
							if (board[i+1][j-1] == BoardSquare.White) {
								moves.add(new Move(i, j, i+1, j-1));
							}
						}
					}
				}
			}
		}
		return moves;
	}

	public String toString() {
		return "Place is: " + board[0][0];
	}
}
