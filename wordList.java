//Name: wordList.java
//Author: Malcolm Chisholm
//Java Refresher
//Date: 3/15/14
//This is the logic portion of the Haiku Detector.

import java.io.*;
//import java.util.*;
import java.util.Scanner;

public class wordList 
{
	
	
	public String inString;
	public String allWords[];
	
	public int numWords;
	public int wordSyls[];
	
	public boolean ioError;
	public String theHaikus;
	public int numHaikus;
	
	
	//constructor
	wordList(String fName, boolean isFile)
	{
		//this constructor should take in a string, either a filename, for which isFile is true, 
		//or the whole sentence, taken in from an input field or something, for which isFile is false.
		//input file
		
		//BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));

		String sentence = "";
		//String fName ="";
		int sWords;
		String divSentence[];
		
		ioError = false;
		
		
		//wordList hWords = new wordList();

		if(isFile)
		{
			try 
			{
				sentence = fileToString(fName);
			}
			catch (IOException ioe) 
			{
				System.out.println("IO ERROR!");
				ioError = true;
				sentence = "";
			}
		}
		else
		{
			sentence = fName;
		}

		
		//process file
		
		sentence = sentence.toLowerCase();
		sentence = sentence.replaceAll("0", "");
		sentence = sentence.replaceAll("1", "");
		sentence = sentence.replaceAll("2", "");
		sentence = sentence.replaceAll("3", "");
		sentence = sentence.replaceAll("4", "");
		sentence = sentence.replaceAll("5", "");
		sentence = sentence.replaceAll("6", "");
		sentence = sentence.replaceAll("7", "");
		sentence = sentence.replaceAll("8", "");
		sentence = sentence.replaceAll("9", "");
		sentence = sentence.replaceAll("\'", "");
		sentence = sentence.replaceAll("\"", "");
		sentence = sentence.replaceAll("\n", " ");
		sentence = sentence.replaceAll("\\P{Alpha}+", " ");

		setInString(sentence);
		sWords = countWords(sentence);
		
		//output file
		
		divSentence = sepWords(sentence, sWords);
		//System.out.println("\nSEPARATED WORDS: ");
		
		setAllWords(divSentence);
		setWordSyls();
		
		theHaikus = HaikuCollector();
		
		
	}
	
	
	//setters
	public void setInString(String s)
	{inString = s;}
	
	public void setNumWords(int i)
	{numWords = i;}
	
	public void setAllWords(String[] s)
	{
		allWords = new String[numWords];
		for(int x=0; x<numWords; x++)
			allWords[x] = s[x];
	}
	
	public void setWordSyls()
	{
		wordSyls = new int[numWords];
		for(int x=0; x<numWords; x++)
		{
			wordSyls[x] = countSyls(allWords[x]);
		}
		
	}
	

	//getters
	public String getWord(int i)
	{return allWords[i];}
	
	public int getWordSyls(int i)
	{return wordSyls[i];}
	
	public int getNumWords()
	{return numWords;}

	public String getInString()
	{return inString;}
	
	public String getHaikus()
	{return theHaikus;}

	public int getNumHaikus()
	{return numHaikus;}
	
	
	//string loaders
	public static String fileToString(String fileName) throws IOException 
	{ 
		@SuppressWarnings("resource")
		String entireFileText = new Scanner(new File(fileName))
		.useDelimiter("\\A").next();
			 
		//System.out.println(entireFileText);
		return entireFileText;
	}

	public String formToString()//not done
	{
		String outStr = "";
		return outStr;
	}
	
	
	//character recognition
	public boolean isaLetter(char c)
	{
		if(Character.isLetter(c))
			return true;
		else
			return false;
	}

	public boolean isaVowel(char c)
	{
		if(isaLetter(c))
			if(c=='a'||c=='e'||c=='i'||c=='o'||c=='u'||c=='y')
				return true;
			else
				return false;
		else
			return false;
	}

	public boolean isaConsonant(char c)
	{
		if(isaLetter(c))
			if(!isaVowel(c))
				return true;
			else
				return false;
		else
			return false;
	}
	

	//string processors
	public int countWords(String inStr)
	{
		int nWords = 0;
		
		inStr = inStr.toLowerCase();//all lower case
		inStr = inStr + " ";
		//System.out.println("length = " + inStr.length());/////////////////////////////
		
		for(int x=1; x<inStr.length(); x++)
		{
			char aCh = inStr.charAt(x-1);
			//System.out.println("aCh = " + aCh);/////////////////////////////
			char bCh = inStr.charAt(x);
			//System.out.println("bCh = " +bCh+"\n");/////////////////////////////

			
			if(isaLetter(aCh))//ach is a letter
				if(!isaLetter(bCh))//bch is whitespace or punctuation or a symbol(the word ended)
					nWords++;
		}
		
		numWords = nWords;
		return nWords;//the number of words in the string.
	}

