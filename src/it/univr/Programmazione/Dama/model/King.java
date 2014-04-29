package it.univr.Programmazione.Dama.model;

import it.univr.Programmazione.Dama.resources.Color;


/**
 * Implementa una dama. 
 */
public class King extends Piece {
	
	/**
	 * Costruisce una dama.
	 * 
	 * @param board la damiera a cui e' associata.
	 * @param color il colore della pedina.
	 * @param box la casella in cui si trova.
	 */
	public King(Board board, Color color, Box box) {
		super(board, color, box, new QuadraryTree(box));		
	}
	
	/**
	 * Costruttore di copia.
	 * 
	 * @param other la dama da copiare.
	 * @param board la damiera a cui associare la nuova dama.
	 */
	public King(King other, Board board) {		
		super(
				board,
				other.getColor(),
				board.getBox(other.getBox().getX(), other.getBox().getY()),
				new QuadraryTree(other.getTree(), board));		
	}
	
	@Override
	public QuadraryTree getTree() {
		return (QuadraryTree) super.getTree();		
	}
	
	@Override
	public boolean isKing() {
		return true;
	}
	
	@Override
	public void updateTree() {
		
		// Reset dell'albero
		resetTree();

		QuadraryTree tree = getTree();
		
		/* Costruisce un albero completo ed elimina i rami con i cammini non
		 * massimi e successivamente elimina i cammini che catturano un numero
		 * di dame non massimo */
		buildTree(getBoard(), tree);		
		evaluateTree(tree);
		cleanTreeByLength(tree);
		cleanTreeByCapturedKing(tree);
		
		/* Pesa l'albero per eliminare i cammini dove la dama viene mangiata
		 * con un numero non minimo di passi */
		evaluateTreeByFirstCapturedKing(tree);
		cleanTreeByFirstCapturedKing();
	}

	@Override
	public void resetTree() {
		setTree(new QuadraryTree(getBox()));
	}	

	/**
	 * Costruisce l'albero del pezzo inserendo tutte le caselle di destinazione
	 * in cui il pezzo puo' arrivare a seguito delle mangiate.
	 * 
	 * Funzione ricorsiva.
	 * 
	 * @param board la damiera da copiare per eseguire le simulazioni allo
	 * scopo di evitare cicli.
	 * @param tree i nodi su cui eseguire la ricorsione.
	 */
	private void buildTree(Board board, QuadraryTree tree) {
		
		Box box = tree.getBox();
		
		// Crea una copia su cui lavorare ad ogni iterazione
		Board boardCopy = new Board(board);
		
		// Aggiorna il puntatore sulla casella della nuova damiera
		Box boxCopy = boardCopy.getBox(box.getX(), box.getY());
		
		/* Esegue tutta la ricorsione verso le x maggiori e le y maggiori */
		if (canEatOnUpRight(boardCopy, boxCopy)) {
			
			Box next = boardCopy.getBox(
					boxCopy.getX() - 2, boxCopy.getY() + 2);
			
			tree.setUpRight(new QuadraryTree(
					getBoard().getBox(next.getX(), next.getY())));
			
			Piece captured = captureSimulation(boardCopy, boxCopy, next);
			buildTree(boardCopy, tree.getUpRight());
			reverseCapture(boardCopy, boxCopy, next, captured);			
		}		
		
		/* Esegue tutta la ricorsione verso le x maggiori e le y minori */
		if (canEatOnUpLeft(boardCopy, boxCopy)) {
			
			Box next = boardCopy.getBox(
					boxCopy.getX() - 2, boxCopy.getY() - 2);
			
			tree.setUpLeft(new QuadraryTree(
					getBoard().getBox(next.getX(), next.getY())));
			
			Piece captured = captureSimulation(boardCopy, boxCopy, next);
			buildTree(boardCopy, tree.getUpLeft());	
			reverseCapture(boardCopy, boxCopy, next, captured);
		}		
		
		/* Esegue tutta la ricorsione verso le x minori e le y maggiori */
		if (canEatOnDownRight(boardCopy, boxCopy)) {
			
			Box next = boardCopy.getBox(
					boxCopy.getX() + 2, boxCopy.getY() + 2);
			
			tree.setDownRight(new QuadraryTree(
					getBoard().getBox(next.getX(), next.getY())));
			
			Piece captured = captureSimulation(boardCopy, boxCopy, next);
			buildTree(boardCopy, tree.getDownRight());
			reverseCapture(boardCopy, boxCopy, next, captured);
		}		
		
		/* Esegue tutta la ricorsione verso le x minori e le y minori */
		if (canEatOnDownLeft(boardCopy, boxCopy)) {
			
			Box next = boardCopy.getBox(
					boxCopy.getX() + 2, boxCopy.getY() - 2);
			
			tree.setDownLeft(new QuadraryTree(
					getBoard().getBox(next.getX(), next.getY())));
			
			Piece captured = captureSimulation(boardCopy, boxCopy, next);
			buildTree(boardCopy, tree.getDownLeft());
			reverseCapture(boardCopy, boxCopy, next, captured);
		}			
	}

