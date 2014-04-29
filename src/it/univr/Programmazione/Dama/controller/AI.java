package it.univr.Programmazione.Dama.controller;

import java.util.ArrayList;
import java.util.Random;
import it.univr.Programmazione.Dama.model.BinaryTree;
import it.univr.Programmazione.Dama.model.Board;
import it.univr.Programmazione.Dama.model.Box;
import it.univr.Programmazione.Dama.model.Piece;
import it.univr.Programmazione.Dama.model.QuadraryTree;
import it.univr.Programmazione.Dama.model.Tree;
import it.univr.Programmazione.Dama.resources.Color;


/**
 * Implementa l'intelligenza artificiale.
 */
public class AI {
	
	private static final Random r = new Random();

	/**
	 * La radice dell'albero delle mosse.
	 */
	private Path root;
	
	/**
	 * La damiera su cui eseguire la mossa.
	 */
	private final Board board;
	
	/**
	 * Il colore dell'intelligenza artificiale.
	 */
	private final Color color;
	
	/**
	 * La difficolta' che fa variare la profondita' dell'albero.
	 * - 2: facile;
	 * - 4: medio;
	 * - 6: difficile.
	 */
	private final int level;

	
	/**
	 * Costruttore dell'intelligenza artificiale.
	 * 
	 * @param board la damiera.
	 * @param color il colore dell'intelligenza artificiale.
	 * @param level il livello di difficolta'.
	 */
	public AI(Board board, Color color, int level) {
		this.board = board;
		this.color = color;	
		this.level = level;
		root = null;		
	}

	/**
	 * Esegue la mossa migliore.
	 */
	public void exec() {
		
		if (root == null) {
			root = new Path(null, board, color);
			update(root, 0, color);
		}
		else {
			
			boolean stop = false;
			
			for (Path p1 : root.getAdj()) {
				for (Path p2 : p1.getAdj())
					if (p2.getBoard().equals(board)) {
						root = p2;
						stop = true;
						break;
					}
				if (stop)
					break;
			}
			
			updateLeafNode(root, 0);			
		}
		
		evaluateTree(root, 0);
		
		int max = Integer.MIN_VALUE;
		ArrayList<Path> bestMoves = new ArrayList<Path>();
		
		for (Path p : root.getAdj())
			if (p.getValue() >= max) {
				max = p.getValue();
				bestMoves.add(p);
				if (bestMoves.size() > 1 && 
						bestMoves.get(1).getValue() > 
				        bestMoves.get(0).getValue()) {
					bestMoves.removeAll(bestMoves);
					bestMoves.add(p);
				}
			}
		
		ArrayList<Box> move;
		
		if (bestMoves.size() > 1)
			move = bestMoves.get(r.nextInt(bestMoves.size())).getMove();
		else
			move = bestMoves.get(0).getMove();
		
		new CPUMove(board, map(move, board));
		
	}	

	/**
	 * Aggiorna l'albero senza ricostruirlo interamente.
	 * 
	 * @param node nodo da cui eseguire l'update.
	 * @param n il livello di profondita' dell'iterazione.
	 */
	private void updateLeafNode(Path node, int n) {
		
		if (n + 2 == level)
			update(node, n, color);
		else
			for (Path p : node.getAdj())
				updateLeafNode(p, n + 1);
	}

	/**
	 * Valuta tutti i nodi dell'albero.
	 * 
	 * @param node il nodo da valutare.
	 * @param n il livello di profondita' dell'iterazione.
	 */
	private void evaluateTree(Path node, int n) {

		for (Path p : node.getAdj())
			evaluateTree(p, n + 1);
		
		if (n % 2 == 0)
			node.setValue(node.getMaxChildValue(n == level));
		else 
			node.setValue(node.getMinChildValue(n == level));
	}

	/**
	 * Aggiorna l'albero delle mosse.
	 * 
	 * @param node il nodo da aggiornare.
	 * @param n il livello di profondita' del nodo.
	 * @param color il colore del giocatore di cui cercare le mosse.
	 */
	private void update(Path node, int n, Color color) {

		if (n != level)
			findMoves(node, n, color);
	}

	/**
	 * Trova le mosse.
	 * 
	 * @param node il nodo da cui cercare le mosse.
	 * @param n il livello di profondita' del nodo.
	 * @param color il colore del giocatore di cui cercare le mosse.
	 */
	private void findMoves(Path node, int n, Color color) {

		Board board = node.getBoard();

		if (!board.canAnyoneCapture(color))
			collectMovements(node, n, color);
		else
			collectCaptureMovements(node, n, color);
	}
	
