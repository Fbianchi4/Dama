package it.univr.Programmazione.Dama.view.menu;

import it.univr.Programmazione.Dama.model.*;
import it.univr.Programmazione.Dama.resources.Color;
import it.univr.Programmazione.Dama.view.game.GBoard;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;


/**
 * Visualizza il menu' del gioco.
 * Il menu' e' formato da cinque pulsanti:
 * - Gioca;
 * - Regole;
 * - Opzioni;
 * - Statistiche;
 * - Esci.
 */
@SuppressWarnings("serial")
public class Menu extends JFrame {

	/**
	 * La stringa con il nome del giocatore.
	 */
	private String player;
	
	/**
	 * L'intero che rappresenta il set di pedine con cui si gioca.
	 */
	private int set;
	
	/**
	 * L'intero che rappresenta il colore delle pedine usate dal giocatore:
	 * zero per il bianco e uno per il nero. 
	 */
	private int color;
	
	/**
	 * L'intero che rappresenta il livello di difficolta' del gioco:
	 * due per il facile, quattro per ilmedio e sei per il difficile.
	 */
	private int level;
	
	/**
	 * Il puntatore all'oggetto che rappresenta le statistiche.
	 */
	private Stats stats;
	
	/**
	 * Il puntatore all'oggetto che rappresenta le opzioni di gioco.
	 */
	private Options options;
	
	/**
	 * Costruttore della Classe.
	 * 
	 * Setta gli attributi a valori di default, crea una finestra non
	 * ridimensionabile e aggiunge alla finestra i pulsanti.
	 */
	public Menu () {
	
		player = "Giocatore";
		set = 0;
		color = 0;
		level = 4;
		stats = new Stats(this);
		
		setResizable(false);
		setSize(300, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1));
        setTitle("Benvenuto");
        
        /* Aggiunta dei pulsanti */
        addPlay();
        addRules();
        addOptions();
        addStats();
        addExit();
        
