package it.univr.Programmazione.Dama.test;

import org.junit.*;
import static org.junit.Assert.*;
import it.univr.Programmazione.Dama.controller.MoveException;
import it.univr.Programmazione.Dama.controller.PlayerMove;
import it.univr.Programmazione.Dama.model.Box;
import it.univr.Programmazione.Dama.model.King;
import it.univr.Programmazione.Dama.model.Board;
import it.univr.Programmazione.Dama.model.SinglePiece;
import it.univr.Programmazione.Dama.resources.Color;


/**
 * Test delle regole della dama.
 */
public class TestRules {
	
	/* Se una pedina puo' mangiare, un'altra pedina non puo' muoversi */
	@Test
	public void test1() {
		
		Board d = new Board();
		cleanBoard(d);
		
		d.getBox(2, 2).setPiece(
				new SinglePiece(d, Color.BLACK, d.getBox(2, 2)));
		d.getBox(3, 3).setPiece(
				new SinglePiece(d, Color.WHITE, d.getBox(3, 3)));
		d.getBox(1, 1).setPiece(
				new SinglePiece(d, Color.BLACK, d.getBox(1, 1)));
		d.update();
		
		try {
			new PlayerMove(d.getBox(1, 1), d.getBox(2, 0), d);
		}
		catch (MoveException e) {
			assertTrue(e.getMessage().equals
					("Non puoi spostarti se qualcuno pu� mangiare"));
		}
	}	
	
	/* Se una pedina puo' mangiare, una dama non puo' muoversi */
	@Test
	public void test2() {
		
		Board d = new Board();
		cleanBoard(d);
		
		d.getBox(2, 2).setPiece(
				new SinglePiece(d, Color.BLACK, d.getBox(2, 2)));
		d.getBox(3, 3).setPiece(
				new SinglePiece(d, Color.WHITE, d.getBox(3, 3)));
		d.getBox(1, 1).setPiece(
				new King(d, Color.BLACK, d.getBox(1, 1)));
		d.update();
		
		try {
			new PlayerMove(d.getBox(1, 1), d.getBox(2, 0), d);
		}
		catch (MoveException e) {
			assertTrue(e.getMessage().equals
					("Non puoi spostarti se qualcuno pu� mangiare"));
		}
	}	
	
	/* Se una dama puo' mangiare, una pedina non puo' muoversi */
	@Test
	public void test3() {
		
		Board d = new Board();
		cleanBoard(d);
		
		d.getBox(2, 2).setPiece(
				new King(d, Color.BLACK, d.getBox(2, 2)));
		d.getBox(3, 3).setPiece(
				new SinglePiece(d, Color.WHITE, d.getBox(3, 3)));
		d.getBox(1, 1).setPiece(
				new SinglePiece(d, Color.BLACK, d.getBox(1, 1)));
		d.update();
		
		try {
			new PlayerMove(d.getBox(1, 1), d.getBox(2, 0), d);
		}
		catch (MoveException e) {
			assertTrue(e.getMessage().equals
					("Non puoi spostarti se qualcuno pu� mangiare"));
		}
	}	
	
	/* Se una dama puo' mangiare, un'altra dama non puo' muoversi */
	@Test
	public void test4() {
		
		Board d = new Board();
		cleanBoard(d);
		
		d.getBox(2, 2).setPiece(
				new King(d, Color.BLACK, d.getBox(2, 2)));
		d.getBox(3, 3).setPiece(
				new SinglePiece(d, Color.WHITE, d.getBox(3, 3)));
		d.getBox(1, 1).setPiece(
				new King(d, Color.BLACK, d.getBox(1, 1)));
		d.update();
		
		try {
			new PlayerMove(d.getBox(1, 1), d.getBox(2, 0), d);
		}
		catch (MoveException e) {
			assertTrue(e.getMessage().equals
					("Non puoi spostarti se qualcuno pu� mangiare"));
		}
	}	
	
