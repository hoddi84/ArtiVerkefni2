
public class Main {
	
	/**
	 * starts the game player and waits for messages from the game master <br>
	 * Command line options: [port]
	 */
	public static void main(String[] args){

		State state = new State(10,10);
		System.out.println(state.board[0][0]);
		System.out.println(state.board[0][1]);
		System.out.println(state.board[0][2]);
		System.out.println(state.board[0][3]);
		System.out.println(state.board[0][4]);

		long startClone = System.nanoTime();
		State stateClone = state.clone();
		long finishClone = startClone - System.nanoTime();
		System.out.println("Clone time: " + finishClone);

		long startCopy = System.nanoTime();
		State stateCopy = state.MakeCopy();
		long finishCopy = startCopy - System.nanoTime();
		System.out.println("Copy time: " + finishCopy);

		if(finishClone > finishCopy) {
			System.out.println("Clone is slower");
		}
		else {
			System.out.println("Copy is slower");
		}

		try{
			// TODO: put in your agent here
			Agent agent = new RandomAgent();

			int port=4001;
			if(args.length>=1){
				port=Integer.parseInt(args[0]);
			}
			GamePlayer gp=new GamePlayer(port, agent);
			gp.waitForExit();
		}catch(Exception ex){
			ex.printStackTrace();
			System.exit(-1);
		}
	}
}
