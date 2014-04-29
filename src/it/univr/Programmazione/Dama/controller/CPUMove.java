package it.univr.Programmazione.Dama.controller;

import java.util.ArrayList;
import it.univr.Programmazione.Dama.model.Board;
import it.univr.Programmazione.Dama.model.Box;
import it.univr.Programmazione.Dama.model.Piece;
import it.univr.Programmazione.Dama.model.SinglePiece;
import it.univr.Programmazione.Dama.resources.Color;


/**
 * Implementa una mossa senza eseguire controlli di correttezza.
 * 
 * Utilizzata principalmente per eseguire le mosse del computer.
 */
public class CPUMove extends Move {

	public CPUMove(Board board, ArrayList<Box> path) {
		
		super(board);
		
		Box first = path.get(0);
		Box second = path.get(1);
		Piece piece = first.getPiece();
		
		/* Se muove il bianco ruota la damiera */
		if (piece.getColor() == Color.WHITE && !board.isRotated())
			board.rotate();
		
		int xFirst = first.getX(), xSecond = second.getX();	
		
		/* Spostamento */
		if (xFirst + 1 == xSecond ||
				(piece.isKing() && xFirst - 1 == xSecond)) {
			
			second.setPiece(piece);
			piece.setBox(second);
			first.setPiece(null);
			
			if (!piece.isKing() && xSecond == 7) {
				((SinglePiece) piece).becomeKing();
				updateNumberOfPieces(piece, piece.getColor());
			}
			
			getBoard().increaseNMoves();
		}
		
		/* Mangiata */
		else {			
			for (int i = 0; i < path.size() - 1; i++)
				capture(path.get(i), path.get(i + 1));
			getBoard().resetNMoves();
		}		
		
		/* Ripristina la damiera */
		if (board.isRotated())
			board.rotate();
		
		/* Aggiorna gli alberi */
		board.update();
	}

	/**
	 * Esegue una mangiata.
	 * 
	 * @param from la casella di partenza.
	 * @param to la casella di arrivo.
	 */
	private void capture(Box from, Box to) {
		
		Piece piece = from.getPiece();
		int xFrom = from.getX(), xTo = to.getX();
		int yFrom = from.getY(), yTo = to.getY();
		
		to.setPiece(piece);
		piece.setBox(to);
		from.setPiece(null);
		
		Box enemyBox = getBoard().getBox((xTo + xFrom) / 2 ,
				(yTo + yFrom) / 2);
		updateNumberOfPieces(enemyBox.getPiece(), piece.getColor());
		enemyBox.setPiece(null);
		
		if (!piece.isKing() && (xTo == 7)) {
			((SinglePiece) piece).becomeKing();
			updateNumberOfPieces(piece, piece.getColor());
		}
	}

}