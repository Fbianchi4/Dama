package it.univr.Programmazione.Dama.model;

import it.univr.Programmazione.Dama.resources.Color;


/**
 * Implementa una pedina.
 */
public class SinglePiece extends Piece {	
	
	/**
	 * Costruisce una pedina nelle condizioni iniziali di inizio partita.
	 * 
	 * @param board la damiera a cui e' associata.
	 * @param color il colore della pedina.
	 * @param box la casella in cui si trova.
	 */
	public SinglePiece(Board board, Color color, Box box) {
		super(board, color, box, new BinaryTree(box));		
	}
	
	/**
	 * Costruttore di copia.
	 * 
	 * @param other la pedina da copiare.
	 * @param board la damiera a cui associare la nuova pedina.
	 */
	public SinglePiece(SinglePiece other, Board board) {		
		super(
				board,
				other.getColor(),
				board.getBox(other.getBox().getX(), other.getBox().getY()),
				new BinaryTree(other.getTree(), board));		
	}
	
	@Override
	public BinaryTree getTree() {
		return (BinaryTree) super.getTree();		
	}
	
	@Override
	public boolean isKing() {
		return false;
	}	
	
	@Override
	public void updateTree() {
		
		resetTree();
		buildTree(getTree());
		evaluateTree(getTree());
		cleanTreeByLength(getTree());
	}
	
	@Override
	public void resetTree() {
		setTree(new BinaryTree(getBox()));		
	}
	
	/**
	 * Trasforma la pedina in una dama.
	 */
	public void becomeKing() {
		getBox().setPiece(new King(getBoard(), getColor(), getBox()));
	}
	
	/**
	 * Costruisce l'albero del pezzo inserendo tutte le caselle di destinazione
	 * in cui il pezzo puo' arrivare a seguito delle mangiate.
	 * 
	 * Funzione ricorsiva.
	 * 
	 * @param tree i nodi su cui eseguire la ricorsione.
	 */
	private void buildTree(BinaryTree tree) {
		
		Box box = tree.getBox();
		
		/* Esegue tutta la ricorsione a destra */
		if (canCaptureOnRight(box)) {
			Box next = getBoard().getBox(box.getX() + 2, box.getY() + 2);
			tree.setRight(new BinaryTree(next));
			buildTree(tree.getRight());
		}
		
		/* Esegue tutta la ricorsione a sinistra */
		if (canCaptureOnLeft(box)) {
			Box next = getBoard().getBox(box.getX() + 2, box.getY() - 2);
			tree.setLeft(new BinaryTree(next));
			buildTree(tree.getLeft());
		}		
	}	
	
	/**
	 * Controlla se il pezzo puo' mangiare spostandosi a destra.
	 * 
	 * @param box la casella di partenza del pezzo.
	 * @return <code>true</code> se e solo se il pezzo puo' mangiare.
	 */
	private boolean canCaptureOnRight(Box box) {
		
		/* Coordinate di partenza */
		int x = box.getX();
		int y = box.getY();
		
		/* Se esco dal campo, ritorna false */
		if (y > 5 || x > 5)
			return false;
		
		/* Caselle su cui effettuare i controlli */
		Box middle = getBoard().getBox(x + 1, y + 1);
		Box end = getBoard().getBox(x + 2, y + 2);
		
		// Controllo
		return !middle.isEmpty() && 
				middle.getPiece().getColor() != getColor() &&
				!middle.getPiece().isKing() &&
				end.isEmpty();		
	}
	
	/**
	 * Controlla se il pezzo puo' mangiare spostandosi a sinistra.
	 * 
	 * @param box la casella di partenza del pezzo.
	 * @return <code>true</code> se e solo se il pezzo puo' mangiare.
	 */
	private boolean canCaptureOnLeft(Box box) {
		
		/* Coordinate di partenza */
		int x = box.getX();
		int y = box.getY();
		
		/* Se esco dal campo, ritorna false */
		if (y < 2 || x > 5)
			return false;
		
		/* Caselle su cui effettuare i controlli */
		Box middle = getBoard().getBox(x + 1, y - 1);
		Box end = getBoard().getBox(x + 2, y - 2);
		
		// Controllo
		return !middle.isEmpty() &&
				middle.getPiece().getColor() != getColor() &&
				!middle.getPiece().isKing() &&
				end.isEmpty();		
	}
	
	/**
	 * Imposta per ogni nodo l'attributo <code>length</code>.
	 * 
	 * Funzione ricorsiva.
	 * 
	 * @param tree i nodi su cui eseguire la ricorsione.
	 */
	private void evaluateTree(BinaryTree tree) {
		
		/* Se il sottoalbero e' una foglia il suo cammino massimo e' zero */
		if (tree.isLeafNode())
			tree.setLength(0);
				
		/* Altrimenti pesa i suoi sottoalberi e imposta il peso del nodo
		 * attuale al massimo del peso dei sottoalberi piu' uno */
		else {
			
			BinaryTree right = tree.getRight();
			BinaryTree left = tree.getLeft();
			
			if (right != null)
				evaluateTree(right);
			if (left != null)
				evaluateTree(left);
			
			tree.setLength(1 + Piece.max(
					right == null ? -1 : right.getLength(),
					left == null ? -1 : left.getLength()));
		}
	}
	
	/**
	 * Elimina tutti i percorsi di lunghezza non massima analizzando
	 * l'attributo <code>length</code> dei nodi.
	 * 
	 * Funzione ricorsiva.
	 * 
	 * @param tree i nodi su cui eseguire la ricorsione.
	 */
	private void cleanTreeByLength(BinaryTree tree) {		
		
		/* Se la procedura arriva su una foglia termina */
		if (tree.isLeafNode())
			return;
		
		BinaryTree right = tree.getRight();
		BinaryTree left = tree.getLeft();
		
		int rightLength = right == null ? -1 : right.getLength();
		int leftLength = left == null ? -1 : left.getLength();
		
		// Peso del ramo piu' lungo (equivalente a getLength() - 1)
		int maxLength = Piece.max(rightLength, leftLength);
		
		/* Per ogni ramo:
		 * se il peso del ramo non e' il massimo per il nodo
		 * elimina il sottoalbero;
		 * altrimenti esegui ricorsivamente la funzione sul sottoalbero */		
		if (rightLength != maxLength)
			tree.setRight(null);
		else if (right != null)
			cleanTreeByLength(right);
		
		if (leftLength != maxLength)
			tree.setLeft(null);
		else if (left != null)
			cleanTreeByLength(left);		
	}
	
}