package it.univr.Programmazione.Dama.controller;

import it.univr.Programmazione.Dama.model.Board;
import it.univr.Programmazione.Dama.model.Box;
import it.univr.Programmazione.Dama.model.Piece;
import it.univr.Programmazione.Dama.model.SinglePiece;
import it.univr.Programmazione.Dama.resources.Color;


/**
 * Implementa la mossa del giocatore, eseguendo i controlli.
 */
public class PlayerMove extends Move {
	
	/**
	 * La casella di partenza.
	 */
	private Box from;
	
	/**
	 * La casella di arrivo. 
	 */
	private Box to;
	
	/**
	 * Il pezzo sulla casella di partenza.
	 */
	private Piece piece;
	
	/**
	 * Il colore del pezzo.
	 */
	private Color color;
	
	/**
	 * Se <code>true</code> indica che il giocatore deve finire la mangiata 
	 * multipla.
	 */
	private boolean again;

	
	/**
	 * Costruisce la mossa e determina se e' uno spostamento o una mangiata.
	 * 
	 * @param from la casella di partenza.
	 * @param to la casella di arrivo.
	 * @param board la damiera.
	 */
	public PlayerMove(Box from, Box to, Board board) {
		
		super(board);
		
		/* Partenza deve contenere un pezzo */
		if (from.getPiece() == null)
			throw new EmptyStartException();
		
		/* Arrivo deve essere vuota */
		if (to.getPiece() != null)
			throw new NotEmptyArrivalException();
		
		this.from = from;
		this.to = to;
		piece = from.getPiece();
		color = piece.getColor();
		again = false;
		
		/* Se muove il bianco ruota la damiera */
		if (color == Color.WHITE && !board.isRotated()) 
			board.rotate();	

		int xFrom = from.getX(), xTo = to.getX();
		int yFrom = from.getY(), yTo = to.getY();

		/* Se e' un possibile spostamento, cerca di spostare il pezzo */
		if (Math.abs(yFrom - yTo) == 1 && (xTo == xFrom + 1 ||
				(piece.isKing() && xTo == xFrom -1)))		

			tryToMove();

		/* Altrimenti cerca di eseguire una mangiata */
		else
			tryToEat();

		/* Ripristina il verso corretto della damiera */
		if (board.isRotated()) 
			board.rotate();

		/* Aggiorna gli alberi dei pezzi */
		if (!again)
			board.update();
	}	
	
	/**
	 * Ritorna l'attributo <code>again</code>.
	 * 
	 * @return l'attributo <code>again</code>.
	 */
	public boolean getAgain() {
		return again;
	}
	
	/**
	 * Sposta il pezzo ed eventualmente crea la dama.
	 */
	public void move() {
		
		to.setPiece(piece);
		piece.setBox(to);
		from.setPiece(null);
		
		if (!piece.isKing() && to.getX() == 7) {
			((SinglePiece) piece).becomeKing();
			updateNumberOfPieces(piece, color);
		}
		getBoard().increaseNMoves();
			
	}

	/**
	 * Convalida lo spostamento. 
	 */
	public void tryToMove() {
		
		/* Se nessuno e' obbligato a mangiare il pezzo puo' spostarsi */
		if (getBoard().canAnyoneCapture(color))
			throw new MustCaptureException();
		
		move();
	}

	/**
	 *  Convalida la mangiata.
	 */
	public void tryToEat() {
		
		if (!from.getPiece().getTree().contains(to))
			throw new InvalidCaptureException();
		
		int xFrom = from.getX(), xTo = to.getX();
		int yFrom = from.getY(), yTo = to.getY();
		
		if (Math.abs(xFrom - xTo) != 2 || Math.abs(yFrom - yTo) != 2)			
			throw new NotNearestBoxException();
		
		capture();
	}
	
	/**
	 * Esegue la mangiata. 
	 */
	public void capture() {
		
		to.setPiece(piece);
		piece.setBox(to);
		from.setPiece(null);
		
		int xFrom = from.getX(), xTo = to.getX();
		int yFrom = from.getY(), yTo = to.getY();
		
		Box enemyBox = getBoard().getBox((xTo + xFrom) / 2 ,
				(yTo + yFrom) / 2);
		updateNumberOfPieces(enemyBox.getPiece(), color);
		enemyBox.setPiece(null);
		
		boolean becameKing = false;
		
		if (!piece.isKing() && xTo == 7) {
			((SinglePiece) piece).becomeKing();
			becameKing = true;
			updateNumberOfPieces(piece, color);
		}
		
		getBoard().resetNMoves();
		
		// Imposta l'attributo again
		getBoard().resetAll();
		piece.updateTree();
		
		again = !(piece.getTree().isLeafNode() || becameKing);
	}	

}
