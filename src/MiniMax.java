import java.util.ArrayList;

public class MiniMax {
	//public ArrayList<Move> actions = new ArrayList<Move>();
	public static int score;
	public static int MiniMax(State state, int depth, boolean isMyTurn, int alpha, int beta){
		
		ArrayList<Move> actions = new ArrayList<Move>();
		//TODO I think we are calling legalmoves 2x check it out.
		if(depth == 0 || state.getUtility(isMyTurn) != -1)
		{
			return state.evaluate();	
		}
		
		actions = state.getLegalMoves(isMyTurn);

		if (isMyTurn){
			score = Integer.MIN_VALUE;
			for (Move action : actions)
			{	//bs state stuff need to fix
				State newState = state.getNextState(action);
				score = Math.max(score,MiniMax(newState, depth -1, !isMyTurn, alpha, beta));
				alpha = Math.max(alpha, score);
				if (beta <= alpha) break; // beta cut-off
				return score;	
			}
		}
		else{
			score = Integer.MAX_VALUE;
			for (Move action : actions)
			{	//bs state stuff need to fix
				State newState = state.getNextState(action);
				score = Math.min(score, MiniMax(newState, depth -1, !isMyTurn, alpha, beta));
				beta = Math.min(beta, score);
				if (beta <= alpha) break; // alpha cut-off
				return score;
				
			}			
		}
		//originally to get rid of error, maybe find a way to remove.
		return state.evaluate();
		
				
	}
}
