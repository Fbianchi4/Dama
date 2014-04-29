package it.univr.Programmazione.Dama.model;


/**
 * Implementa un albero con due rami da associare alle pedine.
 */
public class BinaryTree extends Tree {	
	
	/**
	 * Ramo sinistro.
	 */
	private BinaryTree left;
	
	/**
	 * Ramo destro.
	 */
	private BinaryTree right;
	
	
	/** 
	 * Costruisce un albero che contiene un unico nodo.
	 * 
	 * @param box la casella da associare al nodo.
	 */
	public BinaryTree(Box box) {
		
		super(box);
		
		this.left = null;
		this.right= null;
	}
	
	/**
	 * Esegue una copia di <code>tree</code> mappando le caselle del nuovo
	 * albero sulla damiera <code>board</code>.
	 * 
	 * @param tree l'albero di cui eseguire la copia.
	 * @param board la damiera associata al nuovo albero.
	 */
	public BinaryTree(BinaryTree tree, Board board) {
		
		super(board.getBox(tree.getBox().getX(), tree.getBox().getY()),
				tree.getLength());
		
		BinaryTree right = tree.right;
		BinaryTree left = tree.left;
		
		this.right = right == null ? null : new BinaryTree(right, board);
		this.left = left == null ? null : new BinaryTree(left, board);
	}
	
	/**
	 * Ritorna il sottoalbero sinistro.
	 * 
	 * @return il ramo sinistro.
	 */
	public BinaryTree getLeft() {
		return left;
	}
	
	/**
	 * Imposta il sottoalbero sinistro.
	 * 
	 * @param tree l'albero da impostare.
	 */
	public void setLeft(BinaryTree tree) {
		left = tree;
	}
	
	/**
	 * Ritorna il sottoalbero destro.
	 * 
	 * @return il ramo destro.
	 */
	public BinaryTree getRight() {
		return right;
	}	
	
	/**
	 * Imposta il sottoalbero destro.
	 * 
	 * @param tree l'albero da impostare.
	 */
	public void setRight(BinaryTree tree) {
		right = tree;
	}
	
	@Override
	public boolean isLeafNode() {
		return left == null && right == null;
	}
	
	@Override
	public boolean contains(Box box) {
		
		if (getBox().equals(box))
			return true;
		
		return (right == null ? false : right.contains(box)) ||
				(left == null ? false : left.contains(box));
 	}

}