import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Timer;
import java.util.concurrent.TimeoutException;

public class MiniMax {

	
	public static miniResult miniMax(State state, int depth, boolean isMyTurn, 
			int alpha, int beta, Move actionTaken, long finishBy) throws Exception{
		
		if(System.currentTimeMillis() > finishBy)
		{
			throw new Exception("clock taem");
		}
		
		int score;
		ArrayList<Move> actions = new ArrayList<Move>();
		//TODO I think we are calling legalmoves 2x check it out.
		if(depth == 0 || state.getUtility(isMyTurn) != -1)
		{	
			score = state.evaluate();
			return new miniResult(score,actionTaken);	
		}
		
		actions = state.getLegalMoves(isMyTurn);

		if (isMyTurn){
			score = Integer.MIN_VALUE;
			for (Move action : actions)
			{	//bs state stuff need to fix
				State newState = state.getNextState(action);
				score = Math.max(score,miniMax(newState, depth -1, !isMyTurn, alpha, beta, action, finishBy).score);
				alpha = Math.max(alpha, score);
				if (beta <= alpha) break; // beta cut-off
				return new miniResult(score, action);
				//return score;	
			}
		}
		else{
			score = Integer.MAX_VALUE;
			for (Move action : actions)
			{	//bs state stuff need to fix
				State newState = state.getNextState(action);
				score = Math.min(score, miniMax(newState, depth -1, !isMyTurn, alpha, beta, action, finishBy).score);
				beta = Math.min(beta, score);
				if (beta <= alpha) break; // alpha cut-off
				return new miniResult(score, action);
				//return score;
				
			}			
		}
		
		return null;		
	}
	
	public static Move iterativeDeepening(State startState,long finishBy)
	{	miniResult result = new miniResult(0,null);
		int i = 1;
		while (System.currentTimeMillis()<=finishBy)
		{
			try {
				result = miniMax(startState, i, true, 0, 0, null, finishBy);
			}	
			catch (Exception e ){
				System.out.println(e.getMessage());
			}
			i++;
		}
		return result.action;
	}

}

