import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Timer;
import java.util.concurrent.TimeoutException;

public class MiniMax {


	public static miniResult miniMax(State state, int depth, boolean isMyTurn, Role currentPlayer,
									 int alpha, int beta, Move actionTaken, long finishBy) throws BreakthroughTimoutException{

		Role agentRole = currentPlayer;
		if (!isMyTurn)
		{
			if (currentPlayer == Role.Black)
			{
				agentRole = Role.White;
			}
			else
			{
				agentRole = Role.Black;
			}
		}
		Role nextPlayerRole = Role.White;
		if (currentPlayer == Role.White)
		{
			nextPlayerRole = Role.Black;
		}
		
		if(System.currentTimeMillis() > finishBy)
		{
			throw new BreakthroughTimoutException("clock taem");
		}

		ArrayList<Move> actions = new ArrayList<Move>();
		//TODO I think we are calling legalmoves 2x check it out.
		if(depth == 0 || state.getUtility(agentRole, currentPlayer) != -1)
		{
			return new miniResult(Evaluate.heuristicAdvanced(state,agentRole),actionTaken);
		}

		actions = state.getLegalMoves(currentPlayer);

		if (isMyTurn){
			miniResult bestChoice = new miniResult(Integer.MIN_VALUE, null);
			for (Move action : actions)
			{	//bs state stuff need to fix
				State newState = state.getNextState(action);
				if (newState.getUtility(agentRole, currentPlayer) == 100) return new miniResult(100, action);
				miniResult currentResult = miniMax(newState, depth -1, !isMyTurn, nextPlayerRole, alpha, beta, action, finishBy);
				if (bestChoice.score < currentResult.score)
				{
					//bestChoice = currentResult;
					bestChoice.score = currentResult.score;
					bestChoice.action = action;
				}
				alpha = Math.max(alpha, bestChoice.score);
				if (beta <= alpha) break; // beta cut-off
			}
			return bestChoice;
		}
		else{
			//int score = Integer.MAX_VALUE;
			miniResult bestChoice = new miniResult(Integer.MAX_VALUE, null);
			for (Move action : actions)
			{	//bs state stuff need to fix
				State newState = state.getNextState(action);
				
				miniResult currentResult = miniMax(newState, depth -1, !isMyTurn, nextPlayerRole, alpha, beta, action, finishBy);
				if (bestChoice.score > currentResult.score)
				{
					//bestChoice = currentResult;
					bestChoice.score = currentResult.score;
					bestChoice.action = action;
				}
				beta = Math.min(beta, bestChoice.score);
				if (beta <= alpha) break; // alpha cut-off
			}
			return bestChoice;
		}
	}

	public static Move iterativeDeepening(State startState,long finishBy, boolean myTurn, Role agentRole)
	{	miniResult result = new miniResult(0,null);
		int i = 1;
		while (System.currentTimeMillis()<=finishBy)
		{
			try {
				result = miniMax(startState, i, myTurn, agentRole, Integer.MIN_VALUE, Integer.MAX_VALUE, null, finishBy);
				if (result.score == 100) break;
			}
			catch (BreakthroughTimoutException e ){
				System.out.println(e.getMessage());
			}
			System.out.println("Search depth: "+i);
			i++;
		}
		return result.action;
	}

}