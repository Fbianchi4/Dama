package it.univr.Programmazione.Dama.view.game;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import it.univr.Programmazione.Dama.controller.AI;
import it.univr.Programmazione.Dama.model.*;
import it.univr.Programmazione.Dama.resources.Color;
import it.univr.Programmazione.Dama.view.menu.BackgroundedFrame;
import it.univr.Programmazione.Dama.view.menu.Stats;

/**
 * Rappresentazione grafica di una damiera.
 *
 */
@SuppressWarnings("serial")
public class GBoard extends JFrame {

	/**
	 * Damiera associata.
	 */
	private Board board;
	
	/**
	 * Matrice di caselle per rappresentare graficamente la damiera.
	 */
	private GBox matrix [][];
	
	/**
	 * Intero che rappresenta il set selezionato.
	 */
	private int set;
	
	/**
	 * Stringa con il nome del giocatore.
	 */
	private String player;
	
	/**
	 * Intelligenza artificiale.
	 */
	private AI ai;
	
	/**
	 * Statistiche associate.
	 */
	private Stats stats;
	
	/**
	 * Costruttore di classe.
	 * 
	 * @param b damiera associata;
	 * @param set set associato;
	 * @param s nome del giocatore;
	 * @param color colore dell'intelligenza artificiale;
	 * @param level livello di difficolta';
	 * @param st puntatore alle statistiche.
	 */
	public GBoard(Board b, int set, String s, Color color, int level, Stats st) {
		board = b;
		matrix = new GBox[8][8];
		this.set = set;
		player = s;
		ai = new AI(b, color, level); 
		stats = st;
		
		setTitle("Gioca!");
		setSize(640, 640);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		
		setLayout(new BorderLayout());
		BackgroundedFrame back = new BackgroundedFrame(
				"src/Resources/grafica/Gioco/Set" + set + "/Background.png");
		back.setLayout(new GridLayout(8, 8));
		
		initMatrix();
		
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				back.add(matrix[i][j]);
		
		this.add(back);
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent e) {}

			@Override
			public void windowClosed(WindowEvent e) {}

			@Override
			public void windowClosing(WindowEvent e) {
				if(JOptionPane.showConfirmDialog(GBoard.this,
						"Se si esce ora la partita verrà considerata persa." +
						" Sei sicuro di voler uscire?",
						"Attenzione", 0, 1) == 0)
					dispose();
			}

			@Override
			public void windowDeactivated(WindowEvent e) {}

			@Override
			public void windowDeiconified(WindowEvent e) {}

			@Override
			public void windowIconified(WindowEvent e) {}

			@Override
			public void windowOpened(WindowEvent e) {}
			
		});		

		if(color == Color.WHITE){
			ai.exec();
			refresh();
		}
		
		this.setVisible(true);
	}

	/**
	 * Metodo per ottenere il puntatore alla damiera associata.
	 * 
	 * @return la damiera associata.
	 */
	public Board getBoard() {
		return board;
	}
	
	/**
	 * Metodo per ottenere il puntatore a una casella della matrice.
	 * 
	 * @param x coordinata x della casella;
	 * @param ycoordinata y della casella;
	 * 
	 * @return la casella in posizione x, y.
	 */
	public GBox getCasellaInMatrix(int x, int y) {
		return this.matrix[x][y];
	}
	
	public int getSet () {
		return this.set;
	}	
	
	/**
	 * Metodo che setta le icone delle caselle a inizio partita.
	 */
	public void initMatrix() {

		GBox tile;
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if(board.getBox(i, j).getColor() == Color.BLACK) {
					if(board.getBox(i, j).getPiece() == null)
						tile = new GBox("src/Resources/grafica/Gioco/Set" + set +
								"/CasellaNera.png", board.getBox(i, j), this, ai);
					else if (board.getBox(i, j).getPiece().getColor() == Color.BLACK)
						tile = new GBox("src/Resources/grafica/Gioco/Set" + set +
								"/PedinaNera.png", board.getBox(i, j), this, ai);
					else
						tile = new GBox("src/Resources/grafica/Gioco/Set" + set +
								"/PedinaBianca.png", board.getBox(i, j), this, ai);
				} else 
					tile = new GBox("src/Resources/grafica/Gioco/Set" + set +
							"/CasellaBianca.png", board.getBox(i, j), this, ai);
				
				matrix[i][j] = tile;
			}
			
		}
	}

	/**
	 * Metodo che aggiorna le icone delle caselle.
	 */
	public void refresh() {
		
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++) {
				matrix[i][j].set(board.getBox(i, j));
			}
	}

	/**
	 * Metodo che gestisce la fine di una partita.
	 * 
	 * @param otherColor il colore del giocatore su cui si controlla.
	 */
	public void endGame(Color otherColor) {

		String message;
		
		if (otherColor == ai.getColor()) {
			message = "Complimenti " + this.player + ", hai vinto!";
			updateStats(0);
		} else if (otherColor == (ai.getColor() == Color.BLACK ? Color.WHITE : Color.BLACK))
			message = "Peccato " + this.player + ", hai perso!";
		else {
			message = "Partita patta!";
			updateStats(1);
		}
			
		this.setTitle("Gioco terminato");
		JOptionPane.showMessageDialog(this, message, "Partita finita", 1);
		this.dispose();
	}

	/**
	 * Metodo per aggiornare i valori delle statistiche.
	 * 
	 * @param i zero se la partita e' stata vinta oppure uno se e' patta.
	 */
	private void updateStats(int i) {

		try {
			FileWriter w = new FileWriter("src/Resources/grafica/Menu/Statistiche/Stats.txt");
			BufferedWriter b = new BufferedWriter(w);
			
			b.write(stats.getMatches() + "\n" + (i == 0 ? ((stats.getWin() + 1) + "\n" + stats.getDraw()) :
				((stats.getWin() + "\n" + (stats.getDraw() + 1)))));
			b.flush();
			b.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
