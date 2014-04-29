package it.univr.Programmazione.Dama.view.menu;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;


/**
 * Crea il pannello dove selezionare il set di pedine da utilizzzare durante
 * il gioco. 
 */
@SuppressWarnings("serial")
public class SelectSet extends JPanel {
	
	/**
	 * Intero che rappresenta il set scelto.
	 */
	private int choose;
	
	/**
	 * Bottone con l'anteprima del set.
	 */
	private JButton set;
	
	/**
	 * Costruttore di classe.
	 * 
	 * Crea il pannello, aggiunge un titolo e i bottoni per selezionare il set.
	 * 
	 * @param title la stringa con il percorso del file immagine con il titolo.
	 */
	public SelectSet (String title){
		choose = 0;
		setLayout(new BorderLayout());
		createNorth(title);
		createCenter();
		setOpaque(false);
	}

	/**
	 * Metodo per ottenere il valore di choose.
	 * 
	 * @return il valore di choose.
	 */
	public int getChoose () {
		return choose;
	}
	
	/**
	 * Metodo per settare il valore di choose.
	 * 
	 * @param i il valore da assegnare a choose.
	 */
	public void setChoose(int i) {
		this.choose = i;
	}
	
	/**
	 * Metodo che crea la parte superiore del pannello aggiungendo un titolo.
	 * 
	 * @param title la stringa con il percorso del file immagine con il titolo.
	 */
	private void createNorth(String title) {
		JPanel north = new JPanel();
		north.setOpaque(false);
		JButton button = new JButton(new ImageIcon(title));
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setFocusable(false);
		north.add(button);
		add(north, BorderLayout.NORTH);
	}

	/**
	 * Metodo che crea la parte centrale del pannello aggiungendo l'anteprima
	 * del set selezionato e due pulsanti per selezionare un set diverso.
	 */
	private void createCenter() {
		set = new JButton(new ImageIcon
				("src/Resources/grafica/Menu/Opzioni/Set/Set0.png"));
		set.setFocusable(false);
		set.setBorderPainted(false);
		set.setContentAreaFilled(false);

		JPanel p = new JPanel();
		p.setOpaque(false);
		p.setLayout(new FlowLayout());
		
		JButton left = new JButton(new ImageIcon
				("src/Resources/grafica/Menu/Opzioni/ArrowLeft.png"));
		left.setBorderPainted(false);
		left.setContentAreaFilled(false);
		left.setFocusable(false);
		left.addActionListener(new ActionListener () {
		
			public void actionPerformed(ActionEvent e) {
				choose--;
				if(choose == -1)
					choose = 5;
				setImmagine(choose);
			}
		});
		
		JButton right = new JButton(new ImageIcon
				("src/Resources/grafica/Menu/Opzioni/ArrowRight.png"));
		right.setBorderPainted(false);
		right.setContentAreaFilled(false);
		right.setFocusable(false);
		right.addActionListener(new ActionListener () {
		
			public void actionPerformed(ActionEvent e) {
				choose++;
				if(choose == 6)
					choose = 0;
				setImmagine(choose);
			}
		});
		
		p.add(left);
		p.add(set);
		p.add(right);
		
		this.add(p);
	}	
	
	/**
	 * Metodo per settare l'anteprima del set selezionato sul pulsante.
	 * 
	 * @param i il valore del set.
	 */
	public void setImmagine(int i) {
		
		set.setIcon(new ImageIcon
				("src/Resources/grafica/Menu/Opzioni/Set/Set" +
						i + ".png"));			
	}
	
}