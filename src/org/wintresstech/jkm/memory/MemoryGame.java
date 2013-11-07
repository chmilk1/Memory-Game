package org.wintresstech.jkm.memory;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.wintrisstech.cards.Card;
import org.wintrisstech.cards.Deck;

/**
 * Rules: Cards have to be the same kind of number and suit or else they are not
 * matched + don't let William tweak my spelling (this applies to this
 * package) 
 * <p>
 * James Kelly Maron &copy; 2013
 */
@SuppressWarnings("serial")
public class MemoryGame extends JPanel implements Runnable, ActionListener {
	//constants
	private static final int COLUMN = 4 ;
	private static final int ROW = 4;
	private static final int NUMBER_OF_CARDS = COLUMN * ROW;
	private static final int MILLI_TIMER = 2000;
	//changeable
	private int state = 0;
	private CardButton clickedfirst;
	private CardButton clickedsecond;
	private int counter = 0;
	//resources
	private	Timer cardTimer = new Timer(MILLI_TIMER, this);
	private static final Random RAND = new Random();
	private CardButton[] buttons = new CardButton[NUMBER_OF_CARDS];
	private Deck deck;
	private Card[] cards;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new MemoryGame());
	}
/**
 * 
 */
	@Override
	public void run() {
		cardTimer.setRepeats(false);
		deck = null;
		JFrame frame = new JFrame("Memory Game!");
		// this.setPreferredSize(new Dimension(fourHundere,fivehungred));
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		URL url;
		try {
			url = new URL(
					"http://technologyplusblog.com/wp-content/uploads/2012/09/tf2_logo.png");
			deck = new Deck(url);
		} catch (MalformedURLException e) {
			System.out.println("ERROR ERROR!");
			e.printStackTrace();
		}
		buttons = new CardButton[ROW * COLUMN];
		this.setLayout(new GridLayout(ROW, COLUMN));
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new CardButton(deck.getCard());
			buttons[i].addActionListener((ActionListener) this);
			this.add(buttons[i]);
		}
		selectCards();
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cardTimer) {
			if (state == 2) {
				clickedfirst.flip();
				clickedsecond.flip();
				state = 0;
			}
		} else {

			CardButton clickedButton = (CardButton) e.getSource();
			processButtonClick(clickedButton);
			// clickedButton.flip();
			clickedButton.repaint();
		}
	}

	private void processButtonClick(CardButton clickedButton) {
		switch (state) {
		case 0:
			cardTimer.stop();
			// for (int i = 0; i < cards.length; i++) {
			// buttons[i].flip();
			// }

			if (clickedButton.isFaceup()) {

			} else {
				clickedfirst = clickedButton;
				clickedfirst.flip();
				state = 1;
			}
			break;
		case 1:

			if (clickedButton.isFaceup()) {

			} else {
				clickedsecond = clickedButton;
				clickedsecond.flip();
				if (clickedfirst.getCard() == clickedsecond.getCard()) {
					if (endgame()) {
						if (askUser()) {
							resetGame();
						} else {

						}

					}
					state = 0;
				} else {
					state = 2;
					counter++;
					cardTimer.start();
				}
			}

			break;

		case 2:

			if (clickedButton.isFaceup()) {
				clickedfirst.flip();
				clickedsecond.flip();
				state = 0;
				cardTimer.stop();
			} else {
				// clickedsecond = clickedButton;
				clickedfirst.flip();
				clickedsecond.flip();
				clickedButton.flip();
				clickedfirst = clickedButton;
				state = 1;
			}
			break;
		default:

		}
	}

	private void resetGame() {
		 deck.shuffle();
		for (int i = 0; i < cards.length; i++) {
			buttons[i].flip();

		}
		selectCards();

	}

	private boolean askUser() {
		if (JOptionPane.showConfirmDialog(null, "Reset, you tryed and failed horribaly " +counter+ " times you fail at life.") == JOptionPane.YES_OPTION) {
			counter = 0;
			return true;

		} else {
			return false;
		}
	}

	private boolean endgame() {
		for (int i = 0; i < cards.length; i++) {
			if (buttons[i].isFaceup() == false) {

				return false;
			}

		}
		return true;

	}

	private void selectCards() {

		cards = new Card[NUMBER_OF_CARDS];
		deck.shuffle();
		for (int i = 0; i < cards.length; i++) {
			if (i % 2 == 0) {
				cards[i] = deck.getCard();
			} else {
				cards[i] = cards[i - 1];
			}
		}
		shuffle(cards);
		for (int i = 0; i < cards.length; i++) {
			buttons[i].setCard(cards[i]);
		}

	}

	private void shuffle(Card[] cards) {
		for (int i = cards.length - 1; 0 <= i; i--) {
			int pos = RAND.nextInt(i + 1);
			Card store = cards[pos];
			cards[pos] = cards[i];
			cards[i] = store;
		}

	}

}