	/**
	 * Controlla se il pezzo puo' mangiare spostandosi a destra in avanti.
	 * 
	 * @param board la damiera su cui eseguire la verifica.
	 * @param box la casella di partenza del pezzo.
	 * @return <code>true</code> se e solo se il pezzo puo' mangiare.
	 */
	private boolean canEatOnDownRight(Board damiera, Box box) {
		
		/* Coordinate di partenza */
		int x = box.getX();
		int y = box.getY();
		
		/* Se esco dal campo, ritorna false */
		if (y > 5 || x > 5)
			return false;
		
		/* Caselle su cui effettuare i controlli */
		Box middle = damiera.getBox(x + 1, y + 1);
		Box end = damiera.getBox(x + 2, y + 2);
		
		// Controllo
		return !middle.isEmpty() &&
				middle.getPiece().getColor() != getColor() &&
				end.isEmpty();		
	}
	
	/**
	 * Controlla se il pezzo puo' mangiare spostandosi a sinistra in avanti.
	 * 
	 * @param board la damiera su cui eseguire la verifica.
	 * @param box la casella di partenza del pezzo.
	 * @return <code>true</code> se e solo se il pezzo puo' mangiare.
	 */
	private boolean canEatOnDownLeft(Board damiera, Box box) {
		
		/* Coordinate di partenza */
		int x = box.getX();
		int y = box.getY();
		
		/* Se esco dal campo, ritorna false */
		if (y < 2 || x > 5)
			return false;
		
		/* Caselle su cui effettuare i controlli */
		Box middle = damiera.getBox(x + 1, y - 1);
		Box end = damiera.getBox(x + 2, y - 2);
		
		// Controllo
		return !middle.isEmpty() &&
				middle.getPiece().getColor() != getColor() &&
				end.isEmpty();		
	}
	
	/**
	 * Controlla se il pezzo puo' mangiare spostandosi a destra all'indietro.
	 * 
	 * @param board la damiera su cui eseguire la verifica.
	 * @param box la casella di partenza del pezzo.
	 * @return <code>true</code> se e solo se il pezzo puo' mangiare.
	 */
	private boolean canEatOnUpRight(Board damiera, Box box) {
		
		/* Coordinate di partenza */
		int x = box.getX();
		int y = box.getY();
		
		/* Se esco dal campo, ritorna false */
		if (y > 5 || x < 2)
			return false;
		
		/* Caselle su cui effettuare i controlli */
		Box middle = damiera.getBox(x - 1, y + 1);
		Box end = damiera.getBox(x - 2, y + 2);
		
		// Controllo
		return !middle.isEmpty() &&
				middle.getPiece().getColor() != getColor() &&
				end.isEmpty();		
	}
	
