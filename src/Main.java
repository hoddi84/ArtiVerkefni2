import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class Main {
	
	/**
	 * starts the game player and waits for messages from the game master <br>
	 * Command line options: [port]
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException{
		
		try{
			// TODO: put in your agent here
			Agent agent = new AlphaBetaAgent();
			
			int port = 4001;
			if (args.length>=1){
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
