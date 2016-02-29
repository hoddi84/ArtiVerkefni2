import java.sql.ResultSet;
import java.util.ArrayList;

public class MiniMax {


	public static miniResult miniMax(Heuristic heur, State state, int depth, boolean isMyTurn, Role currentPlayer,
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
		int util = state.getUtility(agentRole, currentPlayer);
		ArrayList<Move> actions = new ArrayList<Move>();
		//TODO I think we are calling legalmoves 2x check it out.
		actions = state.getLegalMoves(currentPlayer);
		
		if(actions.isEmpty())return new miniResult(50, actionTaken);
		
		if (util != -1) return new miniResult(util, actionTaken);
		if (util == 100) return new miniResult(util, actionTaken);
		/*Original
		if(depth == 0)
		{	
			return new miniResult(Evaluate.heuristicAdvanced(state,agentRole),actionTaken);
		}
		*/
		if(depth == 0)
		{
			if(heur == Heuristic.Advanced) {
				return new miniResult(Evaluate.heuristicAdvanced(state, agentRole), actionTaken);
			}
			if(heur == Heuristic.Simple) {
				return new miniResult(Evaluate.heuristicSimple(state, agentRole), actionTaken);
			}
			if(heur == Heuristic.Ari) {
				return new miniResult(Evaluate.heuristicAri(state, agentRole), actionTaken);
			}
			if(heur == Heuristic.Hoddi) {
				return new miniResult(Evaluate.heuristicHoddi(state, agentRole), actionTaken);
			}
			if(heur == Heuristic.Binni) {
				return new miniResult(Evaluate.heuristicBinni(state, agentRole), actionTaken);
			}
			if(heur == Heuristic.Tryggvi) {
				return new miniResult(Evaluate.heuristicTryggvi(state, agentRole), actionTaken);
			}
		}

		miniResult bestChoice;
		if (isMyTurn){
			bestChoice = new miniResult(Integer.MIN_VALUE, null);
			for (Move action : actions)
			{	//bs state stuff need to fix
				State newState = state.getNextState(action);
				miniResult currentResult = miniMax(heur, newState, depth -1, !isMyTurn, nextPlayerRole, alpha, beta, action, finishBy);
				if (bestChoice.score < currentResult.score)
				{
					//bestChoice = currentResult;
					bestChoice.score = currentResult.score;
					bestChoice.action = action;
				}
				alpha = Math.max(alpha, bestChoice.score);
				if (beta <= alpha) break; // beta cut-off
			}
		}
		else{
			//int score = Integer.MAX_VALUE;
			bestChoice = new miniResult(Integer.MAX_VALUE, null);
			for (Move action : actions)
			{	//bs state stuff need to fix
				State newState = state.getNextState(action);
				miniResult currentResult = miniMax(heur, newState, depth -1, !isMyTurn, nextPlayerRole, alpha, beta, action, finishBy);
				if (bestChoice.score > currentResult.score)
				{
					//bestChoice = currentResult;
					bestChoice.score = currentResult.score;
					bestChoice.action = action;
				}
				beta = Math.min(beta, bestChoice.score);
				if (beta <= alpha) break; // alpha cut-off
			}
		}
		
		return bestChoice;
	}

	public static Move iterativeDeepening(Heuristic heur, State startState,long finishBy, boolean myTurn, Role agentRole)
	{	miniResult result = new miniResult(0,null);
		int i = 1;
		while (System.currentTimeMillis()<=finishBy && i < 200)
		{
			try {
				result = miniMax(heur, startState, i, myTurn, agentRole, Integer.MIN_VALUE, Integer.MAX_VALUE, null, finishBy);
				if (result.score == 100) break;
			}
			catch (BreakthroughTimoutException e ){
				System.out.println(e.getMessage());
			}
			System.out.println("Depth: "+i + ", CurrBest: " + result.action + ", Value: " + result.score);
			i++;
			
			
		}
		return result.action;
	}

}