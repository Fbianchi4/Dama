package it.univr.Programmazione.Dama.view.menu;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * Visualizza la finestra delle opzioni dove selezionare il nome del giocatore,
 * il set di gioco, il livello di difficolta' e il colore delle pedine con cui
 * giocare.
 */
@SuppressWarnings("serial")
public class Options extends JFrame {
	
	/**
	 * Puntatore all'oggetto che permette di selezionare il set di gioco.
	 */
	private SelectSet set;
	
	/**
	 * Puntatore all'oggetto che permette di inserire il nome del giocatore.
	 */
	private Player player;
	
	/**
	 * Puntatore all'oggetto che permette di selezionare il livello di
	 * difficolta'.
	 */
	private SelectLevel selectLevel;
	
	/**
	 * Intero che rappresenta il colore selezionato.
	 */
	private int color;
	
	/**
	 * Puntatore al menu'.
	 */
	private Menu menu;
	
	/**
	 * Attributo per controllare se si e' premuto sul pulsante di conferma.
	 */
	private boolean confirmed;
	
	/**
	 * Pulsante per selexionare il colore bianco.
	 */
	private JButton white;
	
	/**
	 * Pulsante per selezionare il colore nero.
	 */
	private JButton black;
	
	/**
	 * Costruttore della classe.
	 * 
	 * Costruisce la finestra delle opzioni.
	 * 
	 * @param m il puntatore al menu'.
	 */
	public Options(Menu m) {
		
		confirmed = false;
		white = new JButton();
		black = new JButton();
		color = 0;
		menu = m;
		
		setSize(500, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setTitle("Opzioni");
        setResizable(false);

        BackgroundedFrame b = new BackgroundedFrame("src/Resources/grafica/Menu/Background.png");
        b.setLayout(new BorderLayout());
        
        b.add(createNorth(), BorderLayout.NORTH);
        b.add(createSouth(), BorderLayout.SOUTH);
        b.add(createCenter(), BorderLayout.CENTER);
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        addWindowListener(new WindowListener() {

	        public void windowClosing(WindowEvent e) {}

	        public void windowOpened(WindowEvent e) {}

	        public void windowClosed(WindowEvent e) {
	        	if(!confirmed)
					menu.deleteOptions();
				else
					reset();
	        	
	        	menu.setEnabled(true);
	        }

	        public void windowIconified(WindowEvent e) {}

	        public void windowDeiconified(WindowEvent e) {}

	        public void windowActivated(WindowEvent e) {}

	        public void windowDeactivated(WindowEvent e) {}

	    });
        
        this.add(b);
	}

	/**
	 * Metodo che setta il colore delle pedine del giocatore.
	 *  
	 * @param i intero che rappresenta il colore scelto.
	 */
	private void setColor(int i) {
		color = i;
	}
	
	/**
	 * Metodo per aggiungere l'oggetto che permette di selezionare il nome
	 * del giocatore.
	 * 
	 * @return il puntatore all''oggetto che permette di selezionare il nome
	 * del giocatore.
	 */
	private Player createNorth() {
		player = new Player();
		player.setOpaque(false);
		return player;
	
	}
	
	/**
	 * Metodo per aggiungere alla finestra l'oggetto che permette di
	 * selezionare il set di gioco.
	 * 
	 * @return un pannello con l'oggetto che permette di selezionare
	 * il set di gioco.
	 */
	private JPanel createCenter() {
		JPanel p = new JPanel();
		p.setOpaque(false);

		set = new SelectSet("src/Resources/grafica/Menu/Opzioni/Set.png");
		
        p.add(set);
        
        return p;
	}
	
	/**
	 * Metodo per aggiungere l'oggetto per selezionare il colore con cui
	 * giocare e i pulsanti di "Conferma" e "Annulla". 
	 * 
	 * @return il pannello da aggiungere alla parte inferiore della
	 * finestra.
	 */
	private JPanel createSouth() {
		selectLevel = new SelectLevel();
		JPanel southPanel = new JPanel();
		
		southPanel.setLayout(new BorderLayout());
		southPanel.add(addConfirm(), BorderLayout.SOUTH);
		southPanel.add(selectColor(), BorderLayout.CENTER);
		southPanel.add(selectLevel, BorderLayout.NORTH);

		
		southPanel.setOpaque(false);
		
		return southPanel;
	}
	
	/**
	 * Metodo che crea il pannello dove selezionare il colore delle pedine con
	 * cui giocare.
	 * 
	 * @return il pannello.
	 */
	private JPanel selectColor(){
		JPanel back = new JPanel();
		back.setOpaque(false);
		
		ImageIcon icon =new ImageIcon
				("src/Resources/grafica/Menu/Opzioni/PedinaBiancaSelected.png");
		white.setIcon(icon);
		white.setBorderPainted(false);
		white.setContentAreaFilled(false);
		white.setFocusable(false);
		
		ImageIcon icon2 =new ImageIcon
				("src/Resources/grafica/Menu/Opzioni/pedinaNeraPiccola.png");
		black.setIcon(icon2);
		black.setBorderPainted(false);
		black.setContentAreaFilled(false);
		black.setFocusable(false);
		back.setLayout(new FlowLayout());
		
		white.addActionListener(new ActionListener () {
			
			public void actionPerformed(ActionEvent e) {
				white.setIcon(new ImageIcon
						("src/Resources/grafica/Menu/Opzioni/PedinaBiancaSelected.png"));
				black.setIcon(new ImageIcon
						("src/Resources/grafica/Menu/Opzioni/pedinaNeraPiccola.png"));
				setColor(0);
			}
		});
		
		black.addActionListener(new ActionListener () {
			
			public void actionPerformed(ActionEvent e) {
				white.setIcon(new ImageIcon
						("src/Resources/grafica/Menu/Opzioni/pedinaBiancaPiccola.png"));
				black.setIcon(new ImageIcon
						("src/Resources/grafica/Menu/Opzioni/PedinaNeraSelected.png"));
				setColor(1);
			}
		});
		
		back.add(white);

		JButton text = new JButton
				(new ImageIcon("src/Resources/grafica/Menu/Opzioni/SelezionaColore.png"));
		text.setContentAreaFilled(false);
		text.setBorderPainted(false);
		text.setFocusable(false);
		
		back.add(text);
		back.add(black);
		
		return back;
	}
	
	/**
	 * Metodo che aggiunge il pannello con i pulsanti "Conferma" e "Annulla".
	 * 
	 * @return il pannello.
	 */
	private JPanel addConfirm() {
		JPanel back = new JPanel();
		back.setLayout(new FlowLayout());
		back.setOpaque(false);
		
		JButton confirm = new JButton();
		ImageIcon icon =new ImageIcon
				("src/Resources/grafica/Menu/Opzioni/Conferma.png");
		confirm.setIcon(icon);
		confirm.setBorderPainted(false);
		confirm.setContentAreaFilled(false);
		confirm.setFocusable(false);
		
		JButton annulla = new JButton();
		ImageIcon icon2 =new ImageIcon
				("src/Resources/grafica/Menu/Opzioni/Annulla.png");
		annulla.setIcon(icon2);
		annulla.setBorderPainted(false);
		annulla.setContentAreaFilled(false);
		annulla.setFocusable(false);
		
		confirm.addActionListener(new ActionListener () {
			
			public void actionPerformed(ActionEvent e) {
				
				menu.setPlayer(player.getRight().getText());
				menu.setSet(set.getChoose());
				menu.setColor(color);
				menu.setLevel(selectLevel.getLevel());
				confirmed = true;
				visible(false);
			}
		});
		
		annulla.addActionListener(new ActionListener () {
			
			public void actionPerformed(ActionEvent e) {
				
				if(!confirmed)
					menu.deleteOptions();
				else
					reset();
				
				visible(false);
			}
		});

		back.add(annulla);
		back.add(confirm);
		
		return back;
	}
	
	/**
	 * Metodo che rende invisibile la finestra delle opzioni e riabillita'
	 * il menu'.
	 * 
	 * @param b boolean che indica se rendere invisibile la finestra delle
	 * opzioni.
	 */
	private void visible (boolean b) {
		setVisible(b);
		menu.setEnabled(true);
	}
	
	/**
	 * Metodo che resetta i valori degli attributi ai valori di default o agli
	 * ultimi valori selezionati.
	 */
	private void reset() {
		int i;
		String s = menu.getPlayer();
		
		player.setTextField(s);
		
		i = menu.getSet();
		set.setImmagine(i);
		set.setChoose(i);
		
		Icon img;
		Icon img2;
		
		i = menu.getColor();
		this.color = i;
		if (i == 0) {
			img = new ImageIcon
					("src/Resources/grafica/Menu/Opzioni/PedinaBiancaSelected.png");
			img2 = new ImageIcon
					("src/Resources/grafica/Menu/Opzioni/pedinaNeraPiccola.png");
		}
		else {
			img = new ImageIcon
					("src/Resources/grafica/Menu/Opzioni/pedinaBiancaPiccola.png");
			img2 = new ImageIcon
					("src/Resources/grafica/Menu/Opzioni/PedinaNeraSelected.png");
		}
		
		white.setIcon(img);
		black.setIcon(img2);
		
		i = menu.getLevel();
		selectLevel.reset(i);
	}

}
