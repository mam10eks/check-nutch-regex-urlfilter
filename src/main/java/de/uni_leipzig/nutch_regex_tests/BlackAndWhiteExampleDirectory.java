package de.uni_leipzig.nutch_regex_tests;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;

@Data
public class BlackAndWhiteExampleDirectory implements Comparable<BlackAndWhiteExampleDirectory>
{
	public static String FILE_BLACK = "black.txt";
	
	public static String FILE_WHITE = "white.txt";
	
	public static String FILE_REGEX = "url-regex.txt";
	
	private final File exampleDirectory;
	
	private final int orderNumber;
	
	public BlackAndWhiteExampleDirectory(File exampleDirectory)
	{
		this.exampleDirectory = exampleDirectory;
		this.orderNumber = extractOrderNumberFromFileName(exampleDirectory);
	}
	
	public String getContentOfWhiteListFile()
	{
		return IOUtils.extractContentOfFileWithMatchingName(exampleDirectory.listFiles(), FILE_WHITE);
	}
	
	public String getContentOfBlackListFile()
	{
		return IOUtils.extractContentOfFileWithMatchingName(exampleDirectory.listFiles(), FILE_BLACK);
	}
	
	public String getContentOfRegexFile()
	{
		return IOUtils.extractContentOfFileWithMatchingName(exampleDirectory.listFiles(), FILE_REGEX);
	}
	
	public boolean isValid()
	{
		String tmpContent = null;
		
		return exampleDirectory != null && exampleDirectory.isDirectory() && exampleDirectory.listFiles() != null && exampleDirectory.listFiles().length == 3
				&& (tmpContent = IOUtils.extractContentOfFileWithMatchingName(exampleDirectory.listFiles(), FILE_BLACK)) != null && !tmpContent.trim().isEmpty()
				&& (tmpContent = IOUtils.extractContentOfFileWithMatchingName(exampleDirectory.listFiles(), FILE_WHITE)) != null && !tmpContent.trim().isEmpty()
				&& (tmpContent = IOUtils.extractContentOfFileWithMatchingName(exampleDirectory.listFiles(), FILE_REGEX)) != null && !tmpContent.trim().isEmpty();
	}

	private static int extractOrderNumberFromFileName(File exampleDirectory)
	{
		return Integer.parseInt(StringUtils.substringBefore(exampleDirectory.getName(), "_"));
	}
	
	@Override
	public int compareTo(BlackAndWhiteExampleDirectory o)
	{
		return Integer.valueOf(getOrderNumber()).compareTo(o.getOrderNumber());
	}
}
