package org.wintresstech.jkm.memory;



import javax.swing.ImageIcon;
import javax.swing.JButton;

import org.wintrisstech.cards.Card;

public class CardButton extends JButton {
	private ImageIcon faceimage;
	private ImageIcon backimage;
	private Card card;
	private boolean faceup;
	public void flip() {
		faceup =!faceup;
		if (faceup){
			this.setIcon(faceimage);
		}else{
			this.setIcon(backimage);
		}
	}

	public CardButton(Card card) {
		this.card = card;
		this.faceimage = new ImageIcon(card.getFaceImage());
		this.backimage = new ImageIcon(card.getBackImage());
		this.setIcon(backimage);
		this.faceup = false;
		
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
		this.faceimage = new ImageIcon(card.getFaceImage());
		
	}

	public boolean isFaceup() {
		return faceup;
	}

	
	

}