	/**
	 * Controlla se il pezzo puo' mangiare spostandosi a sinistra all'indietro.
	 * 
	 * @param board la damiera su cui eseguire la verifica.
	 * @param box la casella di partenza del pezzo.
	 * @return <code>true</code> se e solo se il pezzo puo' mangiare.
	 */
	private boolean canEatOnUpLeft(Board damiera, Box box) {
		
		/* Coordinate di partenza */
		int x = box.getX();
		int y = box.getY();
		
		/* Se esco dal campo, ritorna false */
		if (y < 2 || x < 2)
			return false;
		
		/* Caselle su cui effettuare i controlli */
		Box middle = damiera.getBox(x - 1, y - 1);
		Box end = damiera.getBox(x - 2, y - 2);
		
		// Controllo
		return !middle.isEmpty() &&
				middle.getPiece().getColor() != getColor() &&
				end.isEmpty();		
	}
	
	/**
	 * Esegue una mangiata sulla damiera <code>board</code>.
	 * 
	 * Chiamata nella costruzione degli alberi delle dame per evitare che si
	 * verifichino cicli dovuti alla possibilita' delle dame di catturare i
	 * pezzi in entrambe le direzioni.
	 * 
	 * @param board la damiera su cui eseguire la mangiata.
	 * @param from la casella di partenza del pezzo che mangia.
	 * @param to la casella di arrivo.
	 * @return il pezzo mangiato per poterlo riposizionare in futuro.
	 */
	private Piece captureSimulation(Board board, Box from, Box to) {
		
		Piece piece = from.getPiece();
		
		piece.setBox(to);
		to.setPiece(piece);
		from.setPiece(null);

		/* Salva e rimuovi il pezzo mangiato */
		Piece captured = board.getBox((from.getX() + to.getX()) / 2,
				(from.getY() + to.getY()) / 2).getPiece();
		
		board.getBox((from.getX() + to.getX()) / 2,
				(from.getY() + to.getY()) / 2).setPiece(null);
		
		return captured;		
	}
	
	/**
	 * Inversa della funzione <code>captureSimulation</code>.
	 * 
	 * Reinserisce il pezzo catturato nella damiera e riposiziona i pezzi.
	 * 
	 * @param board la damiera su cui eseguire le operazioni.
	 * @param from la casella da cui il pezzo aveva eseguito la mangiata.
	 * @param to la casella in cui si trova il pezzo.
	 * @param captured il pezzo catturato in precedenza.
	 */
	private void reverseCapture(Board board, Box from, Box to,
			Piece captured) {
		
		Piece piece = to.getPiece();
		
		piece.setBox(from);
		from.setPiece(piece);
		to.setPiece(null);
		
		/* Inserisci il pezzo mangiato in precedenza */
		board.getBox((from.getX() + to.getX()) / 2,
				(from.getY() + to.getY()) / 2).setPiece(captured);
	}
	