	/* Se una pedina p1 puo' mangiare n pezzi e un'altra pedina p2 puo' mangiarne
	 * m > n, allora la pedina p1 non puo' mangiare */
	@Test
	public void test5() {
		
		Board d = new Board();
		cleanBoard(d);
		
		d.getBox(2, 2).setPiece(
				new SinglePiece(d, Color.BLACK, d.getBox(2, 2)));
		d.getBox(3, 3).setPiece(
				new SinglePiece(d, Color.WHITE, d.getBox(3, 3)));
		d.getBox(5, 5).setPiece(
				new SinglePiece(d, Color.WHITE, d.getBox(5, 5)));
		d.getBox(4, 6).setPiece(
				new SinglePiece(d, Color.BLACK, d.getBox(4, 6)));		
		d.update();

		try {
			new PlayerMove(d.getBox(4, 6), d.getBox(6, 4), d);
		}
		catch (MoveException e) {
			assertTrue(e.getMessage().equals
					("Il pezzo non pu� mangiare"));
		}
	}
	
	/* Se una pedina p1 puo' mangiare n pezzi e un'altra pedina p2 puo' mangiarne
	 * m < n, allora la pedina p1 puo' mangiare */
	@Test
	public void test6() {
		
		Board d = new Board();
		cleanBoard(d);
		
		d.getBox(2, 2).setPiece(
				new SinglePiece(d, Color.BLACK, d.getBox(2, 2)));
		d.getBox(3, 3).setPiece(
				new SinglePiece(d, Color.WHITE, d.getBox(3, 3)));
		d.getBox(5, 5).setPiece(
				new SinglePiece(d, Color.WHITE, d.getBox(5, 5)));
		d.getBox(4, 6).setPiece(
				new SinglePiece(d, Color.BLACK, d.getBox(4, 6)));		
		d.update();

		try {
			new PlayerMove(d.getBox(2, 2), d.getBox(6, 6), d);
		}
		catch (MoveException e) {
			assertTrue(e.getMessage().equals
					("Spostati prima sulle caselle intermedie"));
		}
	}	
	
	/* Se una pedina p1 puo' mangiare n pezzi e un'altra pedina p2 puo' mangiarne
	 * m = n, allora la pedina p1 puo' mangiare */
	@Test
	public void test7() {
		
		Board d = new Board();
		cleanBoard(d);
		
		d.getBox(2, 2).setPiece(
				new SinglePiece(d, Color.BLACK, d.getBox(2, 2)));
		d.getBox(3, 3).setPiece(
				new SinglePiece(d, Color.WHITE, d.getBox(3, 3)));
		d.getBox(5, 5).setPiece(
				new SinglePiece(d, Color.WHITE, d.getBox(5, 5)));
		d.getBox(4, 6).setPiece(
				new SinglePiece(d, Color.BLACK, d.getBox(4, 6)));
		d.getBox(6, 6).setPiece(
				new SinglePiece(d, Color.BLACK, d.getBox(6, 6)));
		d.update();

		new PlayerMove(d.getBox(4, 6), d.getBox(6, 4), d);
	
		assertTrue(d.getBox(5, 5).isEmpty());
	}
	
	/* Se una pedina puo' mangiare n pezzi e una dama puo' mangiarne m > n,
	 * allora la pedina non puo' mangiare */
	@Test
	public void test8() {
		
		Board d = new Board();
		cleanBoard(d);
		
		d.getBox(2, 2).setPiece(
				new King(d, Color.BLACK, d.getBox(2, 2)));
		d.getBox(3, 3).setPiece(
				new King(d, Color.WHITE, d.getBox(3, 3)));
		d.getBox(5, 5).setPiece(
				new SinglePiece(d, Color.WHITE, d.getBox(5, 5)));
		d.getBox(4, 6).setPiece(
				new SinglePiece(d, Color.BLACK, d.getBox(4, 6)));			
		d.update();
		
		try {
			new PlayerMove(d.getBox(4, 6), d.getBox(6, 4), d);
		}
		catch (MoveException e) {
			assertTrue(e.getMessage().equals
					("Il pezzo non pu� mangiare"));
		}
	}
	
	/* Se una pedina puo' mangiare n pezzi e una dama puo' mangiarne m < n,
	 * allora la pedina puo' mangiare */
	@Test
	public void test9() {
		
		Board d = new Board();
		cleanBoard(d);
		
		d.getBox(2, 2).setPiece(
				new SinglePiece(d, Color.BLACK, d.getBox(2, 2)));
		d.getBox(3, 3).setPiece(
				new SinglePiece(d, Color.WHITE, d.getBox(3, 3)));
		d.getBox(5, 5).setPiece(
				new SinglePiece(d, Color.WHITE, d.getBox(5, 5)));
		d.getBox(4, 6).setPiece(
				new King(d, Color.BLACK, d.getBox(4, 6)));			
		d.update();
		
		try {
			new PlayerMove(d.getBox(2, 2), d.getBox(6, 6), d);
		}
		catch (MoveException e) {
			assertTrue(e.getMessage().equals
					("Spostati prima sulle caselle intermedie"));
		}
	}
	
