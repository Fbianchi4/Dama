package it.univr.Programmazione.Dama.model;

import it.univr.Programmazione.Dama.resources.Color;


/**
 * Implementa un pezzo e i metodi per aggiornare le sue strutture dati.
 * 
 * Ad ogni pezzo e' associata una casella e un albero di caselle che rappresenta
 * i possibili spostamenti del pezzo a seguito di una o piu' mangiate.
 * 
 * I metodi astratti vengono implementati nelle sottoclassi e si differenziano
 * in base alla struttura dell'albero associata al pezzo.
 */
public abstract class Piece {

	/**
	 * Damiera a cui appartiene la casella del pezzo.
	 */
	private final Board board;
	
	/**
	 * Colore del pezzo.
	 */
	private final Color color;
	
	/**
	 * Casella in cui si trova il pezzo.
	 */
	private Box box;
		
	/** 
	 * Albero delle mangiate del pezzo.
	 * Inizializzato nel costruttore delle sottoclassi con un BinaryTree o un
	 * QuadraryTree.
	 */
	private Tree tree;
	
	
	/**
	 * Costruisce un pezzo.
	 * 
	 * @param board la damiera a cui appartiene il pezzo.
	 * @param color il colore del pezzo.
	 * @param box la casella dove si trova il pezzo.
	 * @param tree l'albero delle mangiate.
	 */
	public Piece(Board board, Color color, Box box, Tree tree) {
		this.board = board;
		this.color = color;
		this.box = box;
		this.tree = tree;		
	}
	
	/**
	 * Ritorna la scacchiera a cui appartiene il pezzo.
	 * 
	 * @return la scacchiera.
	 */
	public Board getBoard() {
		return board;
	}
	
	/**
	 * Ritorna il colore del pezzo.
	 * 
	 * @return il colore.
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Ritorna la casella in cui si trova il pezzo.
	 * 
	 * @return la casella.
	 */
	public Box getBox() {
		return box;
	}
	
	/**
	 * Sposta il pezzo.
	 * 
	 * @param box la nuova casella.
	 */
	public void setBox(Box box) {
		this.box = box;
	}
	
	/**
	 * Ritorna l'albero delle mangiate del pezzo.
	 * 
	 * @return l'albero.
	 */
	public Tree getTree() {
		return tree;
	}
	
	/** 
	 * Imposta l'albero delle mangiate del pezzo.
	 * 
	 * @param tree il nuovo albero.
	 */
	public void setTree(Tree tree) {
		this.tree = tree;
	}
	
	/** 
	 * Ritorna true se il pezzo e' una dama.
	 * Implementato nelle sottoclassi.
	 */
	public abstract boolean isKing();
	
	/**
	 * Costruisce l'albero delle mangiate contenente solo i percorsi ottimi.
	 * Implementato nelle sottoclassi.
	 */
	public abstract void updateTree();	
	
	/** 
	 * Distrugge l'albero delle mangiate mantenendo solo la radice.
	 * Implementata nelle sottoclassi.	 
	 */
	public abstract void resetTree();	

	/**
	 * Restituisce il massimo dei valori naturali.
	 */
	public static int max(int ... weights) {
		
		int max = -1;
		
		for (int n : weights)
			if (n > max)
				max = n;
		
		return max;
	}
	
	/**
	 * Restituisce il minimo dei valori naturali assumendo zero come infinito.
	 */
	public static int min(int ... weights) {
		
		int min = 0;
		
		for (int n : weights)
			if (n != 0 && (n < min || min == 0))
				min = n;
			
		return min;				
	}

}