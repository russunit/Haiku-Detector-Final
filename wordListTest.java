import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.*;


public class wordListTest {

	@Test
	public void testWordList() 
	{
		wordList tWL = new wordList("c:/testdocs/testdoc4.txt", true);
		assertFalse(tWL.ioError);
	}

	@Test
	public void testSetInString() 
	{
		wordList tWL = new wordList("", false);
		tWL.setInString("testinstring");
		assertSame("testinstring", tWL.getInString());
	}

	@Test
	public void testSetNumWords() 
	{
		wordList tWL = new wordList("", false);

		tWL.setNumWords(10);
		assertSame(10, tWL.getNumWords());
	}

	@Test
	public void testSetAllWords() 
	{
		//internal method
	}

	@Test
	public void testSetWordSyls() 
	{
		//internal method
	}

	@Test
	public void testGetWord() 
	{
		wordList tWL = new wordList("c:/testdocs/junitTest1.txt", true);

		assertTrue(tWL.getWord(0).contains("there"));//good enough
	}

	@Test
	public void testGetWordSyls() 
	{
		wordList tWL = new wordList("c:/testdocs/junitTest1.txt", true);

		assertSame(1, tWL.getWordSyls(1));
	}

	@Test
	public void testGetNumWords() 
	{
		wordList tWL = new wordList("c:/testdocs/junitTest1.txt", true);

		assertSame(4, tWL.getNumWords());
	}

	@Test
	public void testGetInString() 
	{
		wordList tWL = new wordList("c:/testdocs/junitTest1.txt", true);
		tWL.setInString("ha");
		assertSame("ha", tWL.getInString());
	}

	@Test
	public void testGetHaikus() 
	{
		wordList tWL = new wordList("c:/testdocs/junitTest1.txt", true);
		tWL.getHaikus();//hmmmmmm
	}

	@Test
	public void testGetNumHaikus() 
	{
		wordList tWL = new wordList("c:/testdocs/junitTest1.txt", true);
		assertSame(0, tWL.getNumHaikus());
	}

	@Test
	public void testFileToString() 
	{
		wordList tWL = new wordList("c:/testdocs/junitTest1.txt", true);
		try
		{
			tWL.fileToString(tWL.getInString());
		}
		catch(IOException ioe)
		{}
	}

	@Test
	public void testFormToString() 
	{
		//wordList tWL = new wordList("c:/testdocs/junitTest1.txt", true);
	}

	@Test
	public void testIsaLetter() 
	{
		wordList tWL = new wordList("c:/testdocs/junitTest1.txt", true);
		assertTrue(tWL.isaLetter('p'));

	}

	@Test
	public void testIsaVowel() 
	{
		wordList tWL = new wordList("c:/testdocs/junitTest1.txt", true);
		assertTrue(tWL.isaVowel('a'));

	}

	@Test
	public void testIsaConsonant() 
	{
		wordList tWL = new wordList("c:/testdocs/junitTest1.txt", true);
		assertTrue(tWL.isaConsonant('p'));
	}

	@Test
	public void testCountWords() 
	{
		wordList tWL = new wordList("c:/testdocs/junitTest1.txt", true);
		tWL.countWords(tWL.getInString());
		
	}

	@Test
	public void testSepWords() 
	{
		wordList tWL = new wordList("c:/testdocs/junitTest1.txt", true);
		tWL.sepWords(tWL.getInString(), tWL.getNumWords());
		
	}

	@Test
	public void testCountSyls() 
	{
		wordList tWL = new wordList("c:/testdocs/junitTest1.txt", true);
		assertSame(3, tWL.countSyls("terrible"));
	}

	@Test
	public void testHaikuCollector() 
	{
		wordList tWL = new wordList("c:/testdocs/junitTest1.txt", true);
		tWL.HaikuCollector();
	}

	@Test
	public void testMain() 
	{
		//wordList tWL = new wordList("c:/testdocs/junitTest1.txt", true);
	}

}
