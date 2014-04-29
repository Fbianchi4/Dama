package it.univr.Programmazione.Dama.model;


/**
 * Implementa un albero con quattro rami da associare alla dame.
 */
public class QuadraryTree extends Tree {

	/**
	 * Ramo sinistro superiore.
	 */
	private QuadraryTree upLeft;
	
	/**
	 * Ramo destro superiore.
	 */
	private QuadraryTree upRight;
	
	/**
	 * Ramo sinistro inferiore.
	 */
	private QuadraryTree downLeft;
	
	/**
	 * Ramo destro inferiore.
	 */
	private QuadraryTree downRight;
	
	/**
	 * Numero massimo di dame che il pezzo puo' mangiare sul cammino piu' lungo.
	 */
	int capturedKing;
	
	/**
	 * Numero minimo di passi che il pezzo deve effettuare per mangiare una
	 * dama avversaria sul cammino piu' lungo che massimizza il numero di dame
	 * catturate. 
	 */
	int firstCapturedKing;
	
	
	/** 
	 * Costruisce un albero che contiene un unico nodo.
	 * 
	 * @param box la casella da associare al nodo.
	 */
	public QuadraryTree(Box box) {
		
		super(box);
		
		this.upLeft = null;
		this.upRight= null;
		this.downLeft = null;
		this.downRight = null;
		
		capturedKing = 0;
		firstCapturedKing = 0;
	}
	
	/**
	 * Esegue una copia di <code>tree</code> mappando le caselle del nuovo
	 * albero sulla damiera <code>board</code>.
	 * 
	 * @param tree l'albero di cui eseguire la copia.
	 * @param board la damiera associata al nuovo albero.
	 */
	public QuadraryTree(QuadraryTree tree, Board board) {
		
		super(board.getBox(tree.getBox().getX(), tree.getBox().getY()),
				tree.getLength());
		
		capturedKing = tree.capturedKing;
		firstCapturedKing = tree.firstCapturedKing;
		
		QuadraryTree upLeft = tree.upLeft;
		QuadraryTree upRight = tree.upRight;
		QuadraryTree downLeft = tree.downLeft;
		QuadraryTree downRight = tree.downRight;
		
		this.upLeft = upLeft == null ? null :
			new QuadraryTree(upLeft, board);
		this.upRight = upRight == null ? null :
			new QuadraryTree(upRight, board);
		this.downLeft = downLeft == null ? null :
			new QuadraryTree(downLeft, board);
		this.downRight = downRight == null ? null :
			new QuadraryTree(downRight, board);		
	}
	
	/**
	 * Ritorna il sottoalbero superiore sinistro.
	 * 
	 * @return il ramo superiore sinistro.
	 */	
	public QuadraryTree getUpLeft() {
		return upLeft;
	}
	
	/**
	 * Imposta il sottoalbero superiore sinistro.
	 * 
	 * @param tree l'albero da impostare.
	 */
	public void setUpLeft(QuadraryTree tree) {
		upLeft = tree;
	}
	
	/**
	 * Ritorna il sottoalbero superiore destro.
	 * 
	 * @return il ramo superiore destro.
	 */
	public QuadraryTree getUpRight() {
		return upRight;
	}
	
	/**
	 * Imposta il sottoalbero superiore destro.
	 * 
	 * @param tree l'albero da impostare.
	 */
	public void setUpRight(QuadraryTree tree) {
		upRight = tree;
	}
	
	/**
	 * Ritorna il sottoalbero inferiore sinistro.
	 * 
	 * @return il ramo inferiore sinistro.
	 */
	public QuadraryTree getDownLeft() {
		return downLeft;
	}
	
	/**
	 * Imposta il sottoalbero inferiore sinistro.
	 * 
	 * @param tree l'albero da impostare.
	 */
	public void setDownLeft(QuadraryTree tree) {
		downLeft = tree;
	}
	
	/**
	 * Ritorna il sottoalbero inferiore destro.
	 * 
	 * @return il ramo inferiore destro.
	 */
	public QuadraryTree getDownRight() {
		return downRight;
	}
	
	/**
	 * Imposta il sottoalbero inferiore destro.
	 * 
	 * @param tree l'albero da impostare.
	 */
	public void setDownRight(QuadraryTree tree) {
		downRight = tree;
	}
	
	/**
	 * Ritorna il numero massimo di dame mangiate dal pezzo.
	 * 
	 * @return il numero massimo di dame mangiate dal pezzo sul cammino
	 * migliore.
	 */
	public int getCapturedKing() {
		return capturedKing;
	}
	
	/**
	 * Imposta il valore di <code>capturedKing</code>.
	 * 
	 * @param capturedKing il valore da impostare.
	 */
	public void setCapturedKing(int capturedKing) {
		this.capturedKing = capturedKing;
	}
	
	/**
	 * Ritorna il numero minimo di passi per mangiare una dama.
	 * 
	 * @return il numero minimo di passi per mangiare una dama sul cammino
	 * migliore.
	 */
	public int getFirstCapturedKing() {
		return firstCapturedKing;
	}
	
	/**
	 * Imposta il valore di <code>firstCapturedKing</code>.
	 * 
	 * @param firstCapturedKing il valore da impostare.
	 */
	public void setFirstCapturedKing(int firstCapturedKing) {
		this.firstCapturedKing = firstCapturedKing;
	}
	
	@Override
	public boolean isLeafNode() {
		return upLeft == null && upRight == null &&
				downLeft == null && downRight == null;
	}
	
	@Override
	public boolean contains(Box box) {
		
		if (getBox().equals(box))
			return true;
		
		return (upRight == null ? false : upRight.contains(box)) || 
				(upLeft == null ? false : upLeft.contains(box)) ||
				(downRight == null ? false : downRight.contains(box)) ||
				(downLeft == null ? false : downLeft.contains(box));						
 	}
	
}