/* Kirby's Memory Game
 * Dat Bui
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import javax.swing.*;

public class KirbyMemoryGame extends JPanel implements KeyListener, MouseMotionListener, MouseListener, ActionListener
{ 
	//fields
	private ImageIcon titleScreen, star, smallStar, kirbyInput, instructionKirby, checkmark;
	private Font fTitle, fInstruction, fOptions, fInstructionSmall;
	private FontMetrics fmTitle, fmInstruction, fmOptions;
	private boolean checkValid;
	private Color colTitle, colCyan;
	private String userName, userNameCapitalized;
	private int titleLength, instructionLength, screen, chooseOption, instructionOp, easyOp, customOp, hardOp, starY, cardChosen1, cardChosen2, userCardNum, counter, point, cardIndex, seconds, matchCount, endGame, cusDistance, cusX, cusCard;
	private ImageIcon[] cardUsed1, cardUsed2, cardUsedCus;
	private boolean faceDown, repeat;
	private int[] cardChosen, cardChosenHard, cardChosenCus;
	private JFrame frame;
	private Timer timeTaken;
	
	//calls the constructor
	public static void main(String[] args)
	{
		new KirbyMemoryGame();
	}
	
	//initializes fields
	public KirbyMemoryGame()
	{	
		//initializes image icons
		titleScreen = new ImageIcon("titlebg.png");
		star = new ImageIcon("spinningStar.gif");
		kirbyInput = new ImageIcon("kirby.png");
		instructionKirby = new ImageIcon ("instructionKirby.png");
		checkmark = new ImageIcon ("greenCheckmark.png");
		
		//initializes and scales image for small star
		Image smallStar1 = (star.getImage().getScaledInstance(50, 50,Image.SCALE_SMOOTH));
		smallStar = new ImageIcon(smallStar1);
		
		//initializes variables to choose cards, check if the cards are the same and other card related variables
		counter = 0;
		cardChosen1 = -1;
		cardChosen2 = -1;
		cardUsed1 = new ImageIcon[10];
		cardUsed2 = new ImageIcon[14];
		cardUsedCus = new ImageIcon[20];
		
		//initializes and declares font
		fTitle = new Font ("Berlin Sans FB Demi", Font.BOLD, 60);
		fInstruction = new Font("Lucida Console", Font.BOLD,25);
		fInstructionSmall = new Font("Lucida Console", Font.BOLD,14);
		fOptions = new Font("Lucida Console", Font.BOLD, 35);
		
		//initializes and declares font metrics
		fmTitle = getFontMetrics(fTitle);
		fmInstruction = getFontMetrics(fInstruction);
		fmOptions = getFontMetrics(fOptions);
		
		//initializes and declares colors
		colTitle = new Color (255, 124, 204);
		colCyan = new Color (195, 255, 255);
		
		//initializes and declares string widths
		titleLength = fmTitle.stringWidth("KIRBY'S MEMORY GAME");
		instructionLength = fmInstruction.stringWidth("Click here to view the instructions...");
		chooseOption = fmInstruction.stringWidth("Click to choose your game mode...");
		instructionOp = fmOptions.stringWidth("INSTRUCTIONS");
		easyOp = fmOptions.stringWidth("EASY");
		customOp = fmOptions.stringWidth("CUSTOM");
		hardOp = fmOptions.stringWidth("HARD");
		starY = 160;
		
		//initializes more fields relating to the menu screen and cards
		userName = null;
		screen = 0;
		userCardNum = 10;
		checkValid = false;
		point = 25;
		cardIndex = 0;
		matchCount = 0;
		faceDown = true;
		cusDistance = 0;
		cusX = 0;
		cusCard =5;
		
		
		//initializes variables/objects for timer
		timeTaken = new Timer (1000, this);
		seconds = 0;
		
		//initializes array for cards that already have their matches found
		cardChosen = new int[10];
		cardChosenHard = new int[14];
		cardChosenCus = new int[20];

		
		//sets properties of the JPanel
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
        setFocusable(true);
        requestFocus();
		
        //sets the properties of the JFrame
		frame = new JFrame();
		frame.setContentPane(this);
		frame.setTitle ("Kirby's Memory Game");
		frame.setSize (800,800);
		frame.setLocationRelativeTo (null);
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.setResizable (false);
		frame.setVisible (true);
		frame.getContentPane().setBackground(colCyan);
	}

	public void keyPressed(KeyEvent e)
	{
		//if the space bar is pressed, the player will see the menu screen
		if(e.getKeyCode()==KeyEvent.VK_SPACE && screen == 0)
		{
			screen = 1;
			repaint();
			
			//asks for the user's name and capitalizes the first character. If they do not put a name, the program will call the player "Stranger"
			userName = (String) JOptionPane.showInputDialog(null,"Please enter your name:\n","Name Input",JOptionPane.DEFAULT_OPTION, kirbyInput, null,null);
			if (userName == null || userName.length() == 0)
			{
				userNameCapitalized = "Stranger";
			}
			else
			{
				userName = userName.toLowerCase();
				userNameCapitalized = userName.substring(0, 1).toUpperCase() + userName.substring(1);
			}
		} 
		
		//if the player is in the instruction screen and presses the space bar, they will return to the main menu
		if (e.getKeyCode() == KeyEvent.VK_SPACE && screen == 2)
		{
			starY = 160;
			screen = 1;
			repaint();
		}
	}
		
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	public void mouseDragged(MouseEvent e) {}
	public void mouseMoved(MouseEvent e)
	{
		//draws a star next to the button the user's mouse hovers over 
		if (screen == 1 && e.getX() >= 100 && e.getX() <= 700 && e.getY() >= 140 && e.getY() <= 240)
		{
			starY = 160;
			repaint();
		}
		else if (screen == 1 && e.getX() >= 100 && e.getX() <= 700 && e.getY() >= 350 && e.getY() <= 450)
		{
			starY = 370;
			repaint();
		}
		else if (screen == 1 && e.getX() >= 100 && e.getX() <= 700 && e.getY() >= 490 && e.getY() <= 590 )
		{
			starY = 510;
			repaint();
		}
		else if (screen == 1 && e.getX() >= 100 && e.getX() <= 700 && e.getY() >= 630 && e.getY() <= 730)
		{
			starY = 650;
			repaint();
		}
	
	}
	public void mouseClicked (MouseEvent e)
	{
		//paints the instruction screen when the user clicks the "instructions" option
		if (screen == 1 && e.getX() >= 100 && e.getX() <= 700 && e.getY() >= 140 && e.getY() <= 240)
		{
			screen = 2;
			repaint();
		}
		//paints and initializes variables for the easy mode screen when the user clicks the "easy" option
		else if (screen == 1 && e.getX() >= 100 && e.getX() <= 700 && e.getY() >= 350 && e.getY() <= 450)
		{
			screen = 3;
			userCardNum = 10;
			
			//calls the card class to generate images for the cards. images are stored in an array
			Card card = new Card(userCardNum);
			cardUsed1 = card.getCardImages(screen, userCardNum);
			
			point = 26;
			faceDown = true;
			matchCount = 0;
			
			for (int i = 0;i<10;i++)
			{
				cardChosen[i] = -1;
			}
			
			timeTaken.restart();
			
			repaint();			
		}
		//paints and initializes variables for the hard mode game screen when the user clicks the "hard" option
		else if (screen == 1 && e.getX() >= 100 && e.getX() <= 700 && e.getY() >= 490 && e.getY() <= 590 )
		{
			screen = 5;
			userCardNum = 14;
			
			//calls the card class to generate images for the cards 
			Card card = new Card(userCardNum);
			cardUsed2 = card.getCardImages(screen, userCardNum);
			
			for (int i = 0;i<14;i++)
			{
				cardChosenHard[i] = -1;
			}
			
			timeTaken.restart();
			point = 40;
			faceDown = true;
			matchCount = 0;
			repaint();
			
		}
		//paints and initializes the custom mode game screen when the user clicks the "custom" option
		else if (screen == 1 && e.getX() >= 100 && e.getX() <= 700 && e.getY() >= 630 && e.getY() <= 730)
		{
			screen = 4;
			checkValid = false;
			while (checkValid == false)
			{
				try 
				{
					userCardNum = Integer.parseInt((String) JOptionPane.showInputDialog(null,"Please enter the total number of cards you want.\nIt must be an even number between four (4) and twenty (20).","Number of Cards Input",JOptionPane.DEFAULT_OPTION, kirbyInput, null,null));
					
					if (userCardNum %2 !=0)
					{
						JOptionPane.showMessageDialog(null, "Your number must be even." , "Invalid Number", JOptionPane.ERROR_MESSAGE);
					}
					else if(userCardNum <4 || userCardNum >20)
					{
						JOptionPane.showMessageDialog(null, "Your number must be between four (4) and twenty (20) inclusive." , "Invalid Number", JOptionPane.ERROR_MESSAGE);
					}
					else
					{
						checkValid = true;
					}
				}
				catch (Exception E)
				{
					JOptionPane.showMessageDialog(null, "Please enter a number." , "Invalid Number", JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
			cusCard = userCardNum/2;
			Card card = new Card(userCardNum);
			cardUsedCus = card.getCardImagesCus(userCardNum);
			point = 25;
			faceDown = true;
			matchCount = 0;
			for (int i = 0;i<20;i++)
			{
				cardChosenCus[i] = -1;
			}
			
			//CustomDimension customdimension = new CustomDimension(userCardNum);
			//cusDistance = customdimension.getDistance;
			
			timeTaken.restart();
			repaint();		
			
			
		}
		else if (screen == 3)
		{
			repeat = false;
			
			
			if (e.getY() >= 100 && e.getY() <= 260)
			{
				for (int i = 0; i < 5; i++)
				{
					if (e.getX() >= (150*i) + 50 && e.getX() <= (150*i) + 150)
					{
						cardIndex = i;
					}
				}
			}
			else if (e.getY() >= 300 && e.getY() <= 460)
			{
				for (int i = 0; i < 5; i++)
				{
					if (e.getX() >= (150*i) + 50 && e.getX() <= (150*i) + 150)
					{
						cardIndex = i+5;
					}
				}
			}
			
			for (int i = 0; i < 10; i++)
			{
				if (cardIndex == cardChosen[i])
				{
					repeat = true;
				}
			}
			
			if (repeat == false)
			{
				if (counter == 0)
				{
					point--;
					faceDown = false;
					cardChosen1 = cardIndex;
					counter =1;
				}
				else if (counter == 1)
				{
					cardChosen2 = cardIndex;
					if (cardChosen2 != cardChosen1)
					{
						point--;
						counter= 0;
						
						if (point < 1)
						{
							timeTaken.stop();
							//tell the player that they ran out of flips and return to menu screen
							JOptionPane.showMessageDialog (null, "You ran out of flips :(. The challenge took you " + seconds + " seconds.", "Ran out of flips!", JOptionPane.PLAIN_MESSAGE, kirbyInput);
							seconds = 0;
							screen = 1;
							repaint();
						}
						
						if (cardUsed1[cardChosen1] == cardUsed1[cardChosen2])
						{
							cardChosen[matchCount*2] = cardChosen1;
							cardChosen[matchCount*2+1] = cardChosen2;
							matchCount++;
							if (matchCount==5) 
							{
								timeTaken.stop();
								String[] playAgainOrQuit = {"Play again", "Quit"};
								
								if (point != 0)
								{
									endGame = JOptionPane.showOptionDialog (null, "Congratulations, you won! You score is " + point + ".\nIt took you " + seconds + " seconds to complete the challenge.\nDo you want to play again or quit?\n", "Play again?", JOptionPane.DEFAULT_OPTION, 0, kirbyInput, playAgainOrQuit, null);
								}
								else
								{
									endGame = JOptionPane.showOptionDialog (null, "Although you ran out of flips, you found all the matches!\nYou still have 0 points though.\nDo you want to play again or quit?\n", "Play again?", JOptionPane.DEFAULT_OPTION, 0, kirbyInput, playAgainOrQuit, null);
								}
								
								if (endGame == 0)
								{
									seconds = 0;
									screen = 1;
									repaint();
								}
								else if (endGame == 1)
								{
									System.exit(0);
								}
							}
						}
					}
				}
			}
			
			repaint();
		}
		else if (screen == 5)
		{
			repeat = false;
			
			
			
			if (e.getY() >= 100 && e.getY() <= 260)
			{
				for (int i = 0; i < 7; i++)
				{
					if (e.getX() >= (110*i) + 10 && e.getX() <= (110*i) + 110)
					{
						cardIndex = i;
					}
				}
			}
			else if (e.getY() >= 300 && e.getY() <= 460)
			{
				for (int i = 0; i < 7; i++)
				{
					if (e.getX() >= (110*i) + 10 && e.getX() <= (110*i) + 110)
					{
						cardIndex = i + 7;
					}
				}
			}
				
			for (int i = 0;i<14;i++)
			{
				if (cardIndex == cardChosenHard[i])
				{
					repeat = true;
				}
			}
			
			if (repeat == false)
			{
				if (counter == 0)
				{
					point--;
					faceDown = false;
					cardChosen1 = cardIndex;
					counter =1;
				}
				else if (counter == 1)
				{
					cardChosen2 = cardIndex;
					if (cardChosen2 != cardChosen1)
					{
						point--;
						counter= 0;
						if (point < 1)
						{
							timeTaken.stop();
							//tell the player that they ran out of flip and return to menu screen
							JOptionPane.showMessageDialog (null, "You ran out of flips :(. The challenge took you " + seconds + " seconds.", "Ran out of flips!", JOptionPane.PLAIN_MESSAGE, kirbyInput);
							seconds = 0;
							screen = 1;
							repaint();
						}
						if (cardUsed2[cardChosen1] == cardUsed2[cardChosen2])
						{
							cardChosenHard[matchCount*2] = cardChosen1;
							cardChosenHard[matchCount*2+1] = cardChosen2;
							matchCount++;
							if (matchCount==7) 
							{
								timeTaken.stop();
								String[] playAgainOrQuit = {"Play again", "Quit"};
								
								if (point != 0)
								{
									endGame = JOptionPane.showOptionDialog (null, "Congratulations, you won! You score is " + point + ".\nIt took you " + seconds + " seconds to complete the challenge.\nDo you want to play again or quit?\n", "Play again?", JOptionPane.DEFAULT_OPTION, 0, kirbyInput, playAgainOrQuit, null);
								}
								else
								{
									endGame = JOptionPane.showOptionDialog (null, "Although you ran out of flips, you found all the matches!\nYou still have 0 points though.\nDo you want to play again or quit?\n", "Play again?", JOptionPane.DEFAULT_OPTION, 0, kirbyInput, playAgainOrQuit, null);
								}
								
								if (endGame == 0)
								{
									seconds = 0;
									screen = 1;
									repaint();
								}
								else if (endGame == 1)
								{
									System.exit(0);
								}
					
							}
						}
						
					}
				}
			}
			repaint();
		}
		
		else if (screen == 4)
		{
			repeat = false;
			
			if (e.getY() >= 100 && e.getY() <= 260)
			{
				for (int i = 0; i < cusCard; i++)
				{
					if (e.getX() >= (150*i) + 50 && e.getX() <= (150*i) + 150)
					{
						cardIndex = i;
					}
				}
			}
			else if (e.getY() >= 300 && e.getY() <= 460)
			{
				for (int i = 0; i < cusCard; i++)
				{
					if (e.getX() >= (150*i) + 50 && e.getX() <= (150*i) + 150)
					{
						cardIndex = i+ cusCard;
					}
				}
			}
			
			for (int i = 0; i < userCardNum; i++)
			{
				if (cardIndex == cardChosenCus[i])
				{
					repeat = true;
				}
			}
			
			if (repeat == false)
			{
				if (counter == 0)
				{
					point--;
					faceDown = false;
					cardChosen1 = cardIndex;
					counter =1;
				}
				else if (counter == 1)
				{
					cardChosen2 = cardIndex;
					if (cardChosen2 != cardChosen1)
					{
						point--;
						counter= 0;
						if (point < 1)
						{
							timeTaken.stop();
							//tell the player that they ran out of flip and return to menu screen
							JOptionPane.showMessageDialog (null, "You ran out of flips :(. The challenge took you " + seconds + " seconds.", "Ran out of flips!", JOptionPane.PLAIN_MESSAGE, kirbyInput);
							seconds = 0;
							screen = 1;
							repaint();
						}
						if (cardUsedCus[cardChosen1] == cardUsedCus[cardChosen2])
						{
							cardChosenCus[matchCount*2] = cardChosen1;
							cardChosenCus[matchCount*2+1] = cardChosen2;
							matchCount++;
							if (matchCount== cusCard)
							{
								timeTaken.stop();
								String[] playAgainOrQuit = {"Play again", "Quit"};
								
								if (point != 0)
								{
									endGame = JOptionPane.showOptionDialog (null, "Congratulations, you won! You score is " + point + ". It took you " +seconds+ " seconds to complete the challenge.\nDo you want to play again or quit?\n", "Play again?", JOptionPane.DEFAULT_OPTION, 0, kirbyInput, playAgainOrQuit, null);
								}
								else
								{
									endGame = JOptionPane.showOptionDialog (null, "Although you ran out of flips, you found all the matches!\nYou still have 0 points though.\nDo you want to play again or quit?\n", "Play again?", JOptionPane.DEFAULT_OPTION, 0, kirbyInput, playAgainOrQuit, null);
								}
								
								if (endGame == 0)
								{
									seconds = 0;
									screen = 1;
									repaint();
								}
								else if (endGame == 1)
								{
									System.exit(0);
								}
							}
						}
						//cardUsed1 = swap (cardUsed1, cardChosen);
					}
				}
			}
			
			repaint();
		}
	}
	public void mousePressed (MouseEvent e) {}
	public void mouseReleased (MouseEvent e) {}
	public void mouseEntered (MouseEvent e) {}
	public void mouseExited (MouseEvent e) {}
	
	public void paint (Graphics g)
	{
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g; 
		
		//draws the title screen
		if (screen == 0)
		{
			g2.drawImage(titleScreen.getImage(), 0, 0, this);
			g2.setFont(fTitle); 
			g2.setColor(colTitle);
			g2.drawString("KIRBY'S MEMORY GAME", getWidth()/2 - titleLength/2, getHeight()/2 + fmTitle.getAscent()/2);
			g2.setFont(fInstruction);
			g2.drawString("Press the spacebar to continue...", getWidth()/2 - instructionLength/2, getHeight()/2 + 75);
			
		}
		
		//draws the menu screen
		else if (screen == 1)
		{
			frame.setSize(800,800);
			frame.setLocationRelativeTo(null);
			
			g2.setFont(fTitle); 
			g2.setColor(colTitle);
			g2.drawString("KIRBY'S MEMORY GAME", getWidth()/2 - titleLength/2, 50 + fmTitle.getAscent()/2);
			
			//draws instructions
			g2.setFont(fInstruction);
			g2.drawString("Click here to view the instructions...", getWidth()/2 - instructionLength/2, 120);
			g2.drawString("Click to choose your game mode...", getWidth()/2 - chooseOption/2, 320);
			
			
			//draws instruction button
			g2.setStroke(new BasicStroke (20));
			g2.setColor(colTitle);
			g2.draw (new Rectangle2D.Double(100, 140, 600, 100));
			g2.setColor(new Color (255,255,255));
			g2.fill (new Rectangle2D.Double(100, 140, 600, 100));
			
			//draws easy mode button
			g2.setColor(colTitle);
			g2.draw (new Rectangle2D.Double(100, 350, 600, 100));
			g2.setColor(new Color (255,255,255));
			g2.fill (new Rectangle2D.Double(100, 350, 600, 100));
			
			//draws hard mode button
			g2.setColor(colTitle);
			g2.draw (new Rectangle2D.Double(100, 490, 600, 100));
			g2.setColor(new Color (255,255,255));
			g2.fill (new Rectangle2D.Double(100, 490, 600, 100));
			
			//draws custom mode button
			g2.setColor(colTitle);
			g2.draw (new Rectangle2D.Double(100, 630, 600, 100));
			g2.setColor(new Color (255,255,255));
			g2.fill (new Rectangle2D.Double(100, 630, 600, 100));	
			
			g2.setFont(fOptions);
			g2.setColor(Color.BLACK);
			g2.drawString("INSTRUCTIONS", getWidth()/2-instructionOp/2+10 , 200);
			g2.drawString("EASY", getWidth()/2-easyOp/2 +10, 410);
			g2.drawString("HARD", getWidth()/2-hardOp/2 +10, 550);
			g2.drawString("CUSTOM", getWidth()/2-customOp/2 +10, 690);
			
			g2.drawImage(smallStar.getImage(),30, starY, this);
			
		}
		
		//draws the instructions screen
		else if(screen == 2)
		{
			g2.drawImage (instructionKirby.getImage(), 115, 225, this);
			
			g2.setFont(fInstructionSmall);
			g2.drawString("The player will be presented with a set of cards facing down. The player is then", 20, 30);
			g2.drawString("prompted to guess which two cards have a matching image on the other side. If they", 20, 55);
			g2.drawString("are not the same, they will be faced down again. If they are the same, those two", 20, 80);
			g2.drawString("cards will be erased from the set. You repeat this process until all the cards", 20, 105);
			g2.drawString("are gone. In order to win, you have to memorize the position of the cards", 20, 130);
			g2.drawString("that have been faced up and try to find the remaining cards with the same picture.", 20, 155);
			g2.drawString("The the fewer flips you make, the higher score you get!", 20, 180);
			g2.drawString("Good luck and have fun, "+ userNameCapitalized + "!", 20, 205);
			g2.drawString("Press space to go back...", 270, 750	);
		}
		
		//paints easy mode screen
		else if (screen == 3)
		{
			frame.setSize(800,800);
			frame.setLocationRelativeTo(null);
			
			g2.setColor(Color.BLACK);
			g2.setFont(fInstructionSmall);
			g2.drawString("Click to choose a card. Try to find matching pairs of cards.", 110, 500);
			g2.drawString("Remember: the fewer flips you use, the higher your score is",140,550);
			
			g2.setStroke(new BasicStroke (5));
			
			for (int i = 0; i < 5; i++) 	 
			{
				g2.setColor(Color.WHITE);
				g2.fill (new Rectangle2D.Double((50 + 150*i), 100, 100, 160));
				
				g2.setColor(colTitle);
				g2.draw (new Rectangle2D.Double((50 + 150*i), 100, 100, 160));
			}
			for (int i = 0; i < 5; i++)
			{
				g2.setColor(Color.WHITE);
				g2.fill (new Rectangle2D.Double ((50 + 150*i), 300, 100, 160));
				
				g2.setColor(colTitle);
				g2.draw (new Rectangle2D.Double ((50 + 150*i), 300, 100, 160));
			}
			
			if (faceDown == false)
			{
				//draws the first card
				if (counter == 1)
				{
					if (cardChosen1 >= 0 && cardChosen1 <= 4)
					{
						g2.drawImage (cardUsed1[cardChosen1].getImage(), 50 + 150*cardChosen1, 100, this);
					}
					else if (cardChosen1 >= 5 && cardChosen1 <= 9)
					{
						g2.drawImage (cardUsed1[cardChosen1].getImage(), 50 + 150*(cardChosen1 - 5), 300, this);
					}
				}
				//draws the second card and the first card
				else
				{
					if (cardChosen1 >= 0 && cardChosen1 <= 4)
					{
						g2.drawImage (cardUsed1[cardChosen1].getImage(), 50 + 150*cardChosen1, 100, this);
						
						if (cardChosen2 >=0 && cardChosen2 <= 4)
						{
							g2.drawImage (cardUsed1[cardChosen2].getImage(), 50 + 150*cardChosen2, 100, this);
							
						}
						else if (cardChosen2 >= 5 && cardChosen2 <= 9)
						{
							g2.drawImage (cardUsed1[cardChosen2].getImage(), 50 + 150*(cardChosen2 - 5), 300, this);
							
						}
					}
					else if (cardChosen1 >= 5 && cardChosen1 <= 9)
					{
						g2.drawImage (cardUsed1[cardChosen1].getImage(), 50 + 150*(cardChosen1 - 5), 300, this);
						
						if (cardChosen2 >=0 && cardChosen2 <= 4)
						{
							g2.drawImage (cardUsed1[cardChosen2].getImage(), 50 + 150*cardChosen2, 100, this);
							
						}
						else if (cardChosen2 >= 5 && cardChosen2 <= 9)
						{
							g2.drawImage (cardUsed1[cardChosen2].getImage(), 50 + 150*(cardChosen2 - 5), 300, this);
						}
					}	
				}
			}

			for (int i = 0; i < 10; i++)
			{
				if (cardChosen[i] != -1 && cardChosen[i] >= 0 && cardChosen[i] <= 4)
				{
					g2.drawImage(checkmark.getImage(), 50 + 150*cardChosen[i], 100, this);
				}
				else if (cardChosen[i] != -1 && cardChosen[i] >= 5 && cardChosen[i] <= 9)
				{
					g2.drawImage(checkmark.getImage(), 50 + 150*(cardChosen[i]-5), 300, this);
				}
			}
			
			String pointString = "POINT: " + point;
			g2.setFont(fOptions);
			g2.drawString(pointString,20,750);
			g2.drawString("TIME TAKEN: " + Integer.toString(seconds), 20, 700);
		}
		
		//paints custom mode screen
		else if (screen == 4 && checkValid == true)
		{
			frame.setSize((50+cusCard*150),800);
			frame.setLocationRelativeTo(null);
			
			g2.setColor(Color.BLACK);
			g2.setFont(fInstructionSmall);
			g2.drawString("Click to choose a card. Try to find matching pairs of cards.", 110, 500);
			g2.drawString("Remember: the fewer flips you use, the higher your score is",140,550);
			
			g2.setStroke(new BasicStroke (5));
			
			for (int i = 0; i < cusCard; i++) 	 
			{
				g2.setColor(Color.WHITE);
				g2.fill (new Rectangle2D.Double((50 + 150*i), 100, 100, 160));
				
				g2.setColor(colTitle);
				g2.draw (new Rectangle2D.Double((50 + 150*i), 100, 100, 160));
			}
			for (int i = 0; i < cusCard; i++)
			{
				g2.setColor(Color.WHITE);
				g2.fill (new Rectangle2D.Double ((50 + 150*i), 300, 100, 160));
				
				g2.setColor(colTitle);
				g2.draw (new Rectangle2D.Double ((50 + 150*i), 300, 100, 160));
			}
			
			if (faceDown == false)
			{
				//draws the first card
				if (counter == 1)
				{
					if (cardChosen1 >= 0 && cardChosen1 <= 4)
					{
						g2.drawImage (cardUsedCus[cardChosen1].getImage(), 50 + 150*cardChosen1, 100, this);
					}
					else if (cardChosen1 >= 5 && cardChosen1 <= 9)
					{
						g2.drawImage (cardUsedCus[cardChosen1].getImage(), 50 + 150*(cardChosen1 - 5), 300, this);
					}
				}
				//draws the second card and the first card
				else
				{
					if (cardChosen1 >= 0 && cardChosen1 <= 4)
					{
						g2.drawImage (cardUsedCus[cardChosen1].getImage(), 50 + 150*cardChosen1, 100, this);
						
						if (cardChosen2 >=0 && cardChosen2 <= 4)
						{
							g2.drawImage (cardUsedCus[cardChosen2].getImage(), 50 + 150*cardChosen2, 100, this);
							
						}
						else if (cardChosen2 >= 5 && cardChosen2 <= 9)
						{
							g2.drawImage (cardUsedCus[cardChosen2].getImage(), 50 + 150*(cardChosen2 - 5), 300, this);
							
						}
					}
					else if (cardChosen1 >= 5 && cardChosen1 <= 9)
					{
						g2.drawImage (cardUsedCus[cardChosen1].getImage(), 50 + 150*(cardChosen1 - 5), 300, this);
						
						if (cardChosen2 >=0 && cardChosen2 <= 4)
						{
							g2.drawImage (cardUsedCus[cardChosen2].getImage(), 50 + 150*cardChosen2, 100, this);
							
						}
						else if (cardChosen2 >= 5 && cardChosen2 <= 9)
						{
							g2.drawImage (cardUsedCus[cardChosen2].getImage(), 50 + 150*(cardChosen2 - 5), 300, this);
						}
					}	
				}
			}

			for (int i = 0; i < userCardNum; i++)
			{
				if (cardChosenCus[i] != -1 && cardChosenCus[i] >= 0 && cardChosenCus[i] <= 4)
				{
					g2.drawImage(checkmark.getImage(), 50 + 150*cardChosen[i], 100, this);
				}
				else if (cardChosenCus[i] != -1 && cardChosenCus[i] >= 5 && cardChosenCus[i] <= 9)
				{
					g2.drawImage(checkmark.getImage(), 50 + 150*(cardChosenCus[i]-5), 300, this);
				}
			}
			
			String pointString = "POINT: " + point;
			g2.setFont(fOptions);
			g2.drawString(pointString,20,750);
			g2.drawString("TIME TAKEN: " + Integer.toString(seconds), 20, 700);
		}
		
		//paints hard mode screen
		else if (screen == 5)
		{
			frame.setSize(800,800);
			frame.setLocationRelativeTo(null);
			
			g2.setColor(Color.BLACK);
			g2.setFont(fInstructionSmall);
			g2.drawString("Click to choose a card. Try to find matching pairs of cards.", 110, 500);
			g2.drawString("Remember: the fewer flips you use, the higher your score is",140,550);
			
			g2.setStroke(new BasicStroke (5));
			
			for (int i = 0; i < 7; i++)
			{
				g2.setColor(Color.WHITE);
				g2.fill (new Rectangle2D.Double((10 + 110*i), 100, 100, 160));
				
				
				g2.setColor(colTitle);
				g2.draw (new Rectangle2D.Double((10 + 110*i), 100, 100, 160));
			}
			for (int i = 0; i < 7; i++)
			{
				g2.setColor(Color.WHITE);
				g2.fill (new Rectangle2D.Double ((10 + 110*i), 300, 100, 160));
				
				g2.setColor(colTitle);
				g2.draw (new Rectangle2D.Double ((10 + 110*i), 300, 100, 160));	
			}
			
			if (faceDown == false)
			{
				//draws the first card
				if (counter == 1)
				{
					if (cardChosen1 >= 0 && cardChosen1 <= 6)
					{
						g2.drawImage (cardUsed2[cardChosen1].getImage(), 10 + 110*cardChosen1, 100, this);
					}
					else if (cardChosen1 >= 7 && cardChosen1 <= 13)
					{
						g2.drawImage (cardUsed2[cardChosen1].getImage(), 10 + 110*(cardChosen1 - 7), 300, this);
					}
				}
				//draws the second card and the first card
				else
				{
					if (cardChosen1 >= 0 && cardChosen1 <= 6)
					{
						g2.drawImage (cardUsed2[cardChosen1].getImage(), 10 + 110*cardChosen1, 100, this);
						
						if (cardChosen2 >= 0 && cardChosen2 <= 6)
						{
							g2.drawImage (cardUsed2[cardChosen2].getImage(), 10 + 110*cardChosen2, 100, this);
							
						}
						else if (cardChosen2 >= 7 && cardChosen2 <= 13)
						{
							g2.drawImage (cardUsed2[cardChosen2].getImage(), 10 + 110*(cardChosen2 - 7), 300, this);
							
						}
					}
					else if (cardChosen1 >= 7 && cardChosen1 <= 13)
					{
						g2.drawImage (cardUsed2[cardChosen1].getImage(), 10 + 110*(cardChosen1 - 7), 300, this);
						
						if (cardChosen2 >= 0 && cardChosen2 <= 6)
						{
							g2.drawImage (cardUsed2[cardChosen2].getImage(), 10 + 110*cardChosen2, 100, this);
							
						}
						else if (cardChosen2 >= 7 && cardChosen2 <= 13)
						{
							g2.drawImage (cardUsed2[cardChosen2].getImage(), 10 + 110*(cardChosen2 - 7), 300, this);
						}
					}	
				}
			}
		
			for (int i = 0; i < 14; i++)
			{
				if (cardChosenHard[i] != -1 && cardChosenHard[i] >= 0 && cardChosenHard[i] <= 6)
				{
					g2.drawImage(checkmark.getImage(), 50 + 110*cardChosenHard[i], 100, this);
				}
				else if (cardChosenHard[i] != -1 && cardChosenHard[i] >= 7 && cardChosenHard[i] <= 13)
				{
					g2.drawImage(checkmark.getImage(), 50 + 110*(cardChosenHard[i]-7), 300, this);
				}
			}
			
			String pointString = "POINT: " + point;
			g2.setFont(fOptions);
			g2.drawString(pointString,20,750);
			g2.drawString("TIME TAKEN: " + Integer.toString(seconds), 20, 700);
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		seconds++;
		repaint();
	}
	
	//
	public ImageIcon[] swap (ImageIcon[] cardUsed, int[]cardChosen)
	{
		Random rnd = new Random();
		int cardSwap1 = -1, cardSwap2 = -1, cardSwap1Stored = -1, validCard1Count = 0, validCard2Count = 0;
		boolean validCard1 = false, validCard2 = false;
		
		while (validCard1 == false)
		{
			cardSwap1 = rnd.nextInt (14);
			validCard1Count = 0;
			
			for (int i = 0; i < cardChosen.length; i++)
			{
				if (cardSwap1 != cardChosen[i])
				{
					validCard1Count++;
				}
			}
			
			if (validCard1Count == cardChosen.length)
			{
				validCard1 = true;
			}
		}
		
		while (validCard2 == false)
		{
			cardSwap2 = rnd.nextInt (14);
			validCard2Count = 0;
			
			for (int i = 0; i < cardChosen.length; i++)
			{
				if (cardSwap2 != cardChosen[i])
				{
					validCard2Count++;
				}
			}
			
			if (validCard2Count == cardChosen.length && cardSwap2 != cardSwap1)
			{
				validCard2 = true;
			}
			
		} 
		
		cardSwap1Stored = cardSwap1;
		cardUsed[cardSwap1] = cardUsed[cardSwap2];
		cardUsed[cardSwap2] = cardUsed[cardSwap1Stored];
		
		return cardUsed;
	}
	
	public void move()
	{
		
	}
}
