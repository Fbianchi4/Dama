package it.univr.Programmazione.Dama.view.game;

import it.univr.Programmazione.Dama.controller.AI;
import it.univr.Programmazione.Dama.model.BinaryTree;
import it.univr.Programmazione.Dama.model.Board;
import it.univr.Programmazione.Dama.model.Box;
import it.univr.Programmazione.Dama.model.QuadraryTree;
import it.univr.Programmazione.Dama.model.Tree;
import it.univr.Programmazione.Dama.resources.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;


/**
 * Classe che rappresenta una casella nella rappresentazione grafica della
 * damiera.
 */
@SuppressWarnings("serial")
public class GBox extends JButton {

	/**
	 * La casella del package model associata.
	 */
	private Box box;
	
	/**
	 * La damiera associata.
	 */
	private Board board;
	
	/**
	 * La rappresentazione grafica della damiera associata
	 */
	private GBoard gboard;
	
	/**
	 * Attributo per controllare se la casella rientra nell'albero delle
	 * mangiate o negli spostamenti di un pezzo.
	 */
	private boolean enlighted = false;
	
	/**
	 * Costruttore della classe.
	 * 
	 * @param s percorso dell'immagine da caricare sul pulsante;
	 * @param b casella associata;
	 * @param g rappresentazione grafica della damiera associata;
	 * @param ai intelligenza artificiale.
	 */
	public GBox (String s, Box b, GBoard g, AI ai) {
		
		box = b;
		board = g.getBoard();
		gboard = g;
		setIcon(new ImageIcon(s));
		
		setBorderPainted(false);
		setContentAreaFilled(false);
		setFocusable(false);
		
		addActionListener(new MyListener(gboard, box, board, ai));
	}
	
	/**
	 * Metodo per ottenere il valore di enlighted.
	 * 
	 * @return il valore di enlighted.
	 */
	public boolean isEnlighted () {
		
		return this.enlighted;
	}
	
	/**
	 * Metodo per settare il valore di enlighted.
	 * 
	 * @param b il valore da assegnare a enlighted.
	 */
	public void setEnlighted (boolean b) {
		
		this.enlighted = b;
	}
	
	/**
	 * Metodo che setta l'icona della rappresentazione grafica della casella in
	 * base ai valori di una casella.
	 * 
	 * @param b la casella da cui recuperare i valori in base a cui settare
	 * l'icona.
	 */
	public void set(Box b) {
		
		if(b.getColor() == Color.BLACK) {
			if(b.getPiece() == null && !this.enlighted)
				this.setIcon(new ImageIcon("src/Resources/grafica/Gioco/Set" +
						gboard.getSet() + "/CasellaNera.png"));
			else if(b.getPiece() == null && this.enlighted)
				this.setIcon(new ImageIcon("src/Resources/grafica/Gioco/Set" +
						gboard.getSet() + "/CasellaNeraIll.png"));
			else if (b.getPiece().getColor() == Color.BLACK &&
						!b.getPiece().isKing())
				this.setIcon(new ImageIcon("src/Resources/grafica/Gioco/Set" +
						gboard.getSet() + "/PedinaNera.png"));
			else if (b.getPiece().getColor() == Color.BLACK &&
						b.getPiece().isKing())
				this.setIcon(new ImageIcon("src/Resources/grafica/Gioco/Set" +
						gboard.getSet() + "/DamaNera.png"));
			else if (b.getPiece().getColor() == Color.WHITE &&
						!b.getPiece().isKing())
				this.setIcon(new ImageIcon("src/Resources/grafica/Gioco/Set" +
						gboard.getSet() + "/PedinaBianca.png"));
			else
				this.setIcon(new ImageIcon("src/Resources/grafica/Gioco/Set" +
						gboard.getSet() + "/DamaBianca.png"));
		} else 
			this.setIcon(new ImageIcon("src/Resources/grafica/Gioco/Set" +
					gboard.getSet() + "/CasellaBianca.png"));
	}

