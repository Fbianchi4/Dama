package it.univr.Programmazione.Dama.model;

import java.util.ArrayList;
import java.util.Iterator;
import it.univr.Programmazione.Dama.resources.Color;


/**
 * Implementa la scacchiera del gioco della dama e le funzioni per la sua
 * rotazione allo scopo di semplificare la realizzazione dei metodi che
 * aggiornano le strutture dati dei pezzi.
 * 
 * Nella classe sono implementati anche alcuni metodi che modificano le
 * strutture dati e che richiedono una visione globale della damiera.
 * 
 * La classe <code>Board</code> e' iterabile sulle caselle che la compongono.
 */
public class Board implements Iterable<Box> {
	
	/**
	 * Scacchiera di una dama italiana.
	 */
	private final Box gameBoard[][] = new Box[8][8];
	
	/**
	 * Il numero di pedine bianche.
	 */
	private int whiteSP;
	
	/**
	 * Il numero di pedine nere.
	 */
	private int blackSP;
	
	/**
	 * Il numero di dame bianche.
	 */
	private int whiteKings;
	
	/**
	 * Il numero di dame nere.
	 */
	private int blackKings;	
	
	/**
	 * Se il valore di <code>rotated</code> e' <code>false</code> allora la
	 * scacchiera e' nella posizione originale con i pezzi neri che si muovono
	 * dall'alto verso il basso, altrimenti se <code>rotated</code> vale
	 * <code>true</code> i pezzi bianchi sono in alto e muovono verso il basso.
	 */
	private boolean rotated;

	/**
	 * Contatore degli spostamenti consecutivi.
	 */
	private int nMoves;
	
	
	/**
	 * Costruttore della classe.
	 * 
	 * Crea una scacchiera e dispone i pezzi secondo la disposizione iniziale
	 * di inizio del gioco.
	 */
	public Board() {
		
		createEmptyBoard();
		rotated = false;		
		
		/* Pedine nero */
		for (int i = 0; i < 3; i++)
			for (int j = i % 2 == 0 ? 0 : 1; j < 8; j += 2)
				gameBoard[i][j].setPiece(
					new SinglePiece(this, Color.BLACK, gameBoard[i][j]));			
		
		/* Pedine bianco */
		for (int i = 7; i > 4; i--)
			for (int j = i % 2 == 0 ? 0 : 1; j < 8; j += 2)
				gameBoard[i][j].setPiece(
					new SinglePiece(this, Color.WHITE, gameBoard[i][j]));	
		
		whiteSP = 12;
		blackSP = 12;
		whiteKings = 0;
		blackKings = 0;	
		nMoves = 0;
	}
	
	/**
	 * Costruttore di copia.
	 * 
	 * Esegue una copia profonda della scacchiera passata come parametro.
	 * 
	 * @param other oggetto su cui eseguire la copia.
	 */
	public Board(Board other) {
	
		createEmptyBoard();
		rotated = false;
		
		// Verso originale della damiera other
		boolean otherRotated = other.rotated;
		
		/* Riporta la damiera other nel verso iniziale */
		if (other.rotated)
			other.rotate();
		
		/* Copia dei pezzi */
		for (Box[] rows : other.gameBoard)
			for (Box box : rows)
				if (!box.isEmpty()) {
					
					int x = box.getX();
					int y = box.getY();					
					Piece otherPiece = box.getPiece();				
					
					if (otherPiece.isKing())
						gameBoard[x][y].setPiece(
								new King(((King) otherPiece), this));
					else
						gameBoard[x][y].setPiece(
							new SinglePiece(((SinglePiece) otherPiece),	this));							
				}
		
		/* Ripristina lo stato dell'oggetto */
		if (other.rotated != otherRotated)
			other.rotate();
		
		/* Ruota la copia in base al verso della damiera da copiare */
		if (other.rotated != rotated)
			rotate();			
	}
	
