import java.util.ArrayList;

public class MiniMax {
	public ArrayList<Move> actions = new ArrayList<Move>();
	public boolean isDraw = false;
	int alpha, beta;
	int score;
	int level;
	public MiniMax(int level, boolean player, int alpha, int beta){
		
		actions = getLegalActions();
		if (actions == null)
			{
				isDraw = true;	
			}
		if(level == 0)
		{
			return score;	
		}
		
		if(player)//max
			for(Move action : actions)
				score = MiniMax(level -1, !player, alpha, beta);
				if (score > alpha) alpha = score;
				if (alpha >= beta) break;  // beta cut-off
				return alpha;
		
		else // min
			for each action
				score = minimax(level -1, !player, alpha, beta);
				if (score < beta) beta = score;
				if (alpha >= beta) break;  // alpha cut-off
			return beta;
			
	}
}