	public String[] sepWords(String inStr, int numW)
	{
		
		for(int x=0; x<inStr.length(); x++)//eliminate nonletters at beginning
		{
			if(!isaLetter(inStr.charAt(x)))
			{
				StringBuilder newStr = new StringBuilder(inStr);
				newStr.deleteCharAt(x);
				inStr = newStr.toString();
			}
			else
				break;
		}
		
		String[] splitStr = inStr.split("\\P{Alpha}+", numW);//delimiter=anything non-letter

		
		return splitStr;
	}

	public int countSyls(String inStr)
	{
		
		//a rule-based system that i created; accurate >95%, usually >98%.
		
		int numSyls = 0;
		inStr = inStr + " ";
		
		for(int x=1; x<inStr.length(); x++)
		{
			char aCh = inStr.charAt(x-1);
			char bCh = inStr.charAt(x);
			
			if(isaVowel(aCh))
				if(!isaVowel(bCh))//syllable detected
				{
					numSyls++;
					
					if(x>=3)
						if((aCh=='e'&&bCh=='s')&&!isaLetter(inStr.charAt(x+1))&&isaVowel(inStr.charAt(x-3)))//-es case, false syllable
						{
							numSyls--;

							char zCh = inStr.charAt(x-2);//char before ach
							if(zCh=='c'||zCh=='g'||zCh=='h'||zCh=='j'||zCh=='s'||zCh=='x'||isaVowel(zCh))//soft consonant before ach
								numSyls++;
						}
					
					if((aCh=='e'&&bCh=='d')&&!isaLetter(inStr.charAt(x+1)))//-ed case, false syllable detected
					{
						numSyls--;
						
						if(x>=3)
						{
							char zCh = inStr.charAt(x-2);//char before ach
							if(zCh=='d'||zCh=='t'||isaVowel(zCh))//d or t or vowel before ach (echoed)
							{
								numSyls++;

							}
						}
						
						
					}
					
					if(isaLetter(aCh)&&!isaLetter(bCh))//end of word
					{
						
						if(aCh=='e')//-e case, false syllable detected
						{
							numSyls--;
							
							if(x>=3)
							{
								char zCh = inStr.charAt(x-2);//char before ach
								
								if(zCh=='l')//a vowel before zCh
								{
									numSyls++;
									if(inStr.length()>=4)
									{
										char yCh = inStr.charAt(x-3);//char before zch
										if(isaVowel(yCh))//vowel before le, not syllable
											numSyls--;
									}
								}
							}
						}
					}
				}
			
			
			if(aCh=='i')
				if(bCh=='u')
				{
					numSyls++;//syllable detected (-iu-)
					continue;
				}
			
			if(aCh=='u')
				if(bCh=='a')
				{
					numSyls++;//syllable detected (-ua-)
					if(x >= 3)
					{
						char zCh = inStr.charAt(x-2);//char before ach
						if(zCh=='q'||zCh=='g')
						{
							numSyls--;//false syllable (gua, qua)
						}

					}
					if(inStr.length() >= 6)
					{
						//char zCh = inStr.charAt(x-2);//char before ach
						char cCh = inStr.charAt(x+1);//char after bch
						char dCh = inStr.charAt(x+2);//char after cch

						if(isaConsonant(cCh)||dCh=='e')
						{
							numSyls--;//false syllable (-uake, -uade)
						}
					}
					continue;
				}
			
			if(aCh=='y')
				if(bCh=='i')
				{
					numSyls++;//syllable detected (-yi-)
					continue;
				}
			
			if(aCh=='i')
				if(bCh=='a')
				{
					numSyls++;//syllable detected (-ia-)
					char cCh = inStr.charAt(x+1);//char after bch
					if(cCh == 'l')
					{
						numSyls--;
					}
					continue;
				}


			
			if(x >= 3)
			{
				char zCh = inStr.charAt(x-2);//char before ach

				if(isaVowel(aCh)&&isaVowel(bCh)&&isaVowel(zCh))
				{
					numSyls++;//syllable detected (3 vowels in a row)
					continue;
				}
				else if(zCh=='i')
					if(aCh=='a')
						if(!isaLetter(bCh)||bCh=='s')
						{
							numSyls++;//syllable detected (-ia)	or (-ias)
							continue;
						}
				}
			
			if( x >= 5 && !isaLetter(bCh))//false syllable cases
			{
				char zCh = inStr.charAt(x-2);//char before ach
				char yCh = inStr.charAt(x-3);//char before zch
				char xCh = inStr.charAt(x-4);//char before ych
				char wCh = inStr.charAt(x-5);//char before xch

				
				//if(!isaLetter(bCh))
					if(aCh=='s'&&zCh=='s')
						if(yCh=='e'&&(xCh=='l'||xCh=='n'))
							if(wCh=='e')// false syllable (-eless / -eness )
							{
								numSyls--;
								continue;
							}
				
				//if(!isaLetter(bCh))
					if(aCh=='y'&&zCh=='l')
						if(yCh=='e'&&isaConsonant(xCh))
							if(isaVowel(wCh))// false syllable (-vowel-consonant-ely )
							{
								numSyls--;
								continue;
							}
			}
			
				
			
		}//big forloop ends
		
		//Of the 500 most commonly used words, 6 were exceptions to the rules.
		//They are corrected here...
		//Also, i added period, therefore, therefor, and thereby.

		if(inStr.contains("beauty")&&inStr.length()==7)
			return 2; 
		if(inStr.contains("idea")&&inStr.length()==5)
			return 3;
		if(inStr.contains("science")&&inStr.length()==8)
			return 2;
		if(inStr.contains("special")&&inStr.length()==8)
			return 2;
		if(inStr.contains("area")&&inStr.length()==5)
			return 3;
		if(inStr.contains("hundred")&&inStr.length()==8)
			return 2;
		if(inStr.contains("period")&&inStr.length()==6)
			return 3;
		if(inStr.contains("therefore")&&inStr.length()==10)
			return 2;
		if(inStr.contains("therefor")&&inStr.length()==9)
			return 2;
		if(inStr.contains("hereby")&&(inStr.length()==8||inStr.length()==7))
			return 2;//hereby or thereby
		
		
		if(numSyls==0)//zero syllable word case, return one
			return 1;
		
		return numSyls;
	}