	/* Se una pedina puo' mangiare n pezzi e una dama puo' mangiarne m = n,
	 * allora la pedina non puo' mangiare */
	@Test
	public void test10() {
		
		Board d = new Board();
		cleanBoard(d);
		
		d.getBox(2, 2).setPiece(
				new SinglePiece(d, Color.BLACK, d.getBox(2, 2)));
		d.getBox(3, 3).setPiece(
				new SinglePiece(d, Color.WHITE, d.getBox(3, 3)));
		d.getBox(5, 5).setPiece(
				new SinglePiece(d, Color.WHITE, d.getBox(5, 5)));
		d.getBox(5, 1).setPiece(
				new SinglePiece(d, Color.WHITE, d.getBox(5, 1)));
		d.getBox(2, 4).setPiece(
				new King(d, Color.BLACK, d.getBox(2, 4)));
		d.update();
		
		try {
			new PlayerMove(d.getBox(2, 2), d.getBox(6, 6), d);
		}
		catch (MoveException e) {
			assertTrue(e.getMessage().equals
					("Il pezzo non puo' mangiare"));
		}
	}
	
	/* Se una dama puo' mangiare n pezzi e una pedina puo' mangiarne m > n,
	 * allora la dama non puo' mangiare */
	@Test
	public void test11() {
		
		Board d = new Board();
		cleanBoard(d);
		
		d.getBox(2, 2).setPiece(
				new SinglePiece(d, Color.BLACK, d.getBox(2, 2)));
		d.getBox(3, 3).setPiece(
				new SinglePiece(d, Color.WHITE, d.getBox(3, 3)));
		d.getBox(5, 5).setPiece(
				new SinglePiece(d, Color.WHITE, d.getBox(5, 5)));
		d.getBox(4, 6).setPiece(
				new King(d, Color.BLACK, d.getBox(4, 6)));			
		d.update();
		
		try {
			new PlayerMove(d.getBox(4, 6), d.getBox(6, 4), d);
		}
		catch (MoveException e) {
			assertTrue(e.getMessage().equals
					("Il pezzo non puo' mangiare"));
		}
	}
	
	/* Se una dama puo' mangiare n pezzi e una pedina puo' mangiarne m < n,
	 * allora la dama puo' mangiare */
	@Test
	public void test12() {
		
		Board d = new Board();
		cleanBoard(d);
		
		d.getBox(2, 2).setPiece(
				new King(d, Color.BLACK, d.getBox(2, 2)));
		d.getBox(3, 3).setPiece(
				new SinglePiece(d, Color.WHITE, d.getBox(3, 3)));
		d.getBox(5, 5).setPiece(
				new SinglePiece(d, Color.WHITE, d.getBox(5, 5)));
		d.getBox(4, 6).setPiece(
				new SinglePiece(d, Color.BLACK, d.getBox(4, 6)));			
		d.update();
		
		try {
			new PlayerMove(d.getBox(2, 2), d.getBox(6, 6), d);
		}
		catch (MoveException e) {
			assertTrue(e.getMessage().equals
					("Spostati prima sulle caselle intermedie"));
		}
	}
	
	/* Se una dama puo' mangiare n pezzi e una pedina puo' mangiarne m = n,
	 * allora la dama puo' mangiare */
	@Test
	public void test13() {
		
		Board d = new Board();
		cleanBoard(d);
		
		d.getBox(2, 2).setPiece(
				new SinglePiece(d, Color.BLACK, d.getBox(2, 2)));
		d.getBox(3, 3).setPiece(
				new SinglePiece(d, Color.WHITE, d.getBox(3, 3)));
		d.getBox(5, 5).setPiece(
				new SinglePiece(d, Color.WHITE, d.getBox(5, 5)));
		d.getBox(5, 1).setPiece(
				new SinglePiece(d, Color.WHITE, d.getBox(5, 1)));
		d.getBox(2, 4).setPiece(
				new King(d, Color.BLACK, d.getBox(2, 4)));
		d.update();
		
		try {
			new PlayerMove(d.getBox(2, 4), d.getBox(6, 0), d);
		}
		catch (MoveException e) {
			assertTrue(e.getMessage().equals
					("Spostati prima sulle caselle intermedie"));
		}
	}
	
