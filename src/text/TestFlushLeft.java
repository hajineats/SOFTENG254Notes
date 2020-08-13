package text;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import text.Formatter;
import static org.hamcrest.CoreMatchers.*;

/**
 * This is a JUnit4 Test for Formatter class provided in SE254 (Quality Assurance)
 * class at University of Auckland for assignment 1.
 * 
 * The cases are derived from constraints detailed in the JavaDoc of Formatter class
 * 
 * Note that constraints 1, 2, 3 are implicitly checked by hard-coded
 * expected list of strings
 * 
 * @author hajinkim
 *
 */
public class TestFlushLeft {
	

	////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////LINEWIDTH///////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////
	
	/********************************
	 * TEST ABNORMAL VALUES OF LINEWIDTH
	 * - Zero limit
	 * - Negative limit
	 */
	
	/*
	 * when LINEWIDTH is 0 and we have a word, we
	 * cannot print it so illegal argument exception is thrown.
	 * If Formatter handles this case by just outputting an empty line
	 * you would not get illegalArgumentException thrown, which will fail the test
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testZeroLimit() {
		String input = "Just Casual Thing";
		int limit = 0;
		Formatter.flushLeftText(input, limit);	
	}
	
	/*
	 * when delimiter is negative, we cannot print anything
	 * so illegal argument exception is thrown. If the handling mechanism
	 * is just outputting an empty string, this test will fail 
	 * as it does not receive any thrown exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeLimit() {
		String input = "Just Casual Thing";
		int limit = -4;
		Formatter.flushLeftText(input, limit);
	}
	
	
	/********************************
	 * Line width of 1
	 * - A general word
	 * - word with only hyphens
	 * - hyphen then char
	 * - hyphen then space then char
	 * - char then hyphen then char
	 * - char then hyphen
	 */
	
	/**
	 * if the limit is 1, a normal word cannot be printed, as it's 
	 * infinitely hyphenated. - so illegal argument exception is thrown.
	 * Formatter may not throw an error, in which case this process
	 * will indefinitely continue, so we give timeout of 1 second.
	 */
	@Test(expected=IllegalArgumentException.class, timeout=1000)
	public void testAWordWithLimitOfOne() {
		String input = "aasdfasd";
		int limit = 1;
		List<String> actual = Formatter.flushLeftText(input, limit);
	}
	

	/**
	 * If limit is 1, and the word is made up of just hyphens,
	 * then the formatter just needs to parse it without 
	 * adding any hyphens.
	 * If formatter simply tries to append hyphen when the word
	 * is broken, then this process may indefinitely continue
	 * hence why timeout of 1 second was added
	 */
	@Test(timeout=1000)
	public void testAWordWithJustHyphensWithLimitOne() {
		String input = "-----";
		int limit = 1;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("-");
		expected.add("-");
		expected.add("-");
		expected.add("-");
		expected.add("-");
		
		assertTrue(actual.equals(expected));
	}
	
	/**
	 * Hyphen should register as one line, and the following line should
	 * contain the next character
	 * If the formatter attempts to handle this by putting extra
	 * hypen, this test will fail.
	 */
	@Test
	public void testHyphenThenAChar() {
		String input = "-h";
		int limit = 1;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("-");
		expected.add("h");
		assertTrue(actual.equals(expected));
	}
	
	/**
	 * The formatter should ignore the space, as it's an extraneous whitespace
	 * and we can't have a line that starts with a whitespace character
	 */
	@Test
	public void testHyphenThenASpaceThenAChar() {
		String input = "- h";
		int limit = 1;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("-");
		expected.add("h");
		assertTrue(actual.equals(expected));
	}
	
