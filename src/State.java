import java.util.ArrayList;
import java.util.LinkedList;

public class State {
	
	public BoardSquare[][] board;
	
	public String getLegalActions(boolean whitesTurn)
	{
		//TOOD implement this
		return "";
	}
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
	 * Has a particular player won the game
	 * if isPlayerWhite is true we check if white has won
	 * else we check if black has won
	 * Returns true if the particular player has won
	 * */
	public boolean hasPlayerWon(boolean isPlayerWhite)
	{
		//TODO finish this
		if (isPlayerWhite)
		{
			
		}
		return false;
	}
	
	public void getLegalMoves () {
		
		return;
	}
}