	/* Se una dama d1 puo' mangiare n pezzi e un'altra dama d2 puo' mangiarne
	 * m > n, allora la dama d1 non puo' mangiare */
	@Test
	public void test14() {
		
		Board d = new Board();
		cleanBoard(d);
		
		d.getBox(2, 2).setPiece(
				new King(d, Color.BLACK, d.getBox(2, 2)));
		d.getBox(3, 3).setPiece(
				new SinglePiece(d, Color.WHITE, d.getBox(3, 3)));
		d.getBox(5, 5).setPiece(
				new SinglePiece(d, Color.WHITE, d.getBox(5, 5)));
		d.getBox(2, 4).setPiece(
				new King(d, Color.BLACK, d.getBox(2, 4)));
		d.update();
		
		try {
			new PlayerMove(d.getBox(2, 4), d.getBox(4, 2), d);
		}
		catch (MoveException e) {
			assertTrue(e.getMessage().equals
					("Il pezzo non pu� mangiare"));
		}
	}
	
	/* Se una dama d1 puo' mangiare n pezzi e un'altra dama d2 puo' mangiarne
	 * m < n, allora la dama d1 puo' mangiare */
	@Test
	public void test15() {
		
		Board d = new Board();
		cleanBoard(d);
		
		d.getBox(2, 2).setPiece(
				new King(d, Color.BLACK, d.getBox(2, 2)));
		d.getBox(3, 3).setPiece(
				new SinglePiece(d, Color.WHITE, d.getBox(3, 3)));
		d.getBox(5, 5).setPiece(
				new SinglePiece(d, Color.WHITE, d.getBox(5, 5)));
		d.getBox(2, 4).setPiece(
				new King(d, Color.BLACK, d.getBox(2, 4)));
		d.update();
		
		try {
			new PlayerMove(d.getBox(2, 2), d.getBox(6, 6), d);
		}
		catch (MoveException e) {
			assertTrue(e.getMessage().equals
					("Spostati prima sulle caselle intermedie"));
		}
	}
	
	/* Se una dama d1 puo' mangiare n pezzi e un'altra dama d2 puo' mangiarne
	 * m = n e d1 mangia piu' dame di d2, allora la dama d1 puo' mangiare */
	@Test
	public void test16() {
		
		Board d = new Board();
		cleanBoard(d);
		
		d.getBox(2, 2).setPiece(
				new King(d, Color.BLACK, d.getBox(2, 2)));
		d.getBox(3, 3).setPiece(
				new SinglePiece(d, Color.WHITE, d.getBox(3, 3)));
		d.getBox(5, 5).setPiece(
				new King(d, Color.WHITE, d.getBox(5, 5)));
		d.getBox(5, 1).setPiece(
				new SinglePiece(d, Color.WHITE, d.getBox(5, 1)));
		d.getBox(2, 4).setPiece(
				new King(d, Color.BLACK, d.getBox(2, 4)));
		d.update();
		
		try {
			new PlayerMove(d.getBox(2, 2), d.getBox(6, 6), d);
		}
		catch (MoveException e) {
			assertTrue(e.getMessage().equals
					("Spostati prima sulle caselle intermedie"));
		}
	}
	
	/* Se una dama d1 puo' mangiare n pezzi e un'altra dama d2 puo' mangiarne
	 * m = n e d1 mangia meno dame di d2, allora la dama d1 non puo' mangiare */
	@Test
	public void test17() {
		
		Board d = new Board();
		cleanBoard(d);
		
		d.getBox(2, 2).setPiece(
				new King(d, Color.BLACK, d.getBox(2, 2)));
		d.getBox(3, 3).setPiece(
				new SinglePiece(d, Color.WHITE, d.getBox(3, 3)));
		d.getBox(5, 5).setPiece(
				new King(d, Color.WHITE, d.getBox(5, 5)));
		d.getBox(5, 1).setPiece(
				new SinglePiece(d, Color.WHITE, d.getBox(5, 1)));
		d.getBox(2, 4).setPiece(
				new King(d, Color.BLACK, d.getBox(2, 4)));
		d.update();
		
		try {
			new PlayerMove(d.getBox(2, 4), d.getBox(6, 0), d);
		}
		catch (MoveException e) {
			assertTrue(e.getMessage().equals
					("Il pezzo non puo' mangiare"));
		}
	}
	
