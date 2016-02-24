public class AlphaBetaAgent implements Agent
{
	

	private String role; // the name of this agent's role (white or BLACK)
	private int playclock; // this is how much time (in seconds) we have before nextAction needs to return a move
	private boolean myTurn; // whether it is this agent's turn or not
	private int width, height; // dimensions of the board
	
	private State currentState; /* The current state of the board */
	
	/*
		init(String role, int playclock) is called once before you have to select the first action. Use it to initialize the agent. role is either "white" or "BLACK" and playclock is the number of seconds after which nextAction must return.
	*/
    public void init(String role, int width, int height, int playclock) {
		this.role = role;
		this.playclock = playclock;
		myTurn = !role.equals("white");
		this.width = width;
		this.height = height;
		// TODO: add your own initialization code here
		
		currentState = new State(this.width, this.height);
    }

	// lastMove is null the first time nextAction gets called (in the initial state)
    // otherwise it contains the coordinates x1,y1,x2,y2 of the move that the last player did
    public String nextAction(int[] lastMove) {
    	
    	/* The time the search must have finished by */
    	long finishBy = System.currentTimeMillis() + this.playclock * 1000 - 1000;
    	
    	if (lastMove != null) {
    		
    		int x1 = lastMove[0], y1 = lastMove[1], x2 = lastMove[2], y2 = lastMove[3];
    		String roleOfLastPlayer;
    		if (myTurn && role.equals("white") || !myTurn && role.equals("black")) {
    			roleOfLastPlayer = "white";
    		} else {
    			roleOfLastPlayer = "black";
    		}
   			System.out.println(roleOfLastPlayer + " moved from " + x1 + "," + y1 + " to " + x2 + "," + y2);
    		// TODO: 1. update your internal world model according to the action that was just executed
    		
   			Move lastM = new Move(x1-1,y1-1,x2-1,y2-1);

   			currentState = currentState.getNextState(lastM);
    	}
    	
    	/* Update the state based on the last move */
    	
    	// update turn (above that line it myTurn is still for the previous state)
    	Role agentRole = Role.Black;
    	if (this.role.equals("white"))
    	{
    		agentRole = Role.White;
    	}
    	Move nextMove = new Move();
		myTurn = !myTurn;
		if (myTurn) {
			nextMove = MiniMax.iterativeDeepening(currentState, finishBy, myTurn, agentRole);
		}
		else
		{
			return "noop";
		}
		
//		String moveString = "(move " +
//		nextMove.fromx + " " +
//		nextMove.fromy + " " +
//		nextMove.tox + " " +
//		nextMove.toy + " " +
//		")";
		String moveString = nextMove.toString();
		
		//TODO return the right action
		return moveString;
	}

	// is called when the game is over or the match is aborted
	@Override
	public void cleanup() {
		// TODO: cleanup so that the agent is ready for the next match
	}

}
