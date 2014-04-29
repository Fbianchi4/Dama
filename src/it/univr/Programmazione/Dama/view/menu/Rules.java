package it.univr.Programmazione.Dama.view.menu;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * Crea una finestra con le regole del gioco della dama italiana.
 */
@SuppressWarnings("serial")
public class Rules extends JFrame {

	/**
	 * Label che contiene la stringa con le regole del gioco della dama
	 * italiana.
	 */
	private JLabel label;
	
	/**
	 * Pannello di sfondo della finestra.
	 */
	private BackgroundedFrame background;
	
	/**
	 * Costruttore della classe.
	 * 
	 * Costrusce la finestra e aggiunge un bottone per tornare al menu'. 
	 */
	public Rules () {
		
		setTitle("Regole");
		setLayout(new BorderLayout());
		setSize(700, 710);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		createBackground();
		
		readFile();		
	}
	
	/**
	 * Metodo che crea il pannello di sfondo e lo aggiunge alla finestra.
	 */
	private void createBackground() {
		background = new BackgroundedFrame
				("src/Resources/grafica/Menu/Regole/Background2.png");
		background.setLayout(new BorderLayout());
		JPanel p = new JPanel();
		p.setOpaque(false);
		background.add(p, BorderLayout.WEST);
		createNorth();
		createSouth();
		add(background);
	}

	/**
	 * Metodo che aggiunge un pulsante con il titolo alla finestra.
	 */
	private void createNorth() {
		JPanel north = new JPanel();
		north.setOpaque(false);
		JButton title = new JButton(new ImageIcon
				("src/Resources/grafica/Menu/Regole/Bottone.png"));
		title.setBorderPainted(false);
		title.setContentAreaFilled(false);
		title.setFocusable(false);
		north.add(title);
		
		background.add(north, BorderLayout.NORTH);
	}
	
	/**
	 * Metodo che crea e aggiunge alla finestra il pulsante per tornare al
	 * menu'.
	 */
	private void createSouth() {
		JButton menu = new JButton();
		ImageIcon icon =new ImageIcon
				("src/Resources/grafica/Menu/Statistiche/Menu.png");
		menu.setIcon(icon);
		menu.setBorderPainted(false);
		menu.setContentAreaFilled(false);
		menu.setFocusable(false);
		
		menu.addActionListener(new ActionListener () {
			
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		background.add(menu, BorderLayout.SOUTH);
	}
	
	/**
	 * Metodo per leggere un testo da un file .txt e salvare tale testo in una
	 * stringa.
	 */
	private void readFile() {
		File name = new File("src/Resources/grafica/Menu/Regole/Regole.txt");
		
		try {
			BufferedReader input = new BufferedReader(new FileReader(name));
			StringBuffer buffer = new StringBuffer();
			String text;
			buffer.append("<html>");
			while ((text = input.readLine()) != null)
				buffer.append(text + "<br>");
			buffer.append("</html>");
			input.close();
			
			label = new JLabel(buffer.toString());
			
			background.add(label, BorderLayout.CENTER);
		} catch (IOException ioException) {}
	}
	
}
