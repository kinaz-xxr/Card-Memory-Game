import javax.swing.ImageIcon;

//this class uses the random array generated in the RandomArray class to assign images to a new array. The new array containing the card images is returned to the main class
public class Card
{
	//declares fields
	private ImageIcon[] cardsSet1, cardUsed;
	private int[] cardNumFinal;
	
	//initializes fields
	public Card (int userCardNum)
	{	
		cardsSet1 = new ImageIcon [20];
		cardsSet1[0] = new ImageIcon("cardsset1\\kirbycard0.png");
		cardsSet1[1] = new ImageIcon("cardsset1\\kirbycard1.png");
		cardsSet1[2] = new ImageIcon("cardsset1\\kirbycard2.png");
		cardsSet1[3] = new ImageIcon("cardsset1\\kirbycard3.png");
		cardsSet1[4] = new ImageIcon("cardsset1\\kirbycard4.png");
		cardsSet1[5] = new ImageIcon("cardsset1\\kirbycard5.png");
		cardsSet1[6] = new ImageIcon("cardsset1\\kirbycard6.png");
		cardsSet1[7] = new ImageIcon("cardsset1\\kirbycard7.png");
		cardsSet1[8] = new ImageIcon("cardsset1\\kirbycard8.png");
		cardsSet1[9] = new ImageIcon("cardsset1\\kirbycard9.png");
		cardsSet1[10] = new ImageIcon("cardsset1\\kirbycard10.png");
		cardsSet1[11] = new ImageIcon("cardsset1\\kirbycard11.png");
		cardsSet1[12] = new ImageIcon("cardsset1\\kirbycard12.png");
		cardsSet1[13] = new ImageIcon("cardsset1\\kirbycard13.png");
		cardsSet1[14] = new ImageIcon("cardsset1\\kirbycard14.png");
		cardsSet1[15] = new ImageIcon("cardsset1\\kirbycard15.png");
		cardsSet1[16] = new ImageIcon("cardsset1\\kirbycard16.png");
		cardsSet1[17] = new ImageIcon("cardsset1\\kirbycard17.png");
		cardsSet1[18] = new ImageIcon("cardsset1\\kirbycard18.png");
		cardsSet1[19] = new ImageIcon("cardsset1\\kirbycard19.png");
		
		cardUsed = new ImageIcon[20];
	}
	
	//returns an array with the images for the cards
	public ImageIcon[] getCardImages (int screen, int userCardNum)
	{	
		//create an object for RandomArray class
		RandomArray rndArray = new RandomArray(userCardNum);
		cardNumFinal = rndArray.randomArray(userCardNum);
		
		//create an array of cards that is randomly chosen from the 20 images
		for (int i = 0;i<userCardNum;i++)
		{
			cardUsed[i] = cardsSet1[cardNumFinal[i]];
		}
		return cardUsed;
	}
	
	
	public ImageIcon[] getCardImagesCus (int userCardNum)
	{	
		//create an object for RandomArray class
		RandomArray rndArray = new RandomArray(userCardNum);
		cardNumFinal = rndArray.randomArray(userCardNum);
		
		//create an array of cards that is randomly chosen from the 20 images
		for (int i = 0;i<userCardNum;i++)
		{
			cardUsed[i] = cardsSet1[cardNumFinal[i]];
		}
		return cardUsed;
	}
}

