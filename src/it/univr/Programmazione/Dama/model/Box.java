package it.univr.Programmazione.Dama.model;

import it.univr.Programmazione.Dama.resources.Color;


/**
 * Implementa la casella di una damiera. *
 */
public class Box {
	
	/**
	 * Colore della casella.
	 */
	private final Color color;
	
	/**
	 * Riga.
	 */
	private int x;
	
	/**
	 * Colonna.
	 */
	private int y;
	
	/**
	 * Puntatore al pezzo.
	 */
	private Piece piece;
	

	/**
	 * Costruisce una casella.
	 * 
	 * @param color il colore della casella da creare.
	 * @param x la riga assegnata alla casella.
	 * @param y la colonna assegnata alla casella.
	 */
	public Box(Color color, int x, int y) {
		this.color = color;
		this.x = x;
		this.y = y;
	}	
	
	/**
	 * Ritorna il colore della casella.
	 * @return colore.
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Ritorna la riga della casella.
	 * 
	 * @return x.
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Imposta la riga della casella.
	 * 
	 * @param x la riga.
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Ritorna la colonna della casella.
	 * 
	 * @return y.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Imposta la colonna della casella.
	 * 
	 * @param y la colonna.
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Ritorna il pezzo a cui punta la casella.
	 * 
	 * @return il pezzo.
	 */
	public Piece getPiece() {
		return piece;
	}
	
	/**
	 * Imposta il pezzo associato alla casella.
	 * 
	 * @param piece il pezzo.
	 */
	public void setPiece(Piece piece) {
		this.piece = piece;
	}	
	
	/**
	 * Ritorna se la casella e' vuota o meno.
	 * 
	 * @return <code>true</code> se e solo se il puntatore al pezzo e' null.
	 */
	public boolean isEmpty() {
		return piece == null;
	}
	
	/**
	 * Due caselle sono uguali se hanno le stesse coordinate.
	 * 
	 * @param other la casella da confrontare.
	 * @return <code>true</code> se e solo se le caselle sono uguali.
	 */
	public boolean equals(Box other) {
		return x == other.x && y == other.y;
	}
	
	/**
	 * Ruota le coordinate della casella.
	 */
	public void rotate() {
		x = 7 - x;
		y = 7 - y;		
	}
	
}