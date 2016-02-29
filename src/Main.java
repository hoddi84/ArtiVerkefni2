import java.io.IOException;

public class Main {
	
	/**
	 * starts the game player and waits for messages from the game master <br>
	 * Command line options: [port]
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException{
		
		try{
			// TODO: put in your agent here
			Agent agent = new AlphaBetaAgent(Heuristic.Hoddi);
			Agent agent2 = new AlphaBetaAgent(Heuristic.Ari);
			
			int port = 4001, port2 = 4002;
			if (args.length>=1){
				port=Integer.parseInt(args[0]);
			}
			GamePlayer gp = new GamePlayer(port, agent);
			GamePlayer gp2 = new GamePlayer(port2, agent2);
			gp.waitForExit();
			gp2.waitForExit();
		}catch(Exception ex){
			ex.printStackTrace();
			System.exit(-1);
		}
	}
}
