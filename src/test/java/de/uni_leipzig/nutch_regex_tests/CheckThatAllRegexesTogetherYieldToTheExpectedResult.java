package de.uni_leipzig.nutch_regex_tests;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.nutch.net.URLFilter;
import org.apache.nutch.urlfilter.regex.RegexURLFilter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import lombok.SneakyThrows;

@RunWith(Parameterized.class)
public class CheckThatAllRegexesTogetherYieldToTheExpectedResult
{
	private static final URLFilter urlFilter = createUrlFilter();
	
	@Parameters
	public static Collection<File> arguments()
	{
		return Arrays.asList(RegexCreator.getAllRegexDirectories());
	}
	
	@SneakyThrows
	private static URLFilter createUrlFilter()
	{
		String urlFilter = RegexCreator.constructConcatenatedRegexFileContent(RegexCreator.getAllRegexDirectories());
		
		return new RegexURLFilter(urlFilter);
	}
	
	private final File regexDirectoryToCheck;
	
	public CheckThatAllRegexesTogetherYieldToTheExpectedResult(File regexDirectoryToCheck)
	{
		this.regexDirectoryToCheck = regexDirectoryToCheck;
	}
	
	@Test
	public void checkThatAllWhitelistUrlsAreNotFiltered()
	{
		List<String> whiteListUrls = loadUrls(RegexCreator.FILE_WHITE);
		
		Assert.assertNotEquals("White and Blacklist must at least contain one example", 0, whiteListUrls.size());
		Assert.assertEquals(whiteListUrls, applyUrlFilter(whiteListUrls));
	}
	
	@Test
	public void checkThatAllBlacklistUrlsAreFiltered()
	{
		List<String> blacklistUrls = loadUrls(RegexCreator.FILE_BLACK);
		
		Assert.assertNotEquals("White and Blacklist must at least contain one example", 0, blacklistUrls.size());
		applyUrlFilter(blacklistUrls).forEach(System.out::println);
		Assert.assertEquals(0, applyUrlFilter(blacklistUrls).size());
	}
	
	private List<String> applyUrlFilter(List<String> urls)
	{	
		return urls.stream()
			.filter(url -> urlFilter.filter(url) != null)
			.collect(Collectors.toList());
	}
	
	private List<String> loadUrls(String fileName)
	{
		return Stream.of(StringUtils.split(RegexCreator.extractContentOfFile(regexDirectoryToCheck.listFiles(), fileName)))
			.map(StringUtils::trim)
			.filter(s -> !StringUtils.startsWith(s, "#") && !s.isEmpty())
			.collect(Collectors.toList());
	}
}
