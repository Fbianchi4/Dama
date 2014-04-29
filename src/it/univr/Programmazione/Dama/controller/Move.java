package it.univr.Programmazione.Dama.controller;

import it.univr.Programmazione.Dama.model.Board;
import it.univr.Programmazione.Dama.model.Piece;
import it.univr.Programmazione.Dama.resources.Color;


/**
 * Implementa una mossa.
 * 
 * Ogni mossa ha associata una damiera sulla quale dovra' essere eseguita. *
 */
public abstract class Move {
	
	/**
	 * Damiera per eseguire la mossa.
	 */
	private final Board board;
	
	
	/**
	 * Costruisce una mossa.
	 * 
	 * @param board la damiera della mossa.
	 */
	public Move(Board board) {
		this.board = board;
	}
	
	/**
	 * Ritorna la damiera associata alla mossa.
	 * 
	 * @return la damiera della mossa.
	 */
	public Board getBoard() {
		return board;
	}
	
	/**
	 * Aggiorna il numero di pezzi sulla damiera assumendo che:
	 * se il colore del pezzo e il colore passato alla funzione coincidono, 
	 * allora assume che il pezzo e' diventato una dama, altrimenti assume che
	 * il pezzo <code>piece</code> sia stato mangiato.
	 * 
	 * @param piece il pezzo da analizzare.
	 * @param color il colore del giocatore.
	 */
	public void updateNumberOfPieces(Piece piece, Color color) {
		
		Color otherColor = piece.getColor();
		
		if (otherColor != color) {
			if (piece.isKing())
				getBoard().decreaseKings(otherColor);
			else
				getBoard().decreaseSP(otherColor);
		}
		
		else {
			getBoard().increaseKings(otherColor);
			getBoard().decreaseSP(otherColor);
		}
	}

}
