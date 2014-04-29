package it.univr.Programmazione.Dama.view.menu;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 * Pannello con i pulsanti per selezionare il livello di difficolta'.
 */
@SuppressWarnings("serial")
public class SelectLevel extends JPanel{

	/**
	 * Intero che rappresenta la difficolta'.
	 */
	private int level;
	
	/**
	 * Bottone per selezionare la difficolta' facile.
	 */
	private JButton easy;
	
	/**
	 * Bottone per selezionare la difficolta' media.
	 */
	private JButton medium;
	
	/**
	 * Bottone per selezionare la difficolta' elevata.
	 */
	private JButton hard;
	
	/**
	 * Costruttore della classe.
	 * 
	 * Imposta la difficolta' media come difficolta' di default e inizializza i
	 * pulsanti.
	 */
	public SelectLevel() {
		
		this.setLayout(new BorderLayout());
		this.level = 4;
		createNorth();
		createCenter();
		this.setOpaque(false);
	}

	
	/**
	 * Metodo per ottenere il livello di difficolta'.
	 * 
	 * @return il livello di difficolta'.
	 */
	public int getLevel() {
		return level;
	}
	
	/**
	 * Metodo che crea la parte centrale del pannello dove vengono aggiunti
	 * i pulsanti per selezionare la difficolta'.
	 */
	private void createCenter() {

		JPanel center = new JPanel();
		center.setLayout(new FlowLayout());
		
		easy = new JButton(new ImageIcon
				("src/Resources/grafica/Menu/Opzioni/Facile.png"));
		easy.addActionListener(new ActionListener () {
			
			public void actionPerformed(ActionEvent e) {
				reset(2);
			}
		});
		
		medium = new JButton(new ImageIcon
				("src/Resources/grafica/Menu/Opzioni/MedioSelected.png"));
		medium.addActionListener(new ActionListener () {
			
			public void actionPerformed(ActionEvent e) {
				reset(4);
			}
		});
		
		hard = new JButton(new ImageIcon
				("src/Resources/grafica/Menu/Opzioni/Difficile.png"));
		hard.addActionListener(new ActionListener () {
			
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(SelectLevel.this,
						"La modalita' difficile puo' richiedere molte risorse.",
						"Attenzione", 2);
				reset(6);
			}
		});
		
		easy.setContentAreaFilled(false);
		easy.setBorderPainted(false);
		easy.setFocusable(false);
		medium.setContentAreaFilled(false);
		medium.setBorderPainted(false);
		medium.setFocusable(false);
		hard.setContentAreaFilled(false);
		hard.setBorderPainted(false);
		hard.setFocusable(false);
		
		center.add(easy);
		center.add(medium);
		center.add(hard);
		
		center.setOpaque(false);
		
		this.add(center, BorderLayout.CENTER);
	}

	/**
	 * Metodo che crea la parte superiore del pannello dove aggiunge un titolo.
	 */
	private void createNorth() {

		JPanel north = new JPanel();
		north.setLayout(new FlowLayout());
		JButton seleziona = new JButton(new ImageIcon
				("src/Resources/grafica/Menu/Opzioni/Difficolta.png"));
		seleziona.setContentAreaFilled(false);
		seleziona.setBorderPainted(false);
		seleziona.setFocusable(false);
		north.add(seleziona);
		
		north.setOpaque(false);
		this.add(north, BorderLayout.NORTH);
	}
	
	/**
	 * Metodo che resetta le icone dei pulsanti e il valore di level
	 * in base al livello di difficolta' selezionato.
	 * 
	 * @param i il loivello di difficolta'.
	 */
	public void reset(int i) {
		level = i;
		
		easy.setIcon(new ImageIcon
				("src/Resources/grafica/Menu/Opzioni/Facile.png"));
		medium.setIcon(new ImageIcon
				("src/Resources/grafica/Menu/Opzioni/Medio.png"));
		hard.setIcon(new ImageIcon
				("src/Resources/grafica/Menu/Opzioni/Difficile.png"));
		
		switch (i) {
		case 2:
			easy.setIcon(new ImageIcon
					("src/Resources/grafica/Menu/Opzioni/FacileSelected.png"));
			break;
		case 4:
			medium.setIcon(new ImageIcon
					("src/Resources/grafica/Menu/Opzioni/MedioSelected.png"));
			break;
		default:
			hard.setIcon(new ImageIcon
					("src/Resources/grafica/Menu/Opzioni/DifficileSelected.png"));	
		}
	}
	
}