        setVisible(true);
	}

	/**
	 * Metodo per ottenere la stringa col nome del giocatore.
	 * 
	 * @return il nome del giocatore.
	 */
	public String getPlayer() {
		return this.player;
	}
	
	/**
	 * Metodo per settare la stringa con il nome del giocatore.
	 * 
	 * @param s la stringa col nome.
	 */
	public void setPlayer (String s) {
		if(!s.equals("")) 
			this.player = s;
	}

	/**
	 * Metodo per ottenere l'intero che rappresenta il set di gioco.
	 * 
	 * @return l'intero che rappresenta il set di gioco.
	 */
	public int getSet() {
		return this.set;
	}

	/**
	 * Metodo per settare l'intero che rappresenta il set di gioco.
	 * 
	 * @param i l'intero da assegnare all'attributo set.
	 */
	public void setSet (int i) {
		this.set = i;
	}

	/**
	 * Metodo per ottenere l'intero che rappresenta il colore del giocatore.
	 * 
	 * @return il colore del giocatore.
	 */
	public int getColor() {
		return this.color;
	}
	
	/**
	 * Metodo per settare il colore del giocatore.
	 * 
	 * @param i il colore del giocatore
	 */
	public void setColor (int i) {
		this.color = i;
		
	}
	
	/**
	 * Metodo per ottenere l'intero che rappresenta il livello di difficolta'.
	 * 
	 * @return il livello di difficolta'.
	 */
	public int getLevel() {
		return this.level;
	}
	
	/**
	 * Metodo per settare il livello di difficola'.
	 * 
	 * @param i il livello di difficolta'.
	 */
	public void setLevel(int i) {
		this.level = i;
	}
	
	/**
	 * Crea il bottone per iniziare il gioco
	 * e lo aggiunge al menu'.
	 */
	private void addPlay() {
		
		JButton b = new JButton();
		ImageIcon icon =new ImageIcon
				("src/Resources/grafica/Menu/Menu/Gioca.png");

		b.setIcon(icon);
		b.setBorderPainted(true);
		b.setContentAreaFilled(false);
		
		b.addActionListener(new ActionListener () {
			
			public void actionPerformed(ActionEvent e) {
			
				/* Prima di iniziare la partita si aggiorano le stistiche
				 * aggiungendo una partita giocata
				 */
				try {
					FileWriter w = new FileWriter
							("src/Resources/grafica/Menu/Statistiche/Stats.txt");
					BufferedWriter b = new BufferedWriter(w);
					
					b.write("" + (stats.getMatches() + 1) + "\n" +
							stats.getWin() + "\n" + stats.getDraw());
					b.flush();
					b.close();
					updateStats();
					new GBoard(new Board(), getSet(), getPlayer(), color == 1 ?
							Color.WHITE : Color.BLACK, level, stats);
					
				} catch (IOException exception) {}
			}
		});
		
		this.add(b);
	}
	
	/**
	 * Crea il bottone per aprire la finestra delle regole
	 * e lo aggiunge al menu'.
	 */
	private void addRules() {

		JButton b = new JButton();
		ImageIcon icon =new ImageIcon
				("src/Resources/grafica/Menu/Menu/Regole.png");

		b.setIcon(icon);
		b.setBorderPainted(true);
		b.setContentAreaFilled(false);
		
		b.addActionListener(new ActionListener () {
			
			public void actionPerformed(ActionEvent e) {
				
				Rules rules = new Rules();
				rules.setVisible(true);
			}
		});
		
		this.add(b);
	}

	/**
	 * Crea il bottone per aprire la finestra delle opzioni
	 * e lo aggiunge il menu'. 
	 */
	private void addOptions() {
		
		JButton b = new JButton();
		ImageIcon icon =new ImageIcon
				("src/Resources/grafica/Menu/Menu/Opzioni.png");

		b.setIcon(icon);
		b.setBorderPainted(true);
		b.setContentAreaFilled(false);
		
		b.addActionListener(new ActionListener () {
			
			public void actionPerformed(ActionEvent e) {
				
				if (options == null)
					options = new Options(Menu.this);
				disableMenu();
				options.setVisible(true);
    		}
		});
		
		this.add(b);
	}
	
	/**
	 * Crea il bottone per aprire la finestra delle statistiche
	 * e lo aggiunge al menu'.
	 */
	private void addStats() {

		JButton b = new JButton();
		ImageIcon icon =new ImageIcon
				("src/Resources/grafica/Menu/Menu/Statistiche.png");

		b.setIcon(icon);
		b.setBorderPainted(true);
		b.setContentAreaFilled(false);
		
		b.addActionListener(new ActionListener () {
			
			public void actionPerformed(ActionEvent e) {
				
				stats.getMenu().setEnabled(false);
				stats.update();
				stats.setVisible(true);
			}

		});
		
		this.add(b);
	}

	/**
	 * Crea il bottone per uscire
	 * e lo aggiunge al menu'.
	 */
	private void addExit() {

		JButton b = new JButton();
		ImageIcon icon =new ImageIcon
				("src/Resources/grafica/Menu/Menu/Esci.png");

		b.setIcon(icon);
		b.setBorderPainted(true);
		b.setContentAreaFilled(false);
		
		b.addActionListener(new ActionListener () {
			
			public void actionPerformed(ActionEvent e) {

				System.exit(0);
    		}
		});
		
		this.add(b);
	}

	/**
	 * Metodo che disabilita' il menu'.
	 */
	private void disableMenu() {
		this.setEnabled(false);
		
	}

	/**
	 * Metodo che cancella il puntatore alle opzioni.
	 */
	public void deleteOptions() {
		this.options = null;
	}

	/**
	 * Metodo che crea il puntatore alle statistiche.
	 */
	private void updateStats() {
		stats = new Stats(this);
	}
	
}