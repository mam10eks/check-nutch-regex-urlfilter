package de.uni_leipzig.nutch_regex_tests;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class BlackAndWhiteExampleDirectoryTest
{
	private static File testDir(String fileName)
	{
		return new File("src"+ File.separator +"test"+ File.separator +"resources"+ File.separator + fileName);
	}
	
	@Test
	public void testOrderNumberIsOneForFile()
	{
		BlackAndWhiteExampleDirectory example = new BlackAndWhiteExampleDirectory(new File("1_tmp.txt"));
		Assert.assertEquals(1, example.getOrderNumber());
	}
	
	@Test
	public void testOrderNumberIsOneForFileWithSeveralUnderScores()
	{
		BlackAndWhiteExampleDirectory example = new BlackAndWhiteExampleDirectory(new File("1_t_m_p.txt"));
		Assert.assertEquals(1, example.getOrderNumber());
	}
	
	@Test
	public void testOrderNumberIsOneForFileWithLongUnderscore()
	{
		BlackAndWhiteExampleDirectory example = new BlackAndWhiteExampleDirectory(new File("1____tmp.txt"));
		Assert.assertEquals(1, example.getOrderNumber());
	}
	
	@Test
	public void checkOrderingOfBlackAndWhiteExampleDirectoryIsCorrect()
	{
		List<BlackAndWhiteExampleDirectory> blackAndWhiteExampleDirectory = Arrays.asList(
			new BlackAndWhiteExampleDirectory(new File("1_tmp.txt")),
			new BlackAndWhiteExampleDirectory(new File("3_tmp.txt")),
			new BlackAndWhiteExampleDirectory(new File("2_tmp.txt__")),
			new BlackAndWhiteExampleDirectory(new File("5_t_m_p_t_x_t_")),
			new BlackAndWhiteExampleDirectory(new File("4______tmp.txt")));
		
		Collections.sort(blackAndWhiteExampleDirectory);
		
		for(int i=1; i< 6; i++)
		{
			Assert.assertEquals(i, blackAndWhiteExampleDirectory.get(i-1).getOrderNumber());
		}
	}
	
	@Test
	public void checkThatCorrectDirectoryIsValidBlackAndWhiteDirectory()
	{
		Assert.assertTrue(new BlackAndWhiteExampleDirectory(testDir("1_complete_directory")).isValid());
	}
	
	@Test
	public void checkThatDirectoryWithEmptyFilesIsNotValid()
	{
		Assert.assertFalse(new BlackAndWhiteExampleDirectory(testDir("2_directory_with_empty_files")).isValid());
	}
	
	@Test
	public void checkThatDirectoryWithOnlyWhiteListIsNotValid()
	{
		Assert.assertFalse(new BlackAndWhiteExampleDirectory(testDir("3_directory_with_whitelist")).isValid());
	}
	
	@Test
	public void checkThatDirectoryWithOnlyBlackListIsNotValid()
	{
		Assert.assertFalse(new BlackAndWhiteExampleDirectory(testDir("4_directory_with_blacklist")).isValid());
	}
	
	@Test
	public void checkThatDirectoryWithOnlyUrlRegexListIsNotValid()
	{
		Assert.assertFalse(new BlackAndWhiteExampleDirectory(testDir("5_directory_with_url_regex")).isValid());
	}
}