	/**
	 * Imposta per ogni nodo gli attributi <code>length</code> e
	 * <code>capturedKing</code> per ogni nodo dell'albero.
	 * 
	 * Funzione ricorsiva.
	 * 
	 * @param tree i nodi su cui eseguire la ricorsione.
	 */
	private void evaluateTree(QuadraryTree tree) {

		/* Se il sottoalbero e' una foglia il suo peso e' zero cosi' come il
		 * massimo numero di dame catturabili a partire dal nodo */
		if (tree.isLeafNode()) {
			tree.setLength(0);
			tree.setCapturedKing(0);
			return;
		}
		
		/* Altrimenti pesa i suoi sottoalberi e imposta il cammino del nodo
		 * attuale al massimo del cammino dei suoi sottoalberi piu' uno.
		 *
		 * Il numero di dame che possono essere mangiate da quel nodo e'
		 * il massimo delle dame mangiabili sui sottorami piu' uno se la
		 * mangiata dal nodo attuale al sottoalbero mangia una dama. */
		QuadraryTree upRight = tree.getUpRight();
		QuadraryTree upLeft = tree.getUpLeft();
		QuadraryTree downLeft = tree.getDownLeft();
		QuadraryTree downRight = tree.getDownRight();

		if (upRight != null)
			evaluateTree(upRight);

		if (upLeft != null)
			evaluateTree(upLeft);

		if (downLeft != null)
			evaluateTree(downLeft);

		if (downRight != null)
			evaluateTree(downRight);

		// Imposta l'attributo length
		tree.setLength(1 + Piece.max(
				upRight == null ? -1 : upRight.getLength(),
				upLeft == null ? -1 : upLeft.getLength(),
				downRight == null ? -1 : downRight.getLength(),
				downLeft == null ? -1 : downLeft.getLength()));

		Box thisCasella = tree.getBox();

		// Imposta l'attributo capturedKing
		tree.setCapturedKing(Piece.max(
				(upRight == null ? - 1 : (evaluate(
						thisCasella,
						upRight.getBox()) +
						upRight.getCapturedKing())),
				(upLeft == null ? - 1 : (evaluate(
						thisCasella,
						upLeft.getBox()) +
						upLeft.getCapturedKing())),
				(downRight == null ? - 1 : (evaluate(
						thisCasella,
						downRight.getBox()) +
						downRight.getCapturedKing())),
				(downLeft == null ? - 1 : (evaluate(
						thisCasella,
						downLeft.getBox()) +
						downLeft.getCapturedKing()))
				));
	}

	/**
	 * Elimina tutti i percorsi di lunghezza non massima analizzando
	 * l'attributo <code>length</code> dei nodi.
	 * 
	 * Funzione ricorsiva.
	 * 
	 * @param tree i nodi su cui eseguire la ricorsione.
	 */
	private void cleanTreeByLength(QuadraryTree tree) {

		/* Se l'albero e' composto da un unico nodo termina */
		if (tree.isLeafNode())
			return;

		QuadraryTree upRight = tree.getUpRight();
		QuadraryTree upLeft = tree.getUpLeft();
		QuadraryTree downLeft = tree.getDownLeft();
		QuadraryTree downRight = tree.getDownRight();

		int upRightLength = upRight == null ? -1 : upRight.getLength();
		int upLeftLength = upLeft == null ? -1 : upLeft.getLength();
		int downRightLength = downRight == null ? -1 : downRight.getLength();
		int downLeftLength = downLeft == null ? -1 : downLeft.getLength();

		// Peso di ogni ramo (equivale a getLength() - 1)
		int maxWeight = Piece.max(upRightLength, upLeftLength,
				downRightLength, downLeftLength);

		/* Per ogni ramo:
		 * se il peso del ramo non e' il massimo per il nodo elimina il
		 * sottoalbero;
		 * altrimenti esegui ricorsivamente la funzione sul sottoalbero */
		if (upRightLength != maxWeight)
			tree.setUpRight(null);
		else if (upRight != null)
			cleanTreeByLength(upRight);

		if (downLeftLength != maxWeight)
			tree.setDownLeft(null);
		else if (downLeft != null)
			cleanTreeByLength(downLeft);

		if (upLeftLength != maxWeight)
			tree.setUpLeft(null);
		else if (upLeft != null)
			cleanTreeByLength(upLeft);

		if (downRightLength != maxWeight)
			tree.setDownRight(null);
		else if (downRight != null)
			cleanTreeByLength(downRight);		
	}
	
