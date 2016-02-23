import static org.junit.Assert.*;

import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Test;

public class Testing_StateTest {

	@Test
	public void testConsctructor1() {
		
		int width = 3;
		int height = 2;
		BoardSquare[][] board = new BoardSquare[width][height];
		board[0][0] = BoardSquare.Empty;
		board[0][1] = BoardSquare.White;
		board[1][0] = BoardSquare.Empty;
		board[1][1] = BoardSquare.Black;
		board[2][0] = BoardSquare.White;
		board[2][1] = BoardSquare.Black;
		
		State state = new State(board);
		assertTrue(state.board.length == width);
		assertTrue(state.board[0].length == height);
		
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				assertTrue(state.board[i][j] == board[i][j]);
			}
		}
	}
	
	@Test
	public void testConstructor2()
	{
		int width = 4;
		int height = 7;
		
		State state = new State(width, height);
		
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				BoardSquare bsq = state.board[i][j];
				if (j == 0 || j == 1)
				{
					assertTrue(bsq == BoardSquare.White);
				}
				else if (j == height - 1 || j == height -2)
				{
					assertTrue(bsq == BoardSquare.Black);
				}
				else
				{
					assertTrue(bsq == BoardSquare.Empty);
				}
			}
		}
	}
	
	@Test
	public void testMakeCopy()
	{
		int width = 4;
		int height = 5;
		State s1 = new State(width, height);
		s1.board[0][0] = BoardSquare.Black;
		
		State s2 = s1.MakeCopy();
		
		Boolean anyThatDiffer = false;
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				assertTrue(s1.board[i][j] == s2.board[i][j]);
			}
		}
		
		s2.board[0][0] = BoardSquare.White;
		assertTrue(s1.board[0][0] != s2.board[0][0]);
	}
	
	@Test
	public void testGetNextState()
	{
		int width = 2;
		int height = 5;
		
		State oldState = new State(width, height);
		State newState = oldState.getNextState(new Move(0,1,0,2));
		State expectedNewState = new State(width, height);
		expectedNewState.board[0][0] = BoardSquare.White;
		expectedNewState.board[1][0] = BoardSquare.White;
		expectedNewState.board[0][1] = BoardSquare.Empty;
		expectedNewState.board[1][1] = BoardSquare.White;
		expectedNewState.board[0][2] = BoardSquare.White;
		expectedNewState.board[1][2] = BoardSquare.Empty;
		expectedNewState.board[0][3] = BoardSquare.Black;
		expectedNewState.board[1][3] = BoardSquare.Black;
		expectedNewState.board[0][4] = BoardSquare.Black;
		expectedNewState.board[1][4] = BoardSquare.Black;
		
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				assertTrue(newState.board[i][j] == expectedNewState.board[i][j]);
			}
		}
	}
	
	@Test
	public void testGetUtility()
	{
		int width = 3;
		int height = 5;
		
		State whiteWins = new State(width, height);
		whiteWins.board[0][height-1] = BoardSquare.White;
		//assertTrue(whiteWins.getUtility(false) == 0);
		//assertTrue(whiteWins.getUtility(true) == 0);
		
		State blackWins = new State(width, height);
		blackWins.board[0][0] = BoardSquare.Black;
		//assertTrue(blackWins.getUtility(false) == 100);
		//assertTrue(blackWins.getUtility(true) == 100);
		
		width = 2;
		height = 2;
		State tie = new State(width, height);
		tie.board[0][0] = BoardSquare.White;
		tie.board[1][0] = BoardSquare.Empty;
		tie.board[0][1] = BoardSquare.Black;
		tie.board[1][1] = BoardSquare.Empty;
		//assertTrue(tie.getUtility(true) == 50);
		//assertTrue(tie.getUtility(false) == 50);
		
		width = 10;
		height = 10;
		BoardSquare[][] board = new BoardSquare[width][height];
		State midGame = new State(board);
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				board[i][j] = BoardSquare.Empty;
			}
		}
		board[2][1] = BoardSquare.White;
		board[8][1] = BoardSquare.White;
		board[1][1] = BoardSquare.White;
		board[3][8] = BoardSquare.Black;
		board[6][9] = BoardSquare.Black;
		board[9][7] = BoardSquare.Black;
		//assertTrue(midGame.getUtility(true) == -1);
		//assertTrue(midGame.getUtility(false) == -1);
		fail("need to fix this test case");
		
	}
}
