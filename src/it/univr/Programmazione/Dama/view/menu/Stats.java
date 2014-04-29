package it.univr.Programmazione.Dama.view.menu;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * La finestra con le statistiche delle partite.
 */
@SuppressWarnings("serial")
public class Stats extends JFrame{

	/**
	 * Numero di partite giocate.
	 */
	private int matches;
	
	/**
	 * Numero di partite vinte.
	 */
	private int win;
	
	/**
	 * Numero di partite patte.
	 */
	private int draw;
	
	/**
	 * Testo con i valori delle partite giocate, vinte e patte.
	 */
	private String text;
	
	/**
	 * Label con il testo con i valori delle partite giocate, vinte e patte.
	 */
	private JLabel label;
	
	/**
	 * Puntatore al menu'.
	 */
	private Menu menu;
	
	/**
	 * Pannello di sfondo.
	 */
	private BackgroundedFrame background;
	
	/**
	 * Costruttore della classe.
	 * 
	 * Crea una finestra con i valori delle partite giocate, vinte, patte e
	 * perse.
	 * 
	 * @param m il puntatore al menu'.
	 */
	public Stats (Menu m) {
		
		setLayout(new BorderLayout());
		setTitle("Statistiche");
		setSize(350, 200);
		setResizable(false);
		setLocationRelativeTo(null);
		menu = m;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		addWindowListener(new WindowListener() {

	        public void windowClosing(WindowEvent e) {}

	        public void windowOpened(WindowEvent e) {}

	        public void windowClosed(WindowEvent e) {
	        	menu.setEnabled(true);
	        }

	        public void windowIconified(WindowEvent e) {}

	        public void windowDeiconified(WindowEvent e) {}

	        public void windowActivated(WindowEvent e) {}

	        public void windowDeactivated(WindowEvent e) {}

	    });
		
		background = new BackgroundedFrame("src/Resources/grafica/Menu/Background.png");
		background.setLayout(new BorderLayout());
		add(background, BorderLayout.CENTER);
		label = new JLabel();
		
		createWest();
		createSouth();
		readFile();
		
		background.add(label, BorderLayout.CENTER);
	}
	
	/**
	 * Metodo per otttenere il valore delle partite giocate.
	 * 
	 * @return il valore delle partite giocate.
	 */
	public int getMatches() {
		return this.matches;
	}
	
	/**
	 * Metodo per otttenere il valore delle partite vinte.
	 * 
	 * @return il valore delle partite vinte.
	 */
	public int getWin() {
		return this.win;
	}
	
	/**
	 * Metodo per otttenere il valore delle partite patte.
	 * 
	 * @return il valore delle partite patte.
	 */
	public int getDraw() {
		return this.draw;
	}

	/**
	 * Metodo per otttenere il puntatore al menu'.
	 * 
	 * @return il puntatore al menu'.
	 */
	public Menu getMenu() {
		return this.menu;
	}
	
	/**
	 * Crea un pannello vuoto nella parte sinistra della finestra.
	 */
	private void createWest() {
		JPanel p = new JPanel();
		p.setOpaque(false);
		background.add(p, BorderLayout.WEST);
	}
	
	/**
	 * Crea la parte inferiore della finestra e aggiunge i pulsanti per tornare
	 * al menu' e resettare i valori delle statistiche.
	 */
	private void createSouth() {
		JButton back = new JButton();
		ImageIcon icon =new ImageIcon
				("src/Resources/grafica/Menu/Statistiche/Menu.png");
		back.setIcon(icon);
		back.setBorderPainted(false);
		back.setContentAreaFilled(false);
		back.setFocusable(false);
		
		back.addActionListener(new ActionListener () {
			
			public void actionPerformed(ActionEvent e) {
				closing();
			}

		});
		
		JButton reset = new JButton();
		ImageIcon icon2 =new ImageIcon("src/Resources/grafica/Menu/Statistiche/Reset.png");
		reset.setIcon(icon2);
		reset.setBorderPainted(false);
		reset.setContentAreaFilled(false);
		reset.setFocusable(false);
		
		reset.addActionListener(new ActionListener () {
			
			public void actionPerformed(ActionEvent e) {
				reset();
			}

		});
		
		
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout());
		southPanel.add(reset);
		southPanel.add(back);
		southPanel.setOpaque(false);
		
		background.add(southPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Metodo che legge da file i valori delle partite giocate, vinte e patte.
	 * Le partite perse vengono calcolate per differenza.
	 */
	private void readFile() {
		File name = new File
				("src/Resources/grafica/Menu/Statistiche/Stats.txt");
		
		try {
			BufferedReader input = new BufferedReader(new FileReader(name));
			StringBuffer buffer = new StringBuffer();
			
			while ((text = input.readLine()) != null) 
				buffer.append(text + " ");
			input.close();

			text = buffer.toString();
			matches = takeValue(this.text);
			win = takeValue(this.text);
			draw = takeValue(this.text);
			
			String s = "<html>Partite giocate: " + matches +
					"<br><br>Partite vinte: " + win + 
					"<br><br>Partite patte: " + draw +
					"<br><br>Partite perse: " +(matches - win - draw) +
					"</html>";
			
			label.setText(s);
			
		} catch (IOException ioException) {}
	}

	/**
	 * Metodo che recupera il primo valore numerico dalla stringa dei risultati
	 * e poi rimuove il valore recuperato dalla stringa.
	 * 
	 * @param la stringa da cui recuoperare i valori.
	 */
	private int takeValue(String text) {
		int i = 0;
		int res = 0;
		
		while(Character.isDigit(text.charAt(i)))
			i++;
		
		this.text = text.substring(i + 1, text.length());
		res =Integer.parseInt(text.substring(0, i));
		
		return res;
	}

	/**
	 * Metodo che chiude la finestra delle statistiche e riabilità quella del 
	 * menuche era stata disabilitata alla pressione del pulsante statistiche.
	 */
	private void closing() {
		this.setVisible(false);
		menu.setEnabled(true);
	}

	/**
	 * Metodo che resetta i valori delle statistiche
	 */
	private void reset() {
		try {
			FileWriter w = new FileWriter
					("src/Resources/grafica/Menu/Statistiche/Stats.txt");
			BufferedWriter b = new BufferedWriter(w);
			
			b.write("0\n0\n0");
			b.flush();
			b.close();
			
		} catch (IOException e) {}
		
		this.remove(label);
		readFile();

		background.add(label, BorderLayout.CENTER);
	}

	/**
	 * Metodo che aggiorna i valori delle statistiche.
	 */
	public void update () {
		this.remove(label);
		readFile();

		background.add(label, BorderLayout.CENTER);
	}
	
}
