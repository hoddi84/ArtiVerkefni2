public class Move {
	String action; 
	int fromx;
	int fromy;
	int tox;
	int toy;
	
	Move (int fx, int fy, int tx, int ty) {
		// Incremented to match gamecontroller environment
		this.action = "move";
		this.fromx = fx+1;
		this.fromy = fy+1;
		this.tox = tx+1;
		this.toy = ty+1;
	}
	
	Move () {
		this.action = "noop";
	}
	
	@Override
	public String toString()
	{
		return ("(move " + fromx + " " + fromy + " " + tox + " " + toy+ ")");
	}

}
