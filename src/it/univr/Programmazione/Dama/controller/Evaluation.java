package it.univr.Programmazione.Dama.controller;

import java.util.ArrayList;
import it.univr.Programmazione.Dama.model.Board;
import it.univr.Programmazione.Dama.model.Box;
import it.univr.Programmazione.Dama.model.Piece;
import it.univr.Programmazione.Dama.resources.Color;


/**
 * Implementa la classe che assegna un punteggio ad una damiera. 
 */
public class Evaluation {
	
	/**
	 * La damiera da valutare.
	 */
	private final Board board;
	
	/**
	 * Il colore del giocatore.
	 */
	private final Color color;
	
	/**
	 * Il valore assegnato ai pezzi presenti sulla damiera.
	 */
	private int piecesValue;
	
	/**
	 * Il valore assegnato ai pezzi singoli.
	 * 
	 * Dipende dalla sicurezza del pezzo e dal numero della riga in cui si
	 * trova.
	 */
	private int SPValue;
	
	/**
	 * Il valore assegnato alle dame.
	 * 
	 * Dipende dalla vicinanza a pezzi vulnerabili.
	 */
	private int KValue;
	
	
	/**
	 * Costruisce la valutazione.
	 * 
	 * @param board la damiera da valutare.
	 * @param color il colore di chi valuta.
	 */
	public Evaluation(Board board, Color color) {
		
		this.board = board;
		this.color = color;
		
		evaluate();
	}
	
	/**
	 * Ritorna il valore della damiera pesando i singoli valori.
	 * 
	 * @return l'intero che rappresenta il valore.
	 */
	public int getValue() {
		return piecesValue * 1000 + SPValue + 100 * KValue;
	}
	
	/**
	 * Valuta ogni pezzo sulla damiera.
	 */
	public void evaluate() {

		for (Box box : board)
			if (!box.isEmpty())
				evaluatePiece(box.getPiece());	
	}
	
	/**
	 * Valuta il pezzo e richiama le funzioni di valutazioni a seconda che sia
	 * un pezzo singolo o una dama.
	 * 
	 * @param piece il pezzo da valutare.
	 */
	private void evaluatePiece(Piece piece) {
		
		int pieceValue = 1;
		
		if (piece.isKing())
			pieceValue++;
		
		if (piece.getColor() == color) {
			piecesValue += pieceValue;
		}
		else
			piecesValue -= pieceValue;
		
		if (!piece.isKing())
			SPValue(piece);
		else
			KValue(piece);
	}

	/**
	 * Valuta una dama.
	 * 
	 * @param piece la dama da valutare.
	 */
	private void KValue(Piece piece) {

		ArrayList<Piece> singlePieces = new ArrayList<Piece>();
		ArrayList<Piece> kings = new ArrayList<Piece>();
		
		for(Box box : board) { 
			if(!box.isEmpty() && 
					box.getPiece().getColor() != piece.getColor() &&
					!box.getPiece().isKing() &&
					Math.abs(safety(box.getPiece())) != 2)
				
				singlePieces.add(box.getPiece());
			
			else if(!box.isEmpty() &&
					box.getPiece().getColor() != piece.getColor() &&
					box.getPiece().isKing())
				
				kings.add(box.getPiece());
		}
		
		if(kings.size() == 0 && singlePieces.size() == 0)
			return;
		
		Box nearest;
		
		if(singlePieces.size() != 0)
			nearest = findNearest(singlePieces, piece.getBox());
		else
			nearest = findNearest(kings, piece.getBox());
		
		int result = 7 - dst(nearest, piece.getBox());
		
		KValue += piece.getColor() == color ? result : (-1 * result);
	}

	/**
	 * Ritorna la casella con il pezzo vulnerabile piu' vicino.
	 * 
	 * @param pieces l'<code>ArrayList</code> dei pezzi vulnerabili tra cui
	 * cercare il piu' vicino.
	 * @param target la casella di riferimento.
	 * @return la casella piu' vicina a <code>target</code>.
	 */
	private Box findNearest(ArrayList<Piece> pieces, Box target) {

		int min = Integer.MAX_VALUE;
		int temp;
		Box result = null;
		
		for(Piece piece : pieces)
			if((temp = dst(piece.getBox(), target)) < min) {
				min = temp;
				result = piece.getBox();
			}
		
		return result;
	}

	/**
	 * Funzione che calcola la distanza in termini del numero minimo di
	 * spostamenti necessari.
	 * 
	 * @param box la casella di partenza.
	 * @param target la casella di arrivo.
	 * @return l'intero che rappresenta la distanza.
	 */
	private int dst(Box box, Box target) {
		
		return Math.max(Math.abs(box.getX() - target.getX()),
				Math.abs(box.getY() - target.getY()));
	}

	/**
	 * Valuta il pezzo singolo.
	 * 
	 * @param piece la pedina da valutare.
	 */
	private void SPValue(Piece piece) {

		int row = piece.getColor() == Color.BLACK ? piece.getBox().getX() : 7 - piece.getBox().getX();
		int result = safety(piece) * (1 + row);
		
		SPValue += piece.getColor() == color ? result : (-1 * result);		
	}

	/**
	 * Funzione che valuta la sicurezza del pezzo.
	 * 
	 * @param piece la pedina da valutare.
	 * @return <code>0</code> se insicuro, <code>1</code> se sicuro solamente
	 * da un lato, <code>2</code> se è sicuro.
	 */
	private int safety(Piece piece) {
		
		int x = piece.getBox().getX();
		int y = piece.getBox().getY();
		
		if (x == 0 || x == 7 || y == 0 || y == 7) 
			return piece.getColor() == color ? 2 : -2;
		
		boolean uL, uR, dL, dR;
		uL = uR = dL = dR = false;
		
		if (!board.getBox(x + 1, y + 1).isEmpty() &&
				board.getBox(x + 1, y + 1).getPiece().getColor() == piece.getColor())
			dR = true;
		
		if (!board.getBox(x + 1, y - 1).isEmpty() &&
				board.getBox(x + 1, y - 1).getPiece().getColor() == piece.getColor())
			dL = true;
		
		if (!board.getBox(x - 1, y + 1).isEmpty() &&
				board.getBox(x - 1, y + 1).getPiece().getColor() == piece.getColor())
			uR = true;
		
		if (!board.getBox(x - 1, y - 1).isEmpty() &&
				board.getBox(x - 1, y - 1).getPiece().getColor() == piece.getColor())
			uL = true;
		
		int result;
		
		if (uR && uL || dR && dL || dR && uR || dL && uL)
			result = 2;
		else if (uR || uL || dR || dL)
			result = 1;
		else
			result = 0;
		
		return piece.getColor() == color ? result : -1 * result;
	}
	
}
