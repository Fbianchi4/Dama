package it.univr.Programmazione.Dama.view.menu;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * Crea un campo dove inserire il nome del giocatore.
 * Se non viene inserito alcun nome viene utilizzato il nome "Giocatore".
 */
@SuppressWarnings("serial")
public class Player extends JPanel {
	
	/**
	 * Campo dove inserire il nome.
	 */
	private final JTextField right;
	
	/**
	 * Costruttore della classe.
	 * 
	 * Creaun pannello con la scritta "Nome del giocatore:" e un campo per
	 * l'inserimento di tale nome.
	 */
	public Player () {
		
		setLayout(new FlowLayout());
		JLabel left = new JLabel("Nome del giocatore:");
		right = new JTextField("Giocatore", 10);
		
		add(left);
		add(right);
	}

	/**
	 * Metodo per ottenere il campo di inserimento.
	 * 
	 * @return il campo di inserimento.
	 */
	public JTextField getRight() {
		
		return right;
	}
	
	/**
	 * Metodo per settare il testo nel campo di inserimento.
	 * 
	 * @param s la stringa con il testo da settare.
	 */
	public void setTextField(String s) {
		this.right.setText(s);
	}

}
