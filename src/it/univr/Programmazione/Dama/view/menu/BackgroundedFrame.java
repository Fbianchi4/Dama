package it.univr.Programmazione.Dama.view.menu;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


/**
 * Crea un pannello con un'immagine di sfondo.
 */
@SuppressWarnings("serial")
public class BackgroundedFrame extends JPanel{
	
	/**
	 * Immagine di sfondo del pannello.
	 */
	private BufferedImage img;

	/**
	 * Costruttore della classe.
	 * 
	 * @param s la stringa con il percorso dell'immagine.
	 */
	public BackgroundedFrame(String s){
		super(true);
		
		try{
			this.img = ImageIO.read(new File(s));
		} catch(Exception e) {}
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
	}

}