	/**
	 * Elimina tutti i percorsi che contengono un numero di dame non massimo
	 * analizzando l'attributo <code>capturedKing</code> dei nodi.
	 * 
	 * Funzione ricorsiva.
	 * 
	 * @param tree i nodi su cui eseguire la ricorsione.
	 */
	private void cleanTreeByCapturedKing(QuadraryTree tree) {

		/* Se l'albero e' composto da un unico nodo termina */
		if (tree.isLeafNode())
			return;

		QuadraryTree upRight = tree.getUpRight();
		QuadraryTree upLeft = tree.getUpLeft();
		QuadraryTree downLeft = tree.getDownLeft();
		QuadraryTree downRight = tree.getDownRight();

		Box box = tree.getBox();
		
		int upRightWeight = upRight == null ? -1 :
			(evaluate(box, upRight.getBox()) +
					upRight.getCapturedKing());
		int upLeftWeight = upLeft == null ? -1 :
			(evaluate(box, upLeft.getBox()) +
					upLeft.getCapturedKing());
		int downRightWeight = downRight == null ? -1 :
			(evaluate(box, downRight.getBox()) +
					downRight.getCapturedKing());
		int downLeftWeight = downLeft == null ? -1 :
			(evaluate(box, downLeft.getBox()) +
					downLeft.getCapturedKing());

		// Peso di ogni ramo
		int maxWeight = Piece.max(upRightWeight, upLeftWeight,
				downRightWeight, downLeftWeight);


		/* Per ogni ramo:
		 * se il peso del ramo non e' il massimo per il nodo elimina il sottoalbero;
		 * altrimenti esegui ricorsivamente la funzione sul sottoalbero
		 */
		if (upRightWeight != maxWeight)
			tree.setUpRight(null);
		else if (upRight != null)
			cleanTreeByCapturedKing(tree.getUpRight());

		if (downLeftWeight != maxWeight)
			tree.setDownLeft(null);
		else if (downLeft != null)
			cleanTreeByCapturedKing(tree.getDownLeft());

		if (upLeftWeight != maxWeight)
			tree.setUpLeft(null);
		else if (upLeft != null)
			cleanTreeByCapturedKing(tree.getUpLeft());

		if (downRightWeight != maxWeight)
			tree.setDownRight(null);
		else if (downRight != null)
			cleanTreeByCapturedKing(tree.getDownRight());		
	}
	
	
	/**
	 * Imposta l'attributo del nodo <code>firstCapturedKing</code>
	 * 
	 * Funzione ricorsiva.
	 * 
	 * @param tree il nodo su cui eseguire la ricorsione.
	 */
	private void evaluateTreeByFirstCapturedKing(QuadraryTree tree) {
		
		/* Se il nodo e' una foglia non mangia nessuna dama */
		if (tree.isLeafNode()) {
			tree.setFirstCapturedKing(0);
			return;
		}
		
		QuadraryTree upRight = tree.getUpRight();
		QuadraryTree upLeft = tree.getUpLeft();
		QuadraryTree downLeft = tree.getDownLeft();
		QuadraryTree downRight = tree.getDownRight();
		
		Box box = tree.getBox();
		
		/* Decora i sottoalberi */
		if (upRight != null)
			evaluateTreeByFirstCapturedKing(upRight);
		
		if (upLeft != null)
			evaluateTreeByFirstCapturedKing(upLeft);
		
		if (downLeft != null)
			evaluateTreeByFirstCapturedKing(downLeft);
		
		if (downRight != null)
			evaluateTreeByFirstCapturedKing(downRight);
		
		/* Calcola il peso dei sottoalberi seguendo il seguente algoritmo:
		 * 
		 * se il sottoalbero non esiste (null) imposta a infinito (zero) il
		 * peso di quel sottoalbero;
		 * 
		 * se il sottoalbero esiste e nel passo tra il nodo attuale e il
		 * sottoalbero e' presente una dama, allora imposta a uno il peso
		 * relativo a quel sottoalbero;
		 * 
		 * se il sottoalbero esiste e nel passo tra il nodo attuale e il
		 * sottoalbero non e' presente una dama e nel percorso del sottoalbero
		 * non viene mangiata nessuna dama, imposta il peso relativo a quel
		 * sottoalbero a 0;
		 * 
		 * altrimenti il peso relativo a quel sottoalbero e' 1 + (il numero
		 * minimo di passi che si devono eseguire all'interno del sottoalbero
		 * per mangiare una dama). */
		int upRightWeight = upRight == null ? 0 : 
			(evaluate(box, upRight.getBox()) == 1 ? 1 :
				1 + (upRight.getFirstCapturedKing() == 0 ? -1 :
					upRight.getFirstCapturedKing()));
		
		int upLeftWeight = upLeft == null ? 0 :
			(evaluate(box, upLeft.getBox()) == 1 ? 1 :
				1 + (upLeft.getFirstCapturedKing() == 0 ? -1 :
					upLeft.getFirstCapturedKing()));
		
		int downRightWeight = downRight == null ? 0 :
			(evaluate(box, downRight.getBox()) == 1 ? 1 :
				1 + (downRight.getFirstCapturedKing() == 0 ? -1 :
					downRight.getFirstCapturedKing()));
		
		int downLeftWeight = downLeft == null ? 0 :
			(evaluate(box, downLeft.getBox()) == 1 ? 1 :
				1 + (downLeft.getFirstCapturedKing() == 0 ? -1 :
					downLeft.getFirstCapturedKing()));
		
		tree.setFirstCapturedKing(Piece.min(upRightWeight, upLeftWeight,
				downRightWeight, downLeftWeight));		
	}
	