	/**
	 * The formatter will attempt to parse the character,
	 * and it needs to add a hyphen in doing so - but it's impossible
	 * so should throw an illegal argument exception.
	 * If formatter handles this by just breaking it into three components,
	 * the expected exception won't be thrown, and hence test will fail
	 */
	@Test(expected=IllegalArgumentException.class,timeout=1000)
	public void testCharThenHyphenThenChar() {
		String input = "h-h";
		int limit = 1;
		List<String> actual = Formatter.flushLeftText(input, limit);
	}
	
	
	/**
	 * The formatter will try parse the input,
	 * but this will fail as it cannot add hyphen to the initial line.
	 */
	@Test(expected=IllegalArgumentException.class,timeout=1000)
	public void testCharThenHyphen() {
		String input = "h-";
		int limit = 1;
		List<String> actual = Formatter.flushLeftText(input, limit);
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////EMPTY,NULL,WHITESPACE INPUT///////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////
	
	/***************************
	 * TEST NULL OR EMPTY INPUT
	 */
	
	/**
	 * Null input is not valid so illegal argument exception is thrown
	 * If formatter registers it as an empty input, no exception will be thrown,
	 * and test will fail.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNullInput() {
		String input = null;
		int limit = 10;
		List<String> actual = Formatter.flushLeftText(input, limit);
	}
	
	/*
	 * Empty string should return an empty string
	 * It should not return null.
	 */
	@Test
	public void testEmptyString() {
		String input = "";
		int limit = 10;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("");
		assertTrue(actual.equals(expected));
	}
	
	/*
	 * Empty string always will return an empty string - should not throw exception
	 * This is not an invalid input, so it should not throw any exception.
	 */
	@Test
	public void testEmptyStringWithLineWidthZero() {
		String input = "";
		int limit = 0;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("");
		assertTrue(actual.equals(expected));
	}
	
	/*
	 * limit that is negative should always throw an exception
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testEmptyStringButLimitIsNegative() {
		String input = "";
		int limit = -1;
		List<String> actual = Formatter.flushLeftText(input, limit);
	}
	
	/*
	 * null input should throw an exception, so should negative limit.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testNullStringLimitZero() {
		String input = null;
		int limit = -1;
		List<String> actual = Formatter.flushLeftText(input, limit);
	}
	
	/****************************************
	 * INPUT MADE UP ONLY OF WHITE SPACES (Constraint 5,6,7)
	 * - tabs
	 * - new lines
	 * - space chars
	 * - mix
	 */
	
	/**
	 * Formatter should handle many tabs as a whitespace, and
	 * should not create any spaces since the constraint is that
	 * there are no leading or trailing spaces.
	 */
	@Test
	public void testManyTabs() {
		String input = "\t\t";
		int limit = 10;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("");
		assertTrue(actual.equals(expected));
	}
	
	/**
	 * There is no printable character in front of newline character,
	 * so it should not have any effect - i.e., we shouldn't get multiple
	 * empty lines.
	 */
	@Test
	public void testManyNewLines() {
		String input = "\n\n";
		int limit = 10;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("");
		assertTrue(actual.equals(expected));
	}
	
	/**
	 * This input is made up of only whitespaces, so Formatter should not
	 * retain them. They shoudl be reduced to an empty string.
	 */
	@Test
	public void testMultipleNormalSpaces() {
		String input = "        ";
		int limit = 10;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("");
		assertTrue(expected.equals(actual));

	}
	
	/*
	 * tabs are just white spaces, hence newline does not have any effect
	 * and all the spaces in between and at the end should be removed.
	 */
	@Test
	public void testDifferentWhiteSpacesCombination() {
		String input = "\t\t\n  \n  ";
		int limit = 10;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("");
		assertTrue(expected.equals(actual));

	}
	

	/*********************************
	 * INPUT WITH SPACES AT THE FRONT (Constraint 5,6,7)
	 * - one white space
	 * - multiple same white space
	 * - different white spaces 
	 * Though may look trivial, they are put in for robustness,
	 * and lest the algorithm only detects one of whitespace types
	 */
	
	/**
	 * there should be no whitespaces at the front of any line
	 * so any set/combinations of whitespaces potentially present
	 * at the front should be taken out
	 */
	@Test
	public void testOneSpaceAtTheFront() {
		String input = " hello";
		int limit = 10;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("hello");
		assertTrue(expected.equals(actual));
	}
	
	/**
	 * Simlarly to testOneSpaceAtTheFront, it should omit the leading space
	 */
	@Test
	public void testOneTabAtTheFront() {
		String input = "\thello";
		int limit = 10;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("hello");
		assertTrue(expected.equals(actual));
	}
	
	/**
	 * Simlarly to testOneSpaceAtTheFront, it should omit the leading space
	 */
	@Test
	public void testOneNewLineAtTheFront() {
		String input = "\nhello";
		int limit = 10;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("hello");
		assertTrue(expected.equals(actual));
	}
	
	/**
	 * Simlarly to testOneSpaceAtTheFront, it should omit the leading space
	 */
	@Test
	public void testMultipleSpacesAtTheFront() {
		String input = "    hello";
		int limit = 10;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("hello");
		assertTrue(expected.equals(actual));
	}
	
	/**
	 * Simlarly to testOneSpaceAtTheFront, it should omit the leading space
	 */
	@Test
	public void testMultipleTabsAtTheFront() {
		String input = "\t\t\thello";
		int limit = 10;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("hello");
		assertTrue(expected.equals(actual));
	}
	
	/**
	 * Simlarly to testOneSpaceAtTheFront, it should omit the leading space
	 */
	@Test
	public void testMultipleNewLinesAtTheFront() {
		String input = "\n\n\nhello";
		int limit = 10;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("hello");
		assertTrue(expected.equals(actual));
	}
	
	/**
	 * Simlarly to testOneSpaceAtTheFront, it should omit the leading space
	 */
	@Test
	public void testWhiteSpacesAtTheFrontOfInput() {
		String input = " \n\n  \n \t\t hello";
		int limit = 10;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("hello");
		assertTrue(expected.equals(actual));
	}
	
	
	/*********************************
	 * INPUT WITH SPACES IN THE MIDDLE (Constraint 5,6,7)
	 * - one white space
	 * - multiple same white space
	 * - different white spaces
	 */
	
	/**
	 * a single space in the middle should be maintained in the output,
	 * given no wrapping
	 */
	@Test
	public void testOneSpaceInTheMiddle() {
		String input = "hell o";
		int limit = 10;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("hell o");
		assertTrue(expected.equals(actual));
	}
	
	/**
	 * a tab in the middle should be converted into a whitespace in
	 * the output. It should not be multiple spaces either.
	 */
	@Test
	public void testOneTabInTheMiddle() {
		String input = "hell\to";
		int limit = 10;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("hell o");
		assertTrue(expected.equals(actual));
	}
	
	/**
	 * a newline in the middle should be converted into a space in
	 * the output
	 */
	@Test
	public void testOneNewLineInTheMiddle() {
		String input = "hell\no";
		int limit = 10;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("hell");
		expected.add("o");
		assertTrue(expected.equals(actual));
	}
	
	/**
	 * multiple spaces in the middle should be converted one space in
	 * the output
	 */
	@Test
	public void testMultipleSpacesInTheMiddle() {
		String input = "hell    o";
		int limit = 10;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("hell o");
		assertTrue(expected.equals(actual));
	}
	
	/**
	 * multiple tabs in the middle should be converted to one space in
	 * the output
	 */
	@Test
	public void testMultipleTabsInTheMiddle() {
		String input = "hell\t\t\to";
		int limit = 10;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("hell o");
		assertTrue(expected.equals(actual));
	}
	
	/**
	 * multiple newlines in the middle should be converted to one space in
	 * the output
	 */
	@Test
	public void testMultipleNewLinesInTheMiddle() {
		String input = "hell\n\n\no";
		int limit = 10;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("hell");
		expected.add("o");
		assertTrue(expected.equals(actual));
	}
	
	/*
	 * new line should be registered as there is an occurence of
	 * printable character before the newline.
	 */
	@Test
	public void testWhiteSpacesInTheMiddle() {
		String input = "hell   \n  \t  o";
		int limit = 10;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("hell");
		expected.add("o");
		assertTrue(expected.equals(actual));
	}	
	
	/*
	 * Output should be the same as testWhiteSpacesInTheMiddle
	 */
	@Test
	public void testWhiteSpacesInTheMiddle2() {
		String input = "hell\n  \t  o";
		int limit = 10;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("hell");
		expected.add("o");
		assertTrue(expected.equals(actual));
	}	
	
	/**********************************
	 * INPUT WITH SPACES AT THE END (Constraint 5,6,7)
	 * - one white space
	 * - multiple same white space
	 * - different white spaces
	 */

	/**
	 * no line ends with an empty space, so spaces should be truncated
	 */
	@Test
	public void testOneSpaceAtTheEnd() {
		String input = "hello ";
		int limit = 10;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("hello");
		assertTrue(expected.equals(actual));
	}
	
	/**
	 * no line ends with an empty space, so spaces should be truncated
	 */
	@Test
	public void testOneTabAtTheEnd() {
		String input = "hello\t";
		int limit = 10;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("hello");
		assertTrue(expected.equals(actual));
	}
	
	/**
	 * no line ends with an empty space, so spaces should be truncated
	 */
	@Test
	public void testOneNewLineAtTheEnd() {
		String input = "hello\n";
		int limit = 10;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("hello");
		assertTrue(expected.equals(actual));
	}
	
	/**
	 * no line ends with an empty space, so spaces should be truncated
	 */
	@Test
	public void testMultipleSpacesAtTheEnd() {
		String input = "hello    ";
		int limit = 10;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("hello");
		assertTrue(expected.equals(actual));
	}
	
	/**
	 * no line ends with an empty space, so spaces should be truncated
	 */
	@Test
	public void testMultipleTabsAtTheEnd() {
		String input = "hello\t\t\t";
		int limit = 10;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("hello");
		assertTrue(expected.equals(actual));
	}
	
	/**
	 * no line ends with an empty space, so spaces should be truncated
	 */
	@Test
	public void testMultipleNewLinesAtTheEnd() {
		String input = "hello\n\n\n";
		int limit = 10;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("hello");
		assertTrue(expected.equals(actual));
	}
	
	/*
	 * when there are a combination of different white spaces in the middle
	 */
	@Test
	public void testWhiteSpacesAtTheEnd() {
		String input = "hello    \n \t \t\t \n\n ";
		int limit = 10;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("hello");
		assertTrue(expected.equals(actual));
	}	
	
	
	/***********************************************
	 * INPUT WITH MULTIPLE SPACES RANDOMLY ALLOCATED
	 */
	
	/**
	 * The space parsing and trunction should be done simultaneously
	 * following the constraints of output not having contiguous whitespaces
	 */
	@Test
	public void testRandomlyScatteredSpaces() {
		String input = "  \n \t \t\t \n\n he\n  \n\n  \t\t \tllo    \n \t \t\t \n\n ";
		int limit = 10;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("he");
		expected.add("llo");
		assertTrue(expected.equals(actual));
	}	
	
	////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////MORE GENERAL PURPOSE//////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////
	
	/********************************
	 * TEST CORE FUNCTIONALITY:
	 * - (Constraint 4,8) When word is longer than doc width,
	 * split with hyphen in the first line
	 * - (Constraint 5) There are no trailing blank spaces
	 * - (Constraint 8) Word with many hyphens are split without additional hyphen
	 */

	
	/**
	 * When the input string has a word longer than line width,
	 * it should put a hyphen to the end of first line, and
	 * take everything else to the next line. This process repeats.
	 */
	@Test
	public void testWordLongerThanLineWidth() {
		String input = "pneumonoultramicr";
		int limit = 5;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("pneu-");
		expected.add("mono-");
		expected.add("ultr-");
		expected.add("amicr");
		assertTrue(actual.equals(expected));
	}
	

	/**
	 * when a sentence is chopped down into many lists,
	 * each list should not have any trailing spaces
	 */
	@Test
	public void testTrailingBlanksAreRemoved() {
		String input = "You know, whatever it is, it will pass";
		int limit = 10;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("You know,");
		expected.add("whatever");
		expected.add("it is, it");
		expected.add("will pass");
		assertTrue(actual.equals(expected));
	}
	
	/*
	 * should only pick a hyphen closest to line width
	 */
	@Test
	public void testWordWithManyHyphenation() {
		String input = "you-know-the-things-that-annoy";
		int limit = 10;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("you-know-");
		expected.add("the-");
		expected.add("things-");
		expected.add("that-annoy");
		assertTrue(actual.equals(expected));
	}
	

	
	
	/********************************
	 * Constraint 9
	 * - \n with no printable character before it
	 * - \n with printable character before it
	 */
	
	/**
	 * If there is no occurrence of printable character
	 * before newline character, don't do anything with it.
	 * 
	 */
	@Test
	public void testNotPrintableCharBeforeNewLine() {
		String input = "        \n  hey";
		int limit = 10;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("hey");
		assertTrue(expected.equals(actual));
	}
	
	/**
	 * If there is a printable character before newline character,
	 * make sure that newline is effective (provided that 
	 * it does not generate any empty list)
	 */
	@Test
	public void testPrintableCharBeforeNewLine() {
		String input = "hey   \n    yo";
		int limit = 10;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("hey");
		expected.add("yo");
		assertTrue(expected.equals(actual));
	}	
	
	
	/********************************
	 * Testing various wrapping
	 */
	
	
	/**
	 * Wrapping should take account for contiguous whitespaces first.
	 * Formatter may register whitespaces as part of linewidth, which
	 * could lead to failures.
	 */
	@Test
	public void testWrapAtSpaceCharacters() {
		String input = "h  e  l   l     o";
		int limit = 2;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("h");
		expected.add("e");
		expected.add("l");
		expected.add("l");
		expected.add("o");
		assertTrue(expected.equals(actual));
	}
	
	/**
	 * NewLine should not be broken into \ and n, even if the wrapping
	 * is attempted in between that pair.
	 */
	@Test
	public void testWrapAtBackslashNewLine() {
		String input = "hello\nmyboy";
		int limit = 6;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("hello");
		expected.add("myboy");
		assertTrue(expected.equals(actual));
	}
	
	/**
	 * Tab should not be broken into \ and t, even if the wrapping
	 * is attempted in between that pair.
	 */
	@Test
	public void testWrapAtBackslashTab() {
		String input = "hello\tmyboy";
		int limit = 6;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("hello");
		expected.add("myboy");
		assertTrue(expected.equals(actual));
	}
	
	/**
	 * The existing hyphen cannot be used in the first line
	 * since that will cause the first line to be longer than
	 * line width. So instead, the word should be broken at oo,
	 * and hyphen should be placed. If exising hyphen is used,
	 * it will cause failure.
	 */
	@Test
	public void testWrapBeforeHyphen() {
		String input = "helloo-myboy";
		int limit = 6;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("hello-");
		expected.add("o-");
		expected.add("myboy");
		assertTrue(expected.equals(actual));
	}
	
	/**
	 * existing hyphen should be used.
	 */
	public void testWrapAtHyphen() {
		String input = "hello-myboy";
		int limit = 6;
		List<String> actual = Formatter.flushLeftText(input, limit);
		List<String> expected = new ArrayList<String>();
		expected.add("hello-");
		expected.add("myboy");
		assertTrue(expected.equals(actual));
	}
	
	
}