	/**
	 * Trova gli spostamenti.
	 * 
	 * @param node il nodo da cui cercare le mosse.
	 * @param n il livello di profondita' del nodo.
	 * @param color il colore del giocatore di cui cercare le mosse.
	 */
	private void collectMovements(Path node, int n, Color color) {

		Board board = node.getBoard();

		if (color == Color.WHITE && !board.isRotated())
			board.rotate();

		for (Box box : board) {	
			Piece piece = box.getPiece();
			if (piece != null && piece.getColor() == color)
				getMovements(piece, node, n, color);
		}

		if (board.isRotated())
			board.rotate();
	}

	/**
	 * Inserisce gli spostamenti nell'albero.
	 * 
	 * @param piece il pezzo di cui cercare i movimenti.
	 * @param node il nodo da cui cercare le mosse.
	 * @param n il livello di profondita' del nodo.
	 * @param color il colore del giocatore di cui cercare le mosse.
	 */
	private void getMovements(Piece piece, Path node, int n, Color color) {

		ArrayList<Box> move;
		Board board = node.getBoard();

		int x = piece.getBox().getX();
		int y = piece.getBox().getY();

		if (piece.isKing()) {			

			if (x < 7 && y < 7 && board.getBox(x + 1, y + 1).isEmpty()) {
				move = new ArrayList<Box>();
				move.add(piece.getBox());
				move.add(board.getBox(x + 1, y + 1));
				
				Board copy = new Board(board);
				Path newNode = new Path(map(move, copy), copy, this.color);
				node.getAdj().add(newNode);
				update(newNode, n + 1, color == Color.WHITE ?
						Color.BLACK : Color.WHITE);
			}

			if (x > 0 && y < 7 && board.getBox(x - 1, y + 1).isEmpty()) {
				move  = new ArrayList<Box>();
				move.add(piece.getBox());
				move.add(board.getBox(x - 1, y + 1));

				Board copy = new Board(board);
				Path newNode = new Path(map(move, copy), copy, this.color);
				node.getAdj().add(newNode);
				update(newNode, n + 1, color == Color.WHITE ?
						Color.BLACK : Color.WHITE);
			}

			if (x < 7 && y > 0 && board.getBox(x + 1, y - 1).isEmpty()) {
				move  = new ArrayList<Box>();
				move.add(piece.getBox());
				move.add(board.getBox(x + 1, y - 1));

				Board copy = new Board(board);
				Path newNode = new Path(map(move, copy), copy, this.color);
				node.getAdj().add(newNode);
				update(newNode, n + 1, color == Color.WHITE ?
						Color.BLACK : Color.WHITE);
			}

			if (x > 0 && y > 0 && board.getBox(x - 1, y - 1).isEmpty()) {
				move  = new ArrayList<Box>();
				move.add(piece.getBox());
				move.add(board.getBox(x - 1, y - 1));

				Board copy = new Board(board);
				Path newNode = new Path(map(move, copy), copy, this.color);
				node.getAdj().add(newNode);
				update(newNode, n + 1, color == Color.WHITE ?
						Color.BLACK : Color.WHITE);
			}			
		}

		else {

			if (x < 7 && y < 7 && board.getBox(x + 1, y + 1).isEmpty()) {
				move  = new ArrayList<Box>();
				move.add(piece.getBox());
				move.add(board.getBox(x + 1, y + 1));

				Board copy = new Board(board);
				Path newNode = new Path(map(move, copy), copy, this.color);
				node.getAdj().add(newNode);
				update(newNode, n + 1, color == Color.WHITE ?
						Color.BLACK : Color.WHITE);
			}

			if (x < 7 && y > 0 && board.getBox(x + 1, y - 1).isEmpty()) {
				move  = new ArrayList<Box>();
				move.add(piece.getBox());
				move.add(board.getBox(x + 1, y - 1));

				Board copy = new Board(board);
				Path newNode = new Path(map(move, copy), copy, this.color);
				node.getAdj().add(newNode);
				update(newNode, n + 1, color == Color.WHITE ?
						Color.BLACK : Color.WHITE);
			}			
		}
	}	

