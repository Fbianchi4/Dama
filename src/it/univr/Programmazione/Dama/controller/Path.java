package it.univr.Programmazione.Dama.controller;

import it.univr.Programmazione.Dama.model.Board;
import it.univr.Programmazione.Dama.model.Box;
import it.univr.Programmazione.Dama.resources.Color;
import java.util.ArrayList;


/**
 * Implementa un nodo dell'albero delle mosse.
 */
public class Path {
	
	/**
	 * Lista di adiacenza del nodo.
	 */
	private final ArrayList<Path> adj;
	
	/**
	 * La mossa che rappresenta il nodo.
	 */
	private final ArrayList<Box> move;
	
	/**
	 * Il valore attribuito alla damiera associata al nodo.
	 */
	private int value;
	
	/**
	 * La damiera associata al nodo.
	 */
	private final Board board;
	
	/**
	 * Il colore di chi esegue la mossa.
	 */
	private final Color color;

	
	/**
	 * Costruzione del nodo.
	 * 
	 * @param move il percorso di caselle.
	 * @param board la damiera.
	 * @param color il colore.
	 */
	public Path(ArrayList<Box> move, Board board, Color color) {

		adj = new ArrayList<Path>();
		this.move = move;
		value = 0;
		this.board = board;
		this.color = color;
		
		if (move != null)
			new CPUMove(board, move);
	}

	
	/**
	 * Ritorna la damiera.
	 * 
	 * @return la damiera.
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * Ritorna la lista di adiacenza.
	 * 
	 * @return la lista di adiacenza.
	 */
	public ArrayList<Path> getAdj() {
		return adj;
	}	

	/**
	 * Ritorna l'intero value.
	 * @return l'intero value.
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Ritorna il percorso di caselle.
	 * @return il percorso di caselle.
	 */
	public ArrayList<Box> getMove() {
		return move;
	}

	/**
	 * Imposta il valore del nodo.
	 */
	public void evaluate() {
		value = (new Evaluation(board,color)).getValue();
	}

	/**
	 * Ritorna il valore massimo tra i nodi della sua lista di adiacenza.
	 * 
	 * @param isLastLevel <code>true</code> se e solo se e' l'ultimo livello 
	 * dell'albero. 
	 * @return il valore massimo.
	 */
	public int getMaxChildValue(boolean isLastLevel) {

		if (adj.size() == 0) {
			if (isLastLevel)
				this.evaluate();
			else
				this.value = Integer.MIN_VALUE;

			return value;
		}			

		int max = Integer.MIN_VALUE;

		for (Path p : adj)
			if (p.getValue() > max)
				max = p.getValue();
		
		return max;
	}

	/**
	 * Ritorna il valore minimo tra i nodi della sua lista di adiacenza.
	 * 
	 * @param isLastLevel <code>true</code> se e solo se e' l'ultimo livello 
	 * dell'albero. 
	 * @return il valore minimo.
	 */
	public int getMinChildValue(boolean isLastLevel) {

		if (adj.size() == 0) {
			if (isLastLevel)
				this.evaluate();
			else
				this.value = Integer.MAX_VALUE;

			return value;
		}

		int min = Integer.MAX_VALUE;

		for (Path p : adj)
			if (p.getValue() < min)
				min = p.getValue();
		
		return min;
	}

	/**
	 * Imposta l'intero value.
	 * 
	 * @param value il valore da impostare.
	 */
	public void setValue(int value) {
		this.value = value;
	}	

}