import java.util.ArrayList;

public class State {
	
	public BoardSquare[][] board;
	
	// Remark 0 in the array is 1 in the game board
	public State(int width, int height)
	{
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
	
	//TODO maybee it could increase performance
	//if we only check for a goal when we atempt to 
	//move a pawn so we dont have to go through all
	//the row

	/*
	 * Get the state of the game
	 * Returns 100 if white has won
	 * Return 0 if black has won
	 * Return 50 if there is a draw
	 * Returns -1 if no one has won yet
	 * */
	public int getUtility(int boardWidth, int boardHeight)
	{
		for (int i = 0; i < boardWidth; i++)
		{
			if (board[i][boardHeight-1] == BoardSquare.White)
			{
				return 100;
			}
		}
		for (int i = 0; i < boardWidth; i++)
		{
			if (board[i][0] == BoardSquare.Black)
			{
				return 0;
			}
		}
		//TODO if draw return 50
		return -1;
	}

	/* A state evaluation */
	public int evaluate(int boardWidth, int boardHeight)
	{
		int heightOfMostAdvancedWhite = 0;
		int heightOfMostAdvancedBlack = 0;

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

		int distMostAdvancedBlack = heightOfMostAdvancedBlack;
		int distMostAdvancedWhite = boardHeight - 1 - heightOfMostAdvancedWhite;

		return 50 - distMostAdvancedWhite + distMostAdvancedBlack;
	}


	public ArrayList<Move> getLegalMoves (String role, boolean isMyTurn) {
		ArrayList<Move> moves = new ArrayList<Move>();
		
		if (!isMyTurn) {
			moves.add(new Move());
			return moves;
		}
		
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				// White's turn
				if (role.equals("white")) {
					if (board[i][j] == BoardSquare.White) {
						// One forward
						if (board[i][j+1] == BoardSquare.Empty) {
							moves.add(new Move(i, j, i, j+1));
						}
						// Capture left
						if (board[i-1][j+1] == BoardSquare.Black) {
							moves.add(new Move(i, j, i-1, j+1));
						}
						// Capture right
						if (board[i+1][j+1] == BoardSquare.Black) {
							moves.add(new Move(i, j, i+1, j+1));
						}
					}
				}
				
				// Black's turn
				if (role.equals("black")) {
					if (board[i][j] == BoardSquare.Black) {
						// One forward
						if (board[i][j-1] == BoardSquare.Empty) {
							moves.add(new Move(i, j, i, j-1));
						}
						// Capture left
						if (board[i-1][j-1] == BoardSquare.White) {
							moves.add(new Move(i, j, i-1, j-1));
						}
						// Capture right
						if (board[i+1][j-1] == BoardSquare.White) {
							moves.add(new Move(i, j, i+1, j-1));
						}
					}
				}
			}
		}
		return moves;
	}
}
