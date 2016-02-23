import static org.junit.Assert.*;

import org.junit.Test;

public class Testing_MoveTest {

	@Test
	public void testConstructor1() {
		Move m = new Move();
		assertTrue(m.action == "noop");
	}
	
	@Test
	public void testConstructor2()
	{
		Move m = new Move(1,2,3,4);
		assertTrue(m.action == "move");
		assertTrue(m.fromx == 1+1);
		assertTrue(m.fromy == 2+1);
		assertTrue(m.tox == 3+1);
		assertTrue(m.toy == 4+1);
	}
}
