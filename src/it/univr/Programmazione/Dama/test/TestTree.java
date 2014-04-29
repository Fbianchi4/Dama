package it.univr.Programmazione.Dama.test;

import static org.junit.Assert.*;
import it.univr.Programmazione.Dama.model.Board;
import it.univr.Programmazione.Dama.model.Box;
import it.univr.Programmazione.Dama.model.King;
import it.univr.Programmazione.Dama.model.QuadraryTree;
import it.univr.Programmazione.Dama.model.SinglePiece;
import it.univr.Programmazione.Dama.resources.Color;
import org.junit.Test;


/**
 * Test sulla costruzione degli alberi.
 */
public class TestTree {
	
	@Test
	public void test1() {

		Board b = new Board();
		cleanBoard(b);
		
		b.getBox(1, 1).setPiece(new King(b, Color.WHITE, b.getBox(1, 1)));
		b.getBox(1, 3).setPiece(new SinglePiece(b, Color.WHITE, b.getBox(1, 3)));
		b.getBox(1, 5).setPiece(new King(b, Color.WHITE, b.getBox(1, 5)));
		b.getBox(3, 1).setPiece(new SinglePiece(b, Color.WHITE, b.getBox(3, 1)));
		b.getBox(3, 3).setPiece(new King(b, Color.WHITE, b.getBox(3, 3)));
		b.getBox(3, 5).setPiece(new SinglePiece(b, Color.WHITE, b.getBox(3, 5)));
		b.getBox(5, 1).setPiece(new SinglePiece(b, Color.WHITE, b.getBox(5, 1)));
		b.getBox(5, 3).setPiece(new SinglePiece(b, Color.WHITE, b.getBox(5, 3)));
		b.getBox(5, 5).setPiece(new King(b, Color.WHITE, b.getBox(5, 3)));
		
		b.getBox(4, 4).setPiece(new King(b, Color.BLACK, b.getBox(4, 4)));
		System.out.println(b);
		
		b.update();
		
		QuadraryTree tree = (QuadraryTree) b.getBox(4,4).getPiece().getTree();
		
		assertTrue(tree.getUpRight() == null && tree.getDownRight() == null &&
				tree.getDownLeft() == null && tree.getUpLeft() != null && 
				tree.getLength() == 8 && tree.getCapturedKing() == 3 &&
				tree.getFirstCapturedKing() == 1);
	}
	
	/* Pulisce la damiera */
	private void cleanBoard(Board b) {
		
		for (Box c : b)
			c.setPiece(null);			
	}

}
