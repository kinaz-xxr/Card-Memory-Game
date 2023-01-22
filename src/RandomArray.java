/*this class generates a randomized array of integers with the following properties:
 * 
 */

public class RandomArray
{
	//declares fields
	private boolean checkRepeat, checkRepeat2;
	private int[] cardNum, cardNumFinal;
	private String[] cardString;
	private int randNum, randNum2;
	
	//initializes fields
	public RandomArray(int userCardNum)
	{
		checkRepeat = true;
		checkRepeat2 = true;
		cardNum = new int[userCardNum];
		cardNumFinal = new int[userCardNum];
		cardString = new String[20];
		randNum = 0;
		randNum2= 0;
		
	}
	public int[] randomArray(int userCardNum)
	{
		//create an array of 20 numbers from 0 to 19 in order
		for (int i=0;i<20;i++)
		{
			cardString[i] = Integer.toString(i);
		}
		
		//create an array of 10 numbers in which the first 5 numbers are randomly taken from 0 to 19 and the last 5 numbers equals the first 5 numbers
		//for example, index 0 = index 5, index 1 = index 6, index 2 = index 7..., index 4 = index 9
		for (int i = 0;i < (userCardNum/2); i++)
		{
			checkRepeat = true;
			while(checkRepeat == true)
			{
				randNum = (int)(Math.random()*20);
				if (!cardString[randNum].equals("taken"))
				{
					cardNum[i] = Integer.parseInt(cardString[randNum]);
					cardNum[i+(userCardNum/2)] = Integer.parseInt(cardString[randNum]);
					cardString[randNum] = "taken";
					checkRepeat = false;
				}
			} 
		}
		
		//then create an array of 10 that contains the same numbers as the array above but not in order
		for (int i=0;i<userCardNum;i++)
		{
			checkRepeat2 = true;
			while (checkRepeat2 == true)
			{
				randNum2 = (int)(Math.random()*userCardNum);
				if (cardNum[randNum2] != -1)
				{
					cardNumFinal[i]= cardNum[randNum2];
					cardNum[randNum2] = -1;
					checkRepeat2 = false;
				}
			}
		}
		//return the array of 10 numbers between 0 and 19 (5 pairs) in random order 
		return cardNumFinal;
	}
}