	/**
	 * Trova le mangiate.
	 * 
	 * @param node il nodo da cui cercare le mosse.
	 * @param n il livello di profondita' del nodo.
	 * @param color il colore del giocatore di cui cercare le mosse.
	 */
	private void collectCaptureMovements(Path node, int n, Color color) {

		Board board = node.getBoard();

		for (Box box : board) {
			Piece piece = box.getPiece();
			if (piece != null && piece.getColor() == color &&
					piece.getTree().getLength() > 0) 
				setParent(piece.getTree(), node, n, color);
		}
	}

	/**
	 * Decora l'albero impostando il padre per ogni nodo.
	 * 
	 * @param tree il nodo dell'albero delle mangiate del pezzo.
	 * @param node il nodo da cui cercare le mosse.
	 * @param n il livello di profondita' del nodo.
	 * @param color il colore del giocatore di cui cercare le mosse.
	 */
	private void setParent(Tree tree, Path node, int n, Color color) {

		if (tree instanceof BinaryTree) {
			BinaryTree BTree = (BinaryTree) tree;

			if (BTree.isLeafNode())
				insertCaptureMovement(BTree, node, n, color);

			if (BTree.getLeft() != null) {
				BTree.getLeft().setPrev(BTree);
				setParent(BTree.getLeft(), node, n, color);
			}

			if (BTree.getRight() != null) {
				BTree.getRight().setPrev(BTree);
				setParent(BTree.getRight(), node, n, color);
			}		
		}

		else {
			QuadraryTree QTree = (QuadraryTree) tree;

			if (QTree.isLeafNode())
				insertCaptureMovement(QTree, node, n, color);

			if (QTree.getUpLeft() != null) {
				QTree.getUpLeft().setPrev(QTree);
				setParent(QTree.getUpLeft(), node, n, color);
			}

			if (QTree.getUpRight() != null) {
				QTree.getUpRight().setPrev(QTree);
				setParent(QTree.getUpRight(), node, n, color);
			}

			if (QTree.getDownLeft() != null) {
				QTree.getDownLeft().setPrev(QTree);
				setParent(QTree.getDownLeft(), node, n, color);
			}

			if (QTree.getDownRight() != null) {
				QTree.getDownRight().setPrev(QTree);
				setParent(QTree.getDownRight(), node, n, color);
			}						
		}
	}

	/**
	 * Inserisce le mangiate nell'albero delle mosse.
	 * 
	 * @param tree il nodo dell'albero delle mangiate del pezzo.
	 * @param node il nodo da cui cercare le mosse.
	 * @param n il livello di profondita' del nodo.
	 * @param color il colore del giocatore di cui cercare le mosse.
	 */
	private void insertCaptureMovement(Tree tree, Path node, int n, Color color) {

		ArrayList<Box> move = new ArrayList<Box>();

		do {
			move.add(tree.getBox());
			tree = tree.getPrev();			
		} while (tree != null);

		reverse(move);

		Board copy = new Board(node.getBoard());
		Path newNode = new Path(map(move, copy), copy, this.color);
		node.getAdj().add(newNode);
		update(newNode, n + 1, color == Color.WHITE? Color.BLACK : Color.WHITE);
	}	

	/**
	 * Inverte l'<code>ArrayList</code>.
	 * 
	 * @param path il percorso da invertire.
	 */
	private void reverse(ArrayList<Box> path) {

		Object[] arrayBox = path.toArray();
		path.removeAll(path);

		for (int i = arrayBox.length - 1; i >= 0; i--)
			path.add((Box) arrayBox[i]);
	}
	
	/**
	 * Mappa le caselle dell'<code>ArrayList</code> sulla damiera <code>board</code>.
	 * 
	 * @param path le caselle da mappare.
	 * @param board la damiera su cui mapparle.
	 * @return le caselle mappate.
	 */
	public static ArrayList<Box> map(ArrayList<Box> path, Board board) {

		ArrayList<Box> res = new ArrayList<Box>();

		for (Box box : path)
			res.add(board.getBox(box.getX(), box.getY()));

		return res;
	}

	/**
	 * Ritorna il colore dell'intelligenza artificiale.
	 * @return il colore dell'intelligenza artficiale.
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Ritorna un intero che rappresenta il livello di difficolta'.
	 * @return il livello di difficolta'.
	 */
	public int getLevel() {
		return this.level;
	}

}