	/* Se una dama d1 puo' mangiare n pezzi e un'altra dama d2 puo' mangiarne
	 * m = n e d1 mangia tante dame quante d2 e d1 mangia una dama prima di d2,
     * allora la dama d1 puo' mangiare */
	@Test
	public void test18() {
		
		Board d = new Board();
		cleanBoard(d);
		
		d.getBox(6, 6).setPiece(
				new King(d, Color.BLACK, d.getBox(6, 6)));
		d.getBox(3, 3).setPiece(
				new SinglePiece(d, Color.WHITE, d.getBox(3, 3)));
		d.getBox(5, 5).setPiece(
				new King(d, Color.WHITE, d.getBox(5, 5)));
		d.getBox(5, 1).setPiece(
				new King(d, Color.WHITE, d.getBox(5, 1)));
		d.getBox(2, 4).setPiece(
				new King(d, Color.BLACK, d.getBox(2, 4)));
		d.update();
		
		try {
			new PlayerMove(d.getBox(6, 6), d.getBox(2, 2), d);
		}
		catch (MoveException e) {
			assertTrue(e.getMessage().equals
					("Spostati prima sulle caselle intermedie"));
		}
	}
	
	/* Se una dama d1 puo' mangiare n pezzi e un'altra dama d2 puo' mangiarne
	 * m = n e d1 mangia tante dame quante d2 e d1 mangia una dama dopo di d2,
     * allora la dama d1 non puo' mangiare */
	@Test
	public void test19() {
		
		Board d = new Board();
		cleanBoard(d);
		
		d.getBox(6, 6).setPiece(
				new King(d, Color.BLACK, d.getBox(6, 6)));
		d.getBox(3, 3).setPiece(
				new SinglePiece(d, Color.WHITE, d.getBox(3, 3)));
		d.getBox(5, 5).setPiece(
				new King(d, Color.WHITE, d.getBox(5, 5)));
		d.getBox(5, 1).setPiece(
				new King(d, Color.WHITE, d.getBox(5, 1)));
		d.getBox(2, 4).setPiece(
				new King(d, Color.BLACK, d.getBox(2, 4)));
		d.update();
		
		try {
			new PlayerMove(d.getBox(2, 4), d.getBox(6, 0), d);
		}
		catch (MoveException e) {
			assertTrue(e.getMessage().equals
					("Il pezzo non puo' mangiare"));
		}
	}
	
	/* Se una dama d1 puo' mangiare n pezzi e un'altra dama d2 puo' mangiarne
	 * m = n e d1 mangia tante dame quante d2 e d1 mangia una dama dopo lo
	 * stesso numero di passi di d2, allora la dama d1 puo' mangiare */
	@Test
	public void test20() {
		
		Board d = new Board();
		cleanBoard(d);
		
		d.getBox(2, 2).setPiece(
				new King(d, Color.BLACK, d.getBox(2, 2)));
		d.getBox(3, 3).setPiece(
				new SinglePiece(d, Color.WHITE, d.getBox(3, 3)));
		d.getBox(5, 5).setPiece(
				new King(d, Color.WHITE, d.getBox(5, 5)));
		d.getBox(5, 1).setPiece(
				new King(d, Color.WHITE, d.getBox(5, 1)));
		d.getBox(2, 4).setPiece(
				new King(d, Color.BLACK, d.getBox(2, 4)));
		d.update();
		
		try {
			new PlayerMove(d.getBox(2, 2), d.getBox(6, 6), d);
		}
		catch (MoveException e) {
			assertTrue(e.getMessage().equals
					("Spostati prima sulle caselle intermedie"));
		}
	}	
	
	/* Pulisce la damiera */
	private void cleanBoard(Board d) {
		
		for (Box c : d)
			c.setPiece(null);			
	}

}