	public String HaikuCollector()
	{
		String Haikus = "";
		int sylsNeeded[] = new int[] {5, 7, 5};
		
		int currSyls = 0;
		int currWord = 0;
		int currLine = 0;
		
		numHaikus = 0;
		
		
		String hLine[] = new String[] {"", "", ""};
		
		for(currWord = 0; currWord < numWords; currWord++)//this will go through all words
		{
			//System.out.println("loop1");
			
			for(int x = currWord; x < numWords; x++ )//this will start at each word
			{
				//System.out.println("loop2");
				
				//x = currWord;

				currSyls = currSyls + wordSyls[x];//add syllables
				hLine[currLine] = hLine[currLine] + allWords[x] + ' ';//add word to line 1
				
				if(currSyls == sylsNeeded[currLine])//line detected
				{
					currLine++;//advance to next line
					currSyls = 0;//reset syllables
					if(currLine == 3)//haiku detected!
					{
						for(int c = 0; c < 3; c++)//load lines into haikus
						{
							Haikus = Haikus + hLine[c] + '\n';
							hLine[c] = "";
						}
						Haikus = Haikus + '\n';//extra newline between haikus
						currLine = 0;
						numHaikus++;
						break;///////////////
					}
				}
				
				else if(currSyls > sylsNeeded[currLine])//not a haiku. next currWord.
				{
					currLine = 0;
					currSyls = 0;
					for(int c = 0; c < 3; c++)//load lines into haiku
						hLine[c] = "";
					
					break;/////////////////
					
				}
				
			}//end of words, no haiku
			currLine = 0;
			currSyls = 0;
			for(int c = 0; c < 3; c++)//load lines into haiku
				hLine[c] = "";
			}
		
		return Haikus;
	}

	
	//main tester
	public static void main(String[] args)
	{
		//Scanner console = new Scanner(System.in);
		
		BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));

		String str = "";
		//String fName ="";
		//int sWords;
		//String divSentence[];

		
		//input string

		try 
		{
			System.out.println("FILEPATH: ");
			str = buff.readLine();
		}
		catch (IOException ioe) 
		{
			System.out.println("IO ERROR!");
		}

		System.out.println("Processing...");
		
		wordList hWords = new wordList(str, true);//filename

		
		//output string
		
		//this segment for testing.
		
		/**/
		System.out.println("\nWORDS: ");
		System.out.println(hWords.getNumWords());
		
		
		System.out.println("\nSEPARATED WORDS: ");
		for(int x=0; x<hWords.getNumWords(); x++)
		{
			System.out.print(hWords.getWordSyls(x) + " ");
			System.out.println(hWords.getWord(x) + " ");
		}
		/**/
		
		System.out.println();
		System.out.print(hWords.getHaikus());
		
		System.out.print("HAIKUS: ");

		System.out.print(hWords.getNumHaikus());
		
////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////
                              
////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////


	}
	
	
}
