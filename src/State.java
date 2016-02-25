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
	 * role is the role of the agent
	 * If the agent has won 100 is returned
	 * If the agent has lost 0 is returned
	 * If there is a draw 50 is returned
	 * If no one has one -1 is returned
	 * */
	public int getUtility(Role agentRole, Role currentRole)
	{
		int boardWidth = this.board.length;
		int boardHeight = this.board[0].length;
		
		for (int i = 0; i < boardWidth; i++)
		{
			if (board[i][boardHeight-1] == BoardSquare.White)
			{
				if (agentRole == Role.White)
				{
					return 100;
				}
				else
				{
					return 0;
				}
			}
		}
		for (int i = 0; i < boardWidth; i++)
		{
			if (board[i][0] == BoardSquare.Black)
			{
				if (agentRole == Role.Black)
				{
					return 100;
				}
				else
				{
					return 0;
				}
			}
		}
		
		//TODO maybee this is not necessary
		//ArrayList<Move> legalMoves = getLegalMoves (currentRole);
		//if (legalMoves.isEmpty())
		//{
		//	return 50;
		//}
			
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
	 * Get the legal moves for the current player
	 * currentPlayersRole is the role of the current player
	 * and it can be both black or white for the same agent
	 * because the agent checks the moves of the other player
	 * in the minimax algorithm
	 */
	public ArrayList<Move> getLegalMoves (Role currentPlayersRole) {
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
				if (currentPlayersRole == Role.White) {
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
				if (currentPlayersRole == Role.Black) {
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