	/** 
	 * Crea la matrice di caselle e ne imposta il colore.	  
	 */
	private void createEmptyBoard() {
		
		// Il colore dell'ultima casella della riga e' uguale al colore della
		// prima casella della nuova riga
		int changeColor = 0;
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++)
				gameBoard[i][j] = new Box(
					changeColor++ % 2 == 0 ? Color.BLACK : Color.WHITE, i, j);
			changeColor++;
		}		
	}
	
	/**
	 * Ritorna una casella della scacchiera.
	 * 	
	 * @param x riga (da 0 a 7).
	 * @param y colonna (da 0 a 7).
	 * @return la casella alla riga <code>x</code> e colonna <code>y</code>.
	 */
	public Box getBox(int x, int y) {
		return gameBoard[x][y];
	}	
	
	/**
	 * Ritorna il numero di pedine del colore specificato.
	 * 
	 * @param il colore delle pedine di cui si vuole ottenere il numero.
	 * @return il numero di pedine del colore specificato.
	 */
	public int getSP(Color color) {
		return color == Color.WHITE ? whiteSP : blackSP;
	}
	
	/**
	 * Decrementa di uno il numero di pedine del colore specificato.
	 * 
	 * @param il colore delle pedine di cui si vuole decrementare il numero.
	 */
	public void decreaseSP(Color color) {
		
		if (color == Color.WHITE)
			whiteSP--;
		else
			blackSP--;
	}
	
	/**
	 * Ritorna il numero di dame del colore specificato.
	 * 
	 * @param il colore delle dame di cui si vuole ottenere il numero.
	 * @return il numero di dame del colore specificato.
	 */
	public int getKings(Color color) {
		return color == Color.WHITE ? whiteKings : blackKings;
	}

	/**
	 * Incrementa di uno il numero di dame del colore specificato.
	 * 
	 * @param il colore delle dame di cui si vuole incrementare il numero.
	 */
	public void increaseKings(Color color) {
		
		if (color == Color.WHITE)
			whiteKings++;
		else
			blackKings++;
	}
	
	/**
	 * Decrementa di uno il numero di dame del colore specificato.
	 * 
	 * @param il colore delle dame di cui si vuole decrementare il numero.
	 */
	public void decreaseKings(Color color) {

		if (color == Color.WHITE)
			whiteKings--;
		else
			blackKings--;
	}
	
	/**
	 * Ritorna il numero di spostamenti consecutivi senza mangiate.
	 * 
	 * @return l'intero nMoves.
	 */
	public int getNMoves() {
		return this.nMoves;
	}
	
	/**
	 * Incrementa l'intero nMoves.
	 */
	public void increaseNMoves() {
		this.nMoves++;
	}
	
	/**
	 * Resetta l'intero nMoves.
	 */
	public void resetNMoves() {
		this.nMoves = 0;
	}
	
	/**
	 * Ritorna il verso della damiera.
	 * 
	 * @return <code>true</code> se e solo se il verso non e' quello originale.
	 */
	public boolean isRotated() {
		return rotated;
	}
	
	/**
	 * Ruota la scacchiera e aggiorna il campo <code>rotated</code>.
	 */
	public void rotate() {
		
		/* Scambia le caselle all'interno della damiera */
		for (int i = 0; i < 4; i++) 
			for (int j = i % 2 == 0 ? 0 : 1; j < 8; j += 2) {
				Box temp = gameBoard[i][j];
				gameBoard[i][j] = gameBoard[7 -i][7 - j];
				gameBoard[7 - i][7 - j] = temp;
			}			
				
		/* Aggiorna le coordinate di ogni casella */
		for (int i = 0; i < 8; i++)
			for (int j = i % 2 == 0 ? 0 : 1; j < 8; j += 2)
				gameBoard[i][j].rotate();
		
		rotated = !rotated;		
	}
	
	/**
	 * Aggiorna le strutture dati dei pezzi.
	 */
	public void update() {
		
		boolean startRotated;
		
		for (Box box : this) 
			if (!box.isEmpty()) {
				
				// Salva il verso della scacchiera
				startRotated = rotated;
				
				Piece piece = box.getPiece();
				
				/* Prima di aggiornare le strutture dati ruota la damiera in
				 * modo che il pezzo si muova dall'alto verso il basso */
				if ((piece.getColor() == Color.BLACK && rotated) || 
						(piece.getColor() == Color.WHITE && !rotated))
					rotate();
				
				// Aggiorna le strutture del pezzo
				piece.updateTree();
				
				/* Ripristina lo stato dell'oggetto */
				if (startRotated != rotated)
					rotate();				
			}
		
		clean();
	}	
	
	/**
	 * Pulisci gli alberi dei pezzi in base agli alberi degli altri pezzi dello
	 * stesso colore.
	 */
	private void clean() {		
		cleanByColor(Color.WHITE);
		cleanByColor(Color.BLACK);		
	}

	/**
	 * Presuppone che siano stati costruiti gli alberi dei pezzi.
	 * 
	 * Le operazioni svolte in ordine dalla funzione sono:
	 * 1) Resetta gli alberi dei pezzi che non hanno il cammino massimo;
	 * 2) Resetta gli alberi delle pedine nel caso in cui vi sono delle dame
	 *    che mangiano;
	 * 3) Resetta gli alberi dei pezzi che non mangiano il numero massimo di
	 *    dame;
	 * 4) Resetta gli alberi dei pezzi che non mangiano la prima dama nel
	 *    numero minimo di passi.
	 *    
	 * @param color il colore dei pezzi su cui eseguire la pulizia.
	 */
	private void cleanByColor(Color color) {		
		
		ArrayList<Piece> pieces = whoCanCapture(color);
		
		cleanByLength(pieces);
		
		/* Termina se non sono rimaste dame */
		if (!cleanByKing(pieces))
			return;
		
		cleanByCapturedKing(pieces);
		cleanByFirstCapturedKing(pieces);	
	}
	
	/**
	 * Crea un'<code>ArrayList</code> dei pezzi che possono mangiare.
	 * 
	 * @param color il colore dei pezzi da esaminare.
	 * @return <code>ArrayList</code> di tipo <code>Piece</code>.
	 */
	private ArrayList<Piece> whoCanCapture(Color color) {
		
		ArrayList<Piece> pieces = new ArrayList<Piece>();		
				
		for (Box box : this)			
			if (!box.isEmpty() && box.getPiece().getColor() == color)				
				pieces.add(box.getPiece());
		
		return pieces;
	}
	
	/**
	 * Resetta gli alberi che non hanno il cammino massimo e aggiorna
	 * <code>pieces</code>.
	 * 
	 * @param pieces pezzi che possono mangiare.
	 */
	private void cleanByLength(ArrayList<Piece> pieces) {
				
		// Cammino massimo
		int maxLength = 0;
		
		// Cammino del pezzo
		int length;
		
		/* Trova il massimo */
		for (Piece piece : pieces) {
			length = piece.getTree().getLength();		
			if (length > maxLength)
				maxLength = length;							
		}
		
		// Pezzi da rimuovere
		ArrayList<Piece> toRemove = new ArrayList<Piece>();
		
		/* Elimina i pezzi che non hanno il cammino massimo */
		for (Piece piece : pieces) {
			length = piece.getTree().getLength();		
			if (length < maxLength)
				toRemove.add(piece);							
		}
		
		remove(pieces, toRemove);			
	}
	
	/**
	 * Presuppone che tutti i pezzi all'interno di <code>pieces</code> abbiano
	 * i cammini di uguale lunghezza.
	 * 
	 * Resetta gli alberi delle pedine se ci sono dame che possono mangiare lo
	 * stesso numero di pezzi.
	 * 
	 * @param pieces pezzi che possono mangiare.
	 * @return <code>true</code> se e solo se sono rimaste dame.
	 */
	private boolean cleanByKing(ArrayList<Piece> pieces) {
		
		/* Verifica se ci sono sia dame che pedine */
		boolean singlePiece = false, king = false;
		
		for (Piece piece : pieces) {
			if (piece.isKing())
				king = true;
			else
				singlePiece = true;
		}
		
		/* Se non sono entrambi true termina */
		if (!singlePiece || !king)
			return king;
		
		/* Altrimenti elimina le pedine */
		
		// Pezzi da rimuovere
		ArrayList<Piece> toRemove = new ArrayList<Piece>();		
		
		for (Piece piece : pieces)
			if (!piece.isKing())
				toRemove.add(piece);
		
		remove(pieces, toRemove);
		
		return king;
	}
	
	/**
	 * Presuppone che pieces contenga pezzi con cammini di uguale lunghezza e
	 * che siano solo dame.
	 * 
	 * Resetta gli alberi delle dame che non mangiano il massimo numero di dame
	 * avversarie.
	 * 
	 * @param pieces dame che possono mangiare.
	 */
	private void cleanByCapturedKing(ArrayList<Piece> pieces) {
		
		// Numero massimo di dame mangiate dai pezzi appartenenti a pieces
		int maxCapturedKing = 0;
		
		// Numero di dame mangiate dal pezzo
		int capturedKing;
		
		/* Trova il massimo */
		for (Piece piece : pieces) {
			capturedKing = ((King) piece).getTree().getCapturedKing();
			if (capturedKing > maxCapturedKing)
				maxCapturedKing = capturedKing;
		}
		
		/* Terimna se nessun pezzo mangia dame */
		if (maxCapturedKing == 0)
			return;
		
		// Pezzi da rimuovere
		ArrayList<Piece> toRemove = new ArrayList<Piece>();	
		
		/* Elimina i pezzi che non hanno il numero massimo */
		for (Piece piece : pieces) {
			capturedKing = ((King) piece).getTree().getCapturedKing();
			if (capturedKing < maxCapturedKing)
				toRemove.add(piece);
		}
		
		remove(pieces, toRemove);			
	}
	
	/**
	 * Presuppone che pieces contenga pezzi con cammini di uguale lunghezza,
	 * che siano solo dame e che ogni dama mangi lo stesso numero di dame
	 * avversarie.
	 * 
	 * Resetta gli alberi delle dame che mangiano la prima dama avversaria con
	 * piu' passi.
	 * 
	 * @param pieces dame che possono mangiare.
	 */
	private void cleanByFirstCapturedKing(ArrayList<Piece> pieces) {
		
		/* IL NUMERO ZERO INDICA UNA DISTANZA INFINITA */
		
		// Numero minimo di passi per mangiare una dama avversaria
		int minFirstCapturedKing = 0;
		
		// Numero di passi per mangiare una dama avversaria del pezzo
		int firstCapturedKing;
		
		/* Trova il minimo */
		for (Piece piece : pieces) {
			firstCapturedKing = ((King) piece).getTree().getFirstCapturedKing();
			if (firstCapturedKing != 0 &&
					(firstCapturedKing < minFirstCapturedKing ||
					 minFirstCapturedKing == 0))
				minFirstCapturedKing = firstCapturedKing;			
		}
		
		/* Termina se nessun pezzo mangia dame */
		if (minFirstCapturedKing == 0)
			return;
		
		// Pezzi da rimuovere
		ArrayList<Piece> toRemove = new ArrayList<Piece>();	
		
		/* Elimina i pezzi che non hanno il numero minimo */
		for (Piece piece : pieces) {
			firstCapturedKing = ((King) piece).getTree().getFirstCapturedKing();
			if (firstCapturedKing == 0 ||
					firstCapturedKing > minFirstCapturedKing)
				toRemove.add(piece);
		}
		
		remove(pieces, toRemove);			
	}
	
	/**
	 * Rimuove i pezzi contenuti in <code>toRemove</code> da <code>pieces</code>.
	 * 
	 * @param pieces lista di pezzi da modificare.
	 * @param toRemove pezzi da rimuovere.
	 */
	private void remove(ArrayList<Piece> pieces, ArrayList<Piece> toRemove) {
		for (Piece piece : toRemove) {
			piece.resetTree();
			pieces.remove(piece);
		}			
	}
	
	@Override
	public Iterator<Box> iterator() {
		
		return new Iterator<Box>() {
			
			int index = 0;

			@Override
			public boolean hasNext() {
				return index < 64;
			}

			@Override
			public Box next() {
				Box res = getBox(index / 8, index - (index / 8) * 8);
				index++;
				return res;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();				
			}
			
		};		
	}
	
	/**
	 *  Ritorna true se qualche pedina del colore specificato puo' mangiare.
	 *  
	 *  @param color il colore dei pezzi su cui eseguire i controlli.
	 *  @return <code>true</code> se e solo se qualcuno puo' mangiare.
	 */
	public boolean canAnyoneCapture(Color colore) {
		
		for (Box casella : this)
			if (!casella.isEmpty() && casella.getPiece().getColor() == colore &&
				!casella.getPiece().getTree().isLeafNode())
				return true;
		
		return false;
	}
	
	/**
	 * Due damiere sono uguali se e solo se i pezzi sono distribuiti sulla
	 * scacchiera nello stesso modo.
	 * 
	 * @param board la damiera con cui confrontre <code>this</code>.
	 * @return <code>true</code> se e solo se le due damiere sono uguali.
	 */	
	public boolean equals(Board board) {
		
		int x, y;
		Piece thisPiece, otherPiece;
		
		for (Box box : board) {
			
			x = box.getX();
			y = box.getY();
			thisPiece = gameBoard[x][y].getPiece();
			otherPiece = box.getPiece();
			
			if (thisPiece == null && otherPiece == null)
				continue;
			
			if ((thisPiece == null && otherPiece != null) ||
					thisPiece != null && otherPiece == null)
				return false;
			
			if (!(thisPiece.getColor() == otherPiece.getColor()) ||
					!(thisPiece.isKing() == otherPiece.isKing()))
				return false;
		}
		
		return true;
	}
	
	/**
	 * Resetta gli alberi di tutti i pezzi.
	 */
	public void resetAll() {

		for (Box box : this)
			if (!box.isEmpty())
				box.getPiece().resetTree();
	}
	
	/**
	 * Stampa la damiera.
	 * 
	 * [ ] indica una casella bianca;
	 * { } indica una casella nera vuota;
	 * {n} indica una casella nera con una pedina nera;
	 * {N} indica una casella nera con una dama nera;
	 * {b} indica una casella nera con una pedina bianca;
	 * {B} indica una casella nera con una dama bianca.
	 */
	@Override
	public String toString() {

		String s = "  0  1  2  3  4  5  6  7\n0 ";
		int i = 1;
		
		for (Box[] rows : gameBoard) {	
			for (Box box : rows) {
				Piece piece = box.getPiece();

				if (box.getColor() == Color.WHITE)
					s += "[ ]";
				else if (box.isEmpty())
					s += "{ }";
				else if (piece.getColor() == Color.BLACK && piece.isKing())
					s += "{N}";
				else if (piece.getColor() == Color.BLACK)
					s += "{n}";
				else if (piece.getColor() == Color.WHITE && piece.isKing())
					s += "{B}";
				else
					s += "{b}";							
			}
			s += "\n" + i++;
		}

		return s.substring(0, s.length() - 2);		
	}
	
}