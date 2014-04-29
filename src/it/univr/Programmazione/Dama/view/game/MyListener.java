package it.univr.Programmazione.Dama.view.game;

import it.univr.Programmazione.Dama.controller.AI;
import it.univr.Programmazione.Dama.controller.PlayerMove;
import it.univr.Programmazione.Dama.model.Board;
import it.univr.Programmazione.Dama.model.Box;
import it.univr.Programmazione.Dama.model.Piece;
import it.univr.Programmazione.Dama.resources.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Implementazione di un ActionListener. 
 * Gestisce una damiera in base ai clic dell'utente.
 */
public class MyListener implements ActionListener {

	/**
	 * Massimo numero di mosse prima di considerare patta una partita.
	 */
	private static final int MAXMOVES = 50;
	
	/**
	 * Boolean che indica se la partita e' terminata.
	 */
	private static boolean end = false;
	
	/**
	 * Ultima casella su cui si e' cliccato.
	 */
	private static Box lastClicked;
	
	/**
	 * intero che indica se si e' cliccato sulla damiera.
	 */
	private static int clicked = 0;
	
	/**
	 * Casella associato al listener.
	 */
	private Box box;
	
	/**
	 * Damiera associata alla casella.
	 */
	private Board board;
	
	/**
	 * Damiera grafica su cui si sta giocando.
	 */
	private GBoard gboard;
	
	/**
	 * Intelligenza artificiale.
	 */
	private AI ai;
	
	/**
	 * Costruttore di classe.
	 * 
	 * @param g damiera grafica;
	 * @param b casella;
	 * @param board damiera;
	 * @param ai intelligenza artificiale.
	 */
	public MyListener(GBoard g, Box b, Board board, AI ai) {
		this.box = b;
		this.board = board;
		this.gboard = g;
		this.ai = ai;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if ((clicked == 0 && box.isEmpty()) || (!box.isEmpty() &&
				box.getPiece().getColor() == ai.getColor()))
			return;
		
		if (!gboard.getCasellaInMatrix(box.getX(), box.getY()).isEnlighted()) {	
			 if (clicked == 1)
				unlightAll();
			clicked = 1;
			lastClicked = box;
			gboard.getCasellaInMatrix(box.getX(), box.getY()).enlight();
			gboard.refresh();
		} else {
			PlayerMove pl = new PlayerMove(board.getBox(lastClicked.getX(),
					lastClicked.getY()), board.getBox(box.getX(),
							box.getY()), board);
			gboard.refresh();
			checkWin(ai.getColor() == Color.WHITE ? Color.BLACK : Color.WHITE);
			clicked = 0;
			unlightAll();
			gboard.refresh();
			
			if (!pl.getAgain() && !end) {
				gboard.setTitle("Sto pensando...");
				ai.exec();
				gboard.refresh();
				checkWin(ai.getColor());
			}
			
			setEnd(false);
		}
		
		gboard.setTitle("Gioca!");		
	}
	
	/**
	 * Metodo per settare il valore di end.
	 * 
	 * @param b il valore da assegnare a end.
	 */
	public void setEnd(boolean b) {
		end = b;
	}
	
	/**
	 * Metodo per controllare se un giocatore ha vinto.
	 * 
	 * @param color colore del giocatore su cui si esegue il controllo.
	 */
	private void checkWin(Color color) {

		Color otherColor = color == Color.BLACK ? Color.WHITE : Color.BLACK;
		
		if(board.getKings(otherColor) + board.getSP(otherColor) == 0 ||
				!hasMoves(otherColor)) {
			gboard.endGame(otherColor);
			setEnd(true);
		}
		else if(board.getNMoves() == MAXMOVES) {
			gboard.endGame(null);
			setEnd(true);
		}
		else if(!hasMoves(color) && !hasMoves(otherColor)){
			gboard.endGame(null);
			setEnd(true);
		}
	}

	/**
	 * Metodo che controlla se un giocatore puo' muovere.
	 * 
	 * @param otherColor colore del giocatore su cui si esegue il controllo.
	 * @return true se ha mosse altrimenti false.
	 */
	private boolean hasMoves(Color otherColor) {
		
		if (board.canAnyoneCapture(otherColor))
			return true;
		
		if (otherColor == Color.WHITE) {
			board.rotate();
		}
			
		for(Box box : board)
			if(!box.isEmpty() && box.getPiece().getColor() == otherColor && 
			     canMove(box.getPiece())) {
				if(board.isRotated())
					board.rotate();
				return true;
			}
				
		
		if(board.isRotated())
			board.rotate();
		
		return false;
	}

	/**
	 * Metodo che controlla se un pezzo puo' muoversi.
	 * 
	 * @param piece il pezzo su cui si esegue il controllo.
	 * 
	 * @return true se puo' muoversi altrimenti false.
	 */
	private boolean canMove(Piece piece) {

		int x = piece.getBox().getX();
		int y = piece.getBox().getY();
		
		if((x < 7 && y < 7 && board.getBox(x + 1, y + 1).isEmpty()) ||
				(x < 7 && y > 0 && board.getBox(x + 1, y - 1).isEmpty()))
			return true;
		
		if((x > 0 && y < 7 && piece.isKing() &&
				(board.getBox(x - 1, y + 1).isEmpty()) ||
				(x > 0 && y > 0 && board.getBox(x - 1, y - 1).isEmpty())))
			return true;
		
		return false;
	}

	/**
	 * Metodo che setta l'attributo enlighted a false per ogni casella nella
	 * damiera.
	 */
	private void unlightAll() {
		
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				gboard.getCasellaInMatrix(i, j).unlight();
		
	}
}
