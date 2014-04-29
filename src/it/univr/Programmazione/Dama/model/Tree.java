package it.univr.Programmazione.Dama.model;


/**
 * Implementa un albero di caselle associato ad ogni pezzo presente sulla
 * scacchiera.
 * 
 * Il nodo radice dell'albero e' la casella in cui si trova il pezzo, mentre i
 * nodi rimanenti rappresentano le caselle su cui il pezzo si puo' muovere nel
 * caso sia possibile mangiare altri pezzi.
 * 
 * Un albero composto solo dal nodo radice rappresenta un pezzo che non puo'
 * catturare nessun pezzo avversario.
 * 
 * L'albero viene costruito ruotando la damiera in modo che il pezzo si muova
 * dall'alto verso il basso e pertanto deve essere letto secondo questo verso.
 * 
 * La classe e' dichiarata astratta perche' devono essere implementati il numero
 * di rami dell'albero che variano a seconda che il pezzo sia una dama o una
 * pedina.
 */
public abstract class Tree {
	
	/**
	 * Nodo dell'albero.
	 */
	private final Box box;
	
	/**
	 * Cammino massimo presente nell'albero a partire da questo nodo.
	 * 
	 * Rappresenta il numero di pezzi che questo pezzo puo' mangiare.
	 */
	private int length;
	
	/**
	 * Puntatore al nodo padre.
	 */
	private Tree prev;
	
	
	/** 
	 * Costruisce il nodo di un albero con cammino pari a <code>length</code>.
	 * 
	 * @param box la casella del nodo.
	 * @param length la lunghezza del cammino massimo da questo nodo.
	 */
	public Tree(Box box, int length) {
		this.box = box;
		this.length = length;
	}	
	
	/**
	 * Costruisce il nodo di un albero con cammino pari a zero.
	 * 
	 * @param box la casella del nodo.
	 */
	public Tree(Box box) {
		this(box, 0);
	}	
	
	/**
	 * Ritorna la casella.
	 * 
	 * @return la casella associata a questo nodo.
	 */
	public Box getBox() {
		return box;
	}
	
	/**
	 * Ritorna la lunghezza del cammino.
	 * 
	 * @return la lunghezza del cammino massimo a partire dal nodo.
	 */
	public int getLength() {
		return length;
	}
	
	/**
	 * Imposta la lunghezza del cammino del nodo.
	 * 
	 * @param length la lunghezza del cammino massimo da questo nodo.
	 */
	public void setLength(int length) {
		this.length = length;
	}
	
	/**
	 * Ritorna il nodo precedente nel cammino radice - foglia.
	 * 
	 * @return il nodo padre.
	 */
	public Tree getPrev() {
		return prev;
	}
	
	/**
	 * Imposta il padre per il nodo attuale.
	 * 
	 * @param prev il nodo padre.
	 */
	public void setPrev(Tree prev) {
		this.prev = prev;
	}
	
	/**
	 * Controlla se il nodo e' una foglia.
	 * 
	 * @return <code>true</code> se e solo se il nodo e' una foglia.
	 */
	public abstract boolean isLeafNode();

	/**
	 * Controlla se <code>box</code> appartiene all'albero.
	 * 
	 * @param box la casella da cercare nell'albero.
	 * @return <code>true</code> se e solo se <code>box</code> appartiene
	 * all'albero.
	 */
	public abstract boolean contains(Box box);

}