	/**
	 * Metodo che setta il valore di enligh se la casella rientra nell'albero
	 * delle mangiate o negli spostamenti di un pezzo.
	 */
	public void enlight() {
		
		if (box.isEmpty())
			return;
		if (board.canAnyoneCapture(box.getPiece().getColor()))
			enlightTree(box.getPiece().getTree());
		else
			enlightMove();
	}

	/**
	 * Metodo che illumina le caselle su cui un pezzo puo' spostarsi.
	 */
	private void enlightMove() {
		int x = box.getX();
		int y = box.getY();
		Color c = box.getPiece().getColor();
		int color = (box.getPiece().getColor() == Color.BLACK ? 1 : -1);
		
		if(box.getPiece().isKing()) {
			if(x < 7 && y < 7 && board.getBox(x + 1, y + 1).isEmpty())
				gboard.getCasellaInMatrix(x + 1, y + 1).setEnlighted(true);
			if(x < 7 && y > 0 && board.getBox(x + 1, y - 1).isEmpty())
				gboard.getCasellaInMatrix(x + 1, y - 1).setEnlighted(true);
			if(x  > 0 && y < 7 && board.getBox(x - 1, y + 1).isEmpty())
				gboard.getCasellaInMatrix(x - 1, y + 1).setEnlighted(true);
			if(x > 0 && y > 0 && board.getBox(x - 1, y - 1).isEmpty())
				gboard.getCasellaInMatrix(x - 1, y - 1).setEnlighted(true);
		} else {
			if(((c == Color.BLACK && x < 7) || (c == Color.WHITE && x > 0)) &&
					y < 7 && board.getBox(x + color, y + 1).isEmpty())
				gboard.getCasellaInMatrix(x + color, y + 1).setEnlighted(true);
			if(((c == Color.BLACK && x < 7) || (c == Color.WHITE && x > 0)) &&
					y > 0 && board.getBox(x + color, y - 1).isEmpty())
				gboard.getCasellaInMatrix(x + color, y - 1).setEnlighted(true);
		}
	}

	/**
	 * Metodo che illumina le caselle dell'albero delle mangiate di un pezzo.
	 * 
	 * @param tree l'albero di cui illuminare le caselle.
	 */
	private void enlightTree(Tree tree) {
		
		if (tree instanceof BinaryTree) {
			BinaryTree bTree = (BinaryTree) tree;
			
			if(bTree.getLeft() != null) {
				Box b = bTree.getLeft().getBox();
				gboard.getCasellaInMatrix(b.getX(),
						b.getY()).setEnlighted(true);
			}
			if(bTree.getRight() != null) {
				Box b = bTree.getRight().getBox();
				gboard.getCasellaInMatrix(b.getX(),
						b.getY()).setEnlighted(true);
			}
		}
		else {
			QuadraryTree qTree = (QuadraryTree) tree;

			if(qTree.getUpLeft() != null) {
				Box b = qTree.getUpLeft().getBox();
				gboard.getCasellaInMatrix(b.getX(),
						b.getY()).setEnlighted(true);
			}
			if(qTree.getUpRight() != null) {
				Box b = qTree.getUpRight().getBox();
				gboard.getCasellaInMatrix(b.getX(),
						b.getY()).setEnlighted(true);
			}
			if(qTree.getDownLeft() != null) {
				Box b = qTree.getDownLeft().getBox();
				gboard.getCasellaInMatrix(b.getX(),
						b.getY()).setEnlighted(true);
			}
			if(qTree.getDownRight() != null) {
				Box b = qTree.getDownRight().getBox();
				gboard.getCasellaInMatrix(b.getX(),
						b.getY()).setEnlighted(true);
			}
		}
	}

	/**
	 * Metodo che setta enlighted a false.
	 */
	public void unlight() {
		
		this.enlighted = false;
		this.set(box);
	}
	
}