	/**
	 * Elimina i rami che mangiano la prima dama in un numero non minimo di
	 * passi.
	 */
	private void cleanTreeByFirstCapturedKing() {
		
		QuadraryTree tree = getTree();
		
		/* Se il nodo non mangia nessuna dama non c'e' niente da eliminare */
		if (tree.getFirstCapturedKing() == 0)
			return;
		
		QuadraryTree upRight = tree.getUpRight();
		QuadraryTree upLeft = tree.getUpLeft();
		QuadraryTree downLeft = tree.getDownLeft();
		QuadraryTree downRight = tree.getDownRight();
		
		Box box = tree.getBox();
		
		/* Se il nodo dichiara di mangiare una dama con un singolo passo,
		 * allora elimina tutti i nodi per cui spostandosi dal nodo attuale al
		 * sottoalbero non e' presente una dama */
		if (tree.getFirstCapturedKing() == 1) {
			
			if (upRight != null && evaluate(box, upRight.getBox()) == 0)
				tree.setUpRight(null);
			
			if (upLeft != null && evaluate(box, upLeft.getBox()) == 0)
				tree.setUpLeft(null);
			
			if (downRight != null && evaluate(box, downRight.getBox()) == 0)
				tree.setDownRight(null);
			
			if (downLeft != null &&	evaluate(box, downLeft.getBox()) == 0)
				tree.setDownLeft(null);
			
			return;
		}
		
		/* Se il nodo dichiara di mangiare una dama con un numero di passi
		 * maggiore di uno, allora elimina i sottoalberi che distano pi� di uno
		 * dal peso dichiarato dal nodo */
		if (upRight != null && tree.getFirstCapturedKing() - 1 !=
				upRight.getFirstCapturedKing())
			tree.setUpRight(null);
		
		if (upLeft != null && tree.getFirstCapturedKing() - 1 !=
				upLeft.getFirstCapturedKing())
			tree.setUpLeft(null);
		
		if (downRight != null && tree.getFirstCapturedKing() - 1 !=
				downRight.getFirstCapturedKing())
			tree.setDownRight(null);
		
		if (downLeft != null && tree.getFirstCapturedKing() - 1 !=
				downLeft.getFirstCapturedKing())
			tree.setDownLeft(null);
	}
	
	/**
	 * Controlla casella situata tra le due caselle passate come parametri.
	 * 
	 * @param from la casella di partenza.
	 * @param to la casella di arrivo.
	 * @return <code>1</code> se e solo se tra <code>from</code> e
	 * <code>to</code> e' presente una dama, altrimenti ritorna <code>0</code>.
	 */
	private int evaluate(Box from, Box to) {
		return getBoard().getBox(
				(from.getX() + to.getX()) / 2,
				(from.getY() + to.getY()) / 2)
				.getPiece().isKing() == true ? 1 : 0;
	}
	
}