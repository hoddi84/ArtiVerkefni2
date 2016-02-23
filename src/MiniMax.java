import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Timer;
import java.util.concurrent.TimeoutException;

public class MiniMax {


	public static miniResult miniMax(State state, int depth, boolean isMyTurn,
									 int alpha, int beta, Move actionTaken, long finishBy) throws BreakthroughTimoutException{

		if(System.currentTimeMillis() > finishBy)
		{
			throw new BreakthroughTimoutException("clock taem");
		}

		ArrayList<Move> actions = new ArrayList<Move>();
		//TODO I think we are calling legalmoves 2x check it out.
		if(depth == 0 || state.getUtility(isMyTurn) != -1)
		{
			return new miniResult(Evaluate.heuristicAdvanced(state),actionTaken);
		}

		actions = state.getLegalMoves(isMyTurn);

		if (isMyTurn){
			//int score = Integer.MIN_VALUE;
			miniResult bestChoice = new miniResult(Integer.MIN_VALUE, null);
			for (Move action : actions)
			{	//bs state stuff need to fix
				State newState = state.getNextState(action);
				if (newState.getUtility(isMyTurn) == 100) return new miniResult(100, action);
				miniResult currentResult = miniMax(newState, depth -1, !isMyTurn, alpha, beta, action, finishBy);
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
				if (newState.getUtility(isMyTurn) == 0) return new miniResult(0, action);
				miniResult currentResult = miniMax(newState, depth -1, !isMyTurn, alpha, beta, action, finishBy);
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

	public static Move iterativeDeepening(State startState,long finishBy)
	{	miniResult result = new miniResult(0,null);
		int i = 1;
		while (System.currentTimeMillis()<=finishBy)
		{
			try {
				result = miniMax(startState, i, true, Integer.MIN_VALUE, Integer.MAX_VALUE, null, finishBy);
			}
			catch (BreakthroughTimoutException e ){
				System.out.println(e.getMessage());
			}
			i++;
		}
		return result.action;
